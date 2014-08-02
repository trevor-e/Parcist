package com.trevore.parcist;

/**
 * Created by Trevor on 7/10/2014.
 */
public class ParcistInvalidatedException extends Exception {

    public ParcistInvalidatedException() {
    }

    public ParcistInvalidatedException(String detailMessage) {
        super(detailMessage);
    }

    public ParcistInvalidatedException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ParcistInvalidatedException(Throwable throwable) {
        super(throwable);
    }
}
