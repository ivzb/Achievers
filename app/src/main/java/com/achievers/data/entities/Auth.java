package com.achievers.data.entities;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel(analyze = { Auth.class })
public class Auth {

    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    public Auth() {

    }

    public Auth(
            String email,
            String password) {

        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}