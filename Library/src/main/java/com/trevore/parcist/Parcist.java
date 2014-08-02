package com.trevore.parcist;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;

import java.util.List;

/**
 * Created by Trevor on 7/10/2014.
 */
public class Parcist {

    /**
     * Unmarshalls a byte array into a Parcel and then sets the data position to the beginning.
     * @param bytes The marshalled data
     * @return The Parcel created out of the input
     */
    private static Parcel unmarshall(byte[] bytes) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0);
        return parcel;
    }

    /**
     * Writes an object to a Parcel and then marshalls it.
     * @param object The object to marshall
     * @return The byte representation of the object after marshalling
     */
    public static byte[] write(Object object) {
        Parcel parcel = Parcel.obtain();
        parcel.writeValue(object);
        byte[] bytes = parcel.marshall();
        parcel.recycle(); // not sure if needed or a good idea
        return bytes;
    }

    /**
     * Creates the given Parcelable class from marshalled data.
     * @param bytes The marshalled data
     * @param creator Creator used to restore class
     * @param <T> Class to create
     * @return The created class
     * @throws ParcistInvalidatedException
     */
    public static <T> T read(byte[] bytes, Parcelable.Creator<T> creator) throws ParcistInvalidatedException {
        try {
            Parcel parcel = unmarshall(bytes);
            return creator.createFromParcel(parcel);
        }
        catch(Exception e) {
            throw new ParcistInvalidatedException("Could not create Parcelable, you should invalidate your data.", e);
        }
    }

    /**
     * Creates the given class from marshalled data.  This is the less preferred way of doing it compared to using the
     * Parcelable Creator.
     * @param bytes The marshalled data
     * @param clazz Class to restore to
     * @param <T> Class to create
     * @return The created class
     * @throws ParcistInvalidatedException
     */
    @SuppressWarnings("unchecked")
    public static <T> T read(byte[] bytes, Class<T> clazz) throws ParcistInvalidatedException {
        try {
            Parcel parcel = unmarshall(bytes);
            return (T) parcel.readValue(clazz.getClassLoader());
        }
        catch(Exception e) {
            throw new ParcistInvalidatedException("Could not create Parcelable, you should invalidate your data.", e);
        }
    }

    /**
     * Populates the given list with the Parcelable data.
     * @param bytes The marshalled data
     * @param list Empty list to populate
     * @param creator Creator used to restore class
     * @param <T> Class to create
     * @throws ParcistInvalidatedException
     */
    public static <T> void readTypedList(byte[] bytes, List<T> list, Parcelable.Creator<T> creator) throws ParcistInvalidatedException {
        try {
            Parcel parcel = unmarshall(bytes);
            parcel.readTypedList(list, creator);
        }
        catch(Exception e) {
            throw new ParcistInvalidatedException("Could not create Parcelable, you should invalidate your data.", e);
        }
    }

    /**
     * Populates the given array with the Parcelable data.
     * @param bytes The marshalled data
     * @param array Empty array to populate
     * @param creator Creator used to restore class
     * @param <T> Class to create
     * @throws ParcistInvalidatedException
     */
    public static <T> void readTypedArray(byte[] bytes, T[] array, Parcelable.Creator<T> creator) throws ParcistInvalidatedException {
        try {
            Parcel parcel = unmarshall(bytes);
            parcel.readTypedArray(array, creator);
        }
        catch(Exception e) {
            throw new ParcistInvalidatedException("Could not create Parcelable, you should invalidate your data.", e);
        }
    }
}
