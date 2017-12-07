package com.achievers.data.entities;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel(analyze = { Authentication.class })
public class Authentication {

    @SerializedName("token")
    String token;

    public Authentication() {

    }

    public Authentication(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authentication that = (Authentication) o;

        return token != null ? token.equals(that.token) : that.token == null;
    }

    @Override
    public int hashCode() {
        return token != null ? token.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Authentication{" +
                "token='" + token + '\'' +
                '}';
    }
}