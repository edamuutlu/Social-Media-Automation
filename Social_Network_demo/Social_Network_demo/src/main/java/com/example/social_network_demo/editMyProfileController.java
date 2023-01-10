package com.example.social_network_demo;

import com.example.social_network_demo.error.AyniKullanici;
import com.example.social_network_demo.error.FileError;
import com.example.social_network_demo.error.UserAbsent;
import com.example.social_network_demo.error.wrongPassword;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class editMyProfileController extends CancelButton{
    @FXML
    private ComboBox<Image> chooseNewImage;

    @FXML
    private Label confirmPasswordLabel;

    @FXML
    private PasswordField newConfirmPasswordTextField;

    @FXML
    private TextField newLastnameTextField;

    @FXML
    private TextField newMailAdressTextField;

    @FXML
    private TextField newNameTextField;

    @FXML
    private PasswordField newPasswordTextField;

    @FXML
    private TextField newUserNameTextField;
    private Parent root;
    private Stage stage;
    private Scene scene;
    ArrayList<String> newBilgi = new ArrayList<>();
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
    ObservableList<Image> images = FXCollections.observableArrayList();
    public void UserWrite(){
        newNameTextField.setText(newBilgi.get(0));
        newLastnameTextField.setText(newBilgi.get(1));
        newMailAdressTextField.setText(newBilgi.get(2));
        newUserNameTextField.setText(newBilgi.get(3));
        newPasswordTextField.setText(newBilgi.get(4));
        newConfirmPasswordTextField.setText(newBilgi.get(5));
        for (int i = 1; i < 5; i++) {
            images.add(new Image("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\resources\\images\\icon"+i+".png"));
        }
        chooseNewImage.getItems().addAll(images);
        chooseNewImage.setButtonCell(new ImageListCell());
        chooseNewImage.setCellFactory(listView -> new ImageListCell());
        chooseNewImage.getSelectionModel().select(0);
    }

    private String newUsername;
    public void update(ActionEvent event){
        String newName = newNameTextField.getText();
        String newLastName = newLastnameTextField.getText();
        String newMail = newMailAdressTextField.getText();
        newUsername = newUserNameTextField.getText();
        String newPass = newPasswordTextField.getText();
        String newConPass = newConfirmPasswordTextField.getText();
        String newImage = chooseNewImage.getValue().getUrl();
        if (newName.isBlank() || newLastName.isBlank() || newMail.isBlank() || newUsername.isBlank() || newPass.isBlank() || newConPass.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information");
            alert.setContentText("Please make sure to fill in all fields!");
            alert.showAndWait();
        } else {
            try {
                setNewUsername(newUsername);
                if (newConPass.equals(newPass)) {
                    File friFileOld = new File("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\friends\\"+newBilgi.get(3)+".txt");
                    File fileOld = new File("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\"+newBilgi.get(3)+".txt");
                    Scanner friOldData = new Scanner(friFileOld);
                    ArrayList<String> oldFriend = new ArrayList<>();
                    while (friOldData.hasNext()){
                        oldFriend.add(friOldData.nextLine()+"\n");
                    }
                    friOldData.close();
                    File friFile = new File("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\friends\\"+getNewUsername()+".txt");
                    File file = new File("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\" + getNewUsername() + ".txt");
                    if (fileOld.exists()){
                        fileOld.delete();
                        System.out.println("K silindi");
                    }
                    if (friFileOld.exists()){
                        friFileOld.delete();
                        System.out.println("F silindi");
                    }
                    if (!(file.exists())) { // yoksa
                        file.createNewFile();
                    }
                    if (!(friFile.exists())) { // yoksa
                        friFile.createNewFile();
                    }
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    BufferedWriter friWriter = new BufferedWriter(new FileWriter(friFile));
                    for (int i = 0; i < oldFriend.size(); i++){
                        System.out.println("yazıyor: "+oldFriend.get(i));
                        friWriter.write(oldFriend.get(i)+"\n");
                    }
                    friWriter.close();
                    writer.write(newName + "\n");
                    writer.write(newLastName + "\n");
                    writer.write(newMail + "\n");
                    writer.write(newUsername+"\n");
                    writer.write(newPass + "\n");
                    writer.write(newConPass + "\n");
                    writer.write(newImage);
                    writer.close();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setContentText("User has been registered successfully!");
                    if (alert.showAndWait().get() == ButtonType.OK) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("profilePage.fxml"));
                        root = loader.load();
                        profilePageController girisController = loader.getController();
                        girisController.GirisController(newUsername, newPass);
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
                System.out.println(ex.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Information");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }

        }
    }
    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String username) throws AyniKullanici {
        File file = new File("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\" + username + ".txt");
        if (!(file.exists())) { // yoksa
            this.newUsername = username;
        } else {
            throw new AyniKullanici(username);
        }
    }
    @Override
    public void cancelButton(ActionEvent event) throws IOException, wrongPassword, FileError, UserAbsent {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("profilePage.fxml"));
        root = loader.load();
        profilePageController girisController = loader.getController();

        girisController.GirisController(newBilgi.get(3), newBilgi.get(4));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void Bilgiler(ArrayList<String> newBilgi){
        this.newBilgi = newBilgi;
    }

}
