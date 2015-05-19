package fr.osb.deployapi.service.impl;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import fr.osb.deployapi.service.RemoteCommandService;
import fr.osb.deployapi.service.exception.RemoteCommandException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.OutputStream;

/**
 * Remote command service using {@code SSH} protocol.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
@Service
public class SSHRemoteCommandService implements RemoteCommandService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SSHRemoteCommandService.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void executeScript(final String host, final String username, final String password, final String remoteScript,
                              final Object... arguments) {

        if (StringUtils.isAnyBlank(host, username, password, remoteScript)) {
            throw new IllegalArgumentException("Remote script execution arguments are invalid.");
        }

        final String scriptArgs = asScriptArguments(arguments);

        LOGGER.info("Executing remote script '{}' on host '{}' with arguments '{}'.", remoteScript, host, scriptArgs);

        final JSch jsch = new JSch();
        Session session = null;
        Channel channel = null;

        try {

            session = jsch.getSession(username, host, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");

            session.connect();

            channel = session.openChannel("exec");
            channel.setInputStream(null);
            channel.setOutputStream(System.out);
            ((ChannelExec) channel).setPty(true);

            // man sudo
            // -S The -S (stdin) option causes sudo to read the password from the standard input instead of the terminal device.
            // -p The -p (prompt) option allows you to override the default password prompt and use a custom one.
            final String command = "sudo -S -p '' " + remoteScript + scriptArgs;
            ((ChannelExec) channel).setCommand(command);
            ((ChannelExec) channel).setErrStream(System.err);

            final OutputStream out = channel.getOutputStream();

            channel.connect();

            // FIXME Password appears clearly in console logs (security breach).
            out.write((password + System.getProperty("line.separator")).getBytes());
            out.flush();

            LOGGER.debug("Starting command \"{}\" execution.", command);

            while (true) {
                if (channel.isClosed()) {
                    LOGGER.debug("Remote script '{}' exit status: {}", remoteScript, channel.getExitStatus());
                    break;
                }
                Thread.sleep(200);
            }

            final int exitStatus = channel.getExitStatus();
            if (exitStatus != 0) {
                throw new UnsupportedOperationException("Remote script exit with code " + exitStatus + ".");
            }

        } catch (final Exception e) {
            throw new RemoteCommandException("Remote script '" + remoteScript + "' execution on host '" + host + "' failed.", e);

        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }

    /**
     * Returns the given {@code arguments} as script command arguments string.<br>
     * The returned value starts with a space character.
     *
     * @param arguments
     *         The arguments array ({@code null} values are ignored).
     * @return The given {@code arguments} as script command arguments string.
     */
    private static String asScriptArguments(final Object... arguments) {

        if (ArrayUtils.isEmpty(arguments)) {
            return "";
        }

        final StringBuilder builder = new StringBuilder();

        for (final Object argument : arguments) {
            if (argument == null) {
                continue;
            }
            builder.append(' ').append(argument.toString());
        }

        return builder.toString();
    }

}
