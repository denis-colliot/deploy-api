package fr.osb.deployapi.service;

import fr.osb.deployapi.service.exception.RemoteCommandException;

/**
 * Service providing SSH commands.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
public interface RemoteCommandService {

    /**
     * <p>
     * Executes remotely the given {@code command} on the given {@code env}.
     * </p>
     * <p>
     * The command is executed with <b>sudo</b>, therefore the provided {@code username} has to be a <b>sudoer</b>.
     * </p>
     *
     * @param host
     *         The remote server host.
     * @param username
     *         The remote server username (has to be a <b>sudoer</b>).
     * @param password
     *         The remote server password.
     * @param remoteScript
     *         The executed remote script <b>absolute</b> path.
     * @param arguments
     *         The command arguments (if any). {@code null} values are ignored.
     * @throws RemoteCommandException
     *         If the command execution fails.
     */
    void executeScript(String host, String username, String password, String remoteScript, Object... arguments) throws RemoteCommandException;

}
