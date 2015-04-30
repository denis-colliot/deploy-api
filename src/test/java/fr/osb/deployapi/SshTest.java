package fr.osb.deployapi;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * Created on 29/04/15.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
public class SshTest {

    public static void main(String[] args) throws JSchException {
        final JSch jsch = new JSch();
        jsch.setKnownHosts("~/.ssh/known_hosts");

        final Session session = jsch.getSession("admin", "192.168.51.9", 22);
        session.setPassword("qs4Q258l");
        session.connect(30 * 1000);

        final Channel channel = session.openChannel("shell");
        channel.setInputStream(System.in);
        channel.setOutputStream(System.out);
        channel.connect(3 * 1000);
    }

}
