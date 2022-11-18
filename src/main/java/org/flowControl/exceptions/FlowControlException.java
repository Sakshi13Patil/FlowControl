package org.flowControl.exceptions;

public class FlowControlException extends RuntimeException{
    public FlowControlException(String message) {
        super(message);
    }

    public FlowControlException(String message, Throwable cause) {
        super(message, cause);
    }
}
