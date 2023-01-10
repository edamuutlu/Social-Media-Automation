package com.example.social_network_demo;

import com.example.social_network_demo.error.FileError;
import com.example.social_network_demo.error.UserAbsent;
import com.example.social_network_demo.error.wrongPassword;
import javafx.event.ActionEvent;

import java.io.IOException;

public abstract class CancelButton {
    public abstract void cancelButton(ActionEvent event) throws IOException, wrongPassword, FileError, UserAbsent;
}
