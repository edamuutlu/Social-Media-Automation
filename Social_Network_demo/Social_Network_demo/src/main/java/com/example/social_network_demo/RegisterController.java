package com.example.social_network_demo;

import com.example.social_network_demo.error.AyniKullanici;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.security.auth.callback.Callback;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegisterController {
    @FXML
    private ComboBox<Image> chooseImage;

    @FXML
    private PasswordField confirmPasswordTextField;

    @FXML
    private TextField lastnameTextField;

    @FXML
    private TextField mailAdressTextField;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField nameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label registrationMessageLabel;

    @FXML
    private TextField userNameTextField;

    private String username;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void cancelButtonClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    ObservableList<Image> images = FXCollections.observableArrayList();
    public void addImage(){
        for (int i = 1; i < 5; i++) {
            images.add(new Image("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\resources\\images\\icon"+i+".png"));
        }
        chooseImage.getItems().addAll(images);
        chooseImage.setButtonCell(new ImageListCell());
        chooseImage.setCellFactory(listView -> new ImageListCell());
        chooseImage.getSelectionModel().select(0);
    }
    class ImageListCell extends ListCell<Image> {
        private final ImageView view;
        ImageListCell() {
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY); // içerik olarak sadece resim vb.
            setAlignment(Pos.CENTER_LEFT); // resimlerin pos duzeni
            view = new ImageView(); // ImageView nesnesi oluşturma
        }
        @Override
        protected void updateItem(Image item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setGraphic(null);
            } else {
                view.setImage(item);
                view.setFitHeight(40);
                view.setFitWidth(40);
                setGraphic(view);
            }
        }
    }

    public void Kayitİslem(ActionEvent event) throws IOException, AyniKullanici {
        //
        //System.out.println(deger);
        if (nameTextField.getText().isBlank() || lastnameTextField.getText().isBlank() || mailAdressTextField.getText().isBlank() || userNameTextField.getText().isBlank() || passwordTextField.getText().isBlank() || confirmPasswordTextField.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information");
            alert.setContentText("Please make sure to fill in all fields!");
            alert.showAndWait();
        } else {
            try {
                String imageUrl = chooseImage.getValue().getUrl();
                String name = nameTextField.getText();
                String lastname = lastnameTextField.getText();
                String mailadress = mailAdressTextField.getText();
                String password = passwordTextField.getText();
                String confirmpassword = confirmPasswordTextField.getText();
                setUsername(userNameTextField.getText());
                if (confirmpassword.equals(password)) {
                    File file = new File("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\" + getUsername() + ".txt");
                    File fileFri = new File("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\friends\\" + getUsername() + ".txt");
                    if (!(file.exists())) { // yoksa
                        file.createNewFile();
                    }
                    if (!(fileFri.exists())) { // yoksa
                        fileFri.createNewFile();
                    }

                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.write(name + "\n");
                    writer.write(lastname + "\n");
                    writer.write(mailadress + "\n");
                    writer.write(username+"\n");
                    writer.write(password + "\n");
                    writer.write(confirmpassword + "\n");
                    writer.write(imageUrl);
                    writer.close();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setContentText("User has been registered successfully!");
                    if (alert.showAndWait().get() == ButtonType.OK) {
                        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Password does not match!");
                    alert.showAndWait();
                }
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Information");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }

        }

    }
    public String getUsername () {
        return username;
    }
    public void setUsername (String username) throws AyniKullanici {
        File file = new File("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\" + username + ".txt");
        if (!(file.exists())) { // yoksa
            this.username = username;
        } else {
            throw new AyniKullanici(username);
        }
    }
}

