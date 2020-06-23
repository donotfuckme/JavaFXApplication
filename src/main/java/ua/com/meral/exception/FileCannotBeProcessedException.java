package ua.com.meral.exception;

public class FileCannotBeProcessedException extends RuntimeException {

    public FileCannotBeProcessedException(String message, Throwable cause) {
        super(message, cause);
    }
}
