package com.example.social_network_demo.error;

public class FileError extends Exception{
    public FileError(){
        super("File can not found.");
    }
}
