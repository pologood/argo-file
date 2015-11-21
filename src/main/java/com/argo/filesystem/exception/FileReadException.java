package com.argo.filesystem.exception;

/**
 * Created by yamingd on 9/10/15.
 */
public class FileReadException extends Exception {

    private int statusCode = 404;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public FileReadException() {

    }

    public FileReadException(String message) {
        super(message);
    }

    public FileReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileReadException(Throwable cause) {
        super(cause);
    }
}
