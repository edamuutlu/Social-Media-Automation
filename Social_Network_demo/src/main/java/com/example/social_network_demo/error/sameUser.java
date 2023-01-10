package com.example.social_network_demo.error;

public class sameUser extends Exception{
    public sameUser(String metin){
        super("This username has been already taken!");
    }
}
