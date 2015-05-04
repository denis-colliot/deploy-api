package fr.osb.deployapi.service.exception;

/**
 * Exception thrown when a remote command execution failed.
 *
 * @author Denis Colliot (denis.colliot@zenika.com)
 */
public class RemoteCommandException extends RuntimeException {

    public RemoteCommandException(String message) {
        super(message);
    }

    public RemoteCommandException(String message, Throwable cause) {
        super(message, cause);
    }

}
