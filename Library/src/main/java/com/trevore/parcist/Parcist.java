package com.trevore.parcist;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by trevor.
 */
public class Parcist {

    public static <T extends Parcelable> T readParcelable(byte[] bytes, Parcelable.Creator<T> creator) throws ParcistInvalidatedException {
        try {
            Parcel parcel = unmarshall(bytes);
            return creator.createFromParcel(parcel);
        } catch(Exception e) {
            throw new ParcistInvalidatedException(e);
        }
    }

    public static byte[] writeParcelable(Parcelable val) {
        Parcel parcel = Parcel.obtain();
        val.writeToParcel(parcel, 0);
        return marshall(parcel);
    }

    public static <T extends Parcelable> List<T> readTypedList(byte[] bytes, Parcelable.Creator<T> creator) throws ParcistInvalidatedException {
        try {
            Parcel parcel = unmarshall(bytes);
            return parcel.createTypedArrayList(creator);
        } catch(Exception e) {
            throw new ParcistInvalidatedException(e);
        }
    }

    public static <T extends Parcelable> byte[] writeTypedList(List<T> val) {
        Parcel parcel = Parcel.obtain();
        parcel.writeTypedList(val);
        return marshall(parcel);
    }

    public static <T extends Parcelable> T[] readTypedArray(byte[] bytes, Parcelable.Creator<T> creator) throws ParcistInvalidatedException {
        try {
            Parcel parcel = unmarshall(bytes);
            return parcel.createTypedArray(creator);
        } catch(Exception e) {
            throw new ParcistInvalidatedException(e);
        }
    }

    public static <T extends Parcelable> byte[] writeTypedArray(T[] val) {
        Parcel parcel = Parcel.obtain();
        parcel.writeTypedArray(val, 0);
        return marshall(parcel);
    }

    /** Creates the given class from marshalled data.  Use the more explicit methods in favor of this. **/
    @SuppressWarnings("unchecked")
    public static <T> T read(byte[] bytes, Class<T> clazz) throws ParcistInvalidatedException {
        try {
            Parcel parcel = unmarshall(bytes);
            return (T) parcel.readValue(clazz.getClassLoader());
        } catch(Exception e) {
            throw new ParcistInvalidatedException(e);
        }
    }

    /** Writes an object to a Parcel and then marshalls it.  Use the more explicit methods in favor of this. **/
    public static byte[] write(Object val) {
        Parcel parcel = Parcel.obtain();
        parcel.writeValue(val);
        return marshall(parcel);
    }

    private static Parcel unmarshall(byte[] bytes) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0);
        return parcel;
    }

    private static byte[] marshall(Parcel parcel) {
        byte[] bytes = parcel.marshall();
        parcel.recycle(); // not totally sure if this is needed
        return bytes;
    }

    public static class ParcistInvalidatedException extends RuntimeException {

        public ParcistInvalidatedException() {
            super();
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
}
