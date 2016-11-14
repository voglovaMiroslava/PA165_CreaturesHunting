package com.monsterhunters.pa165.exceptions;

/**
 *
 * @author tomas durcak
 */
public class HuntersServiceException extends RuntimeException {

    public HuntersServiceException() {
        super();
    }

    public HuntersServiceException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public HuntersServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public HuntersServiceException(String message) {
        super(message);
    }

    public HuntersServiceException(Throwable cause) {
        super(cause);
    }
}
