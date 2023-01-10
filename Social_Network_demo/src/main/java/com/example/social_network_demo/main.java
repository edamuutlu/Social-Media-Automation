package com.example.social_network_demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent load = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        Scene scene = new Scene(load);
        stage.setTitle("Social Media Automation");
        stage.setScene(scene);
        stage.setResizable(false); // açılan pencerenin boyutunun büyütülmesini engeller
        stage.show();
    }
}
