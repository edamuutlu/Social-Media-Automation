package com.example.social_network_demo.error;

public class UserAbsent extends Exception{
    public UserAbsent(String metin){
        super("This user was not found!");
    }

}
