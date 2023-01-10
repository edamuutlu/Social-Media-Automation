package com.example.social_network_demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AddFriendInfo {
    String name;
    String lastname;
    String mail;
    String url;
    String like;

    public AddFriendInfo() {
    }

    public AddFriendInfo(String name,String lastname,String mail,String url,String like){
        this.name=name;
        this.lastname=lastname;
        this.mail=mail;
        this.url=url;
        this.like=like;
    }

}
