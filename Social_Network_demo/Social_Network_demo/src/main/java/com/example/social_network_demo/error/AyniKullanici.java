package com.example.social_network_demo.error;

public class AyniKullanici extends Exception{
    public AyniKullanici(String metin){
        super("This username has been already taken!");
    }
}
