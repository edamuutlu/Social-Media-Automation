package com.example.social_network_demo.error;

public class wrongPassword extends Exception{
    public wrongPassword(String metin){
        super("Password does not match!");
    }
}
