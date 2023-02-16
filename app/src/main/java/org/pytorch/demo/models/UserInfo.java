package org.pytorch.demo.models;

import com.google.gson.annotations.SerializedName;

public class UserInfo {
    @SerializedName("_id")
    private String _id;
    @SerializedName("username")
    private String username;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("__v")
    private String __v;

    public UserInfo(String _id, String username, String email, String password, String createdAt, String __v) {
        this._id = _id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.__v = __v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }
}
