package com.trevore.parcist.sample;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Trevor on 7/10/2014.
 */
public class User implements Parcelable {

    private String name;
    private int age;
    private String github;
    private String website;

    public User(String name, int age, String github, String website) {
        this.name = name;
        this.age = age;
        this.github = github;
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeString(this.github);
        dest.writeString(this.website);
    }

    private User(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
        this.github = in.readString();
        this.website = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
