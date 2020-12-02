package com.example.csgofinder;

public class Player {

    private String uid;
    private String email;
    private String name;
    private String role;
    private boolean play;

    public Player() {
    }

    public Player(String uid, String email, String name, String role, boolean play) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.role = role;
        this.play = play;
    }

    public boolean isPlay() {
        return play;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
