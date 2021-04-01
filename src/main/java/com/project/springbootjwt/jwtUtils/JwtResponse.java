package com.project.springbootjwt.jwtUtils;

import java.io.Serializable;

public class JwtResponse implements Serializable {
    private int id;
    private String token;
    private final String type = "Bearer ";


    public JwtResponse() {
    }

    public JwtResponse(String token) {
        this.token = token;
    }

    public JwtResponse(String token, int id) {
        this.token = token;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "JwtResponse{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
