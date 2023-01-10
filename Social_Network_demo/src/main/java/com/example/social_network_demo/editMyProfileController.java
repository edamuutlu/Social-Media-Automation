package com.example.social_network_demo;

import com.example.social_network_demo.error.sameUser;
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
    ObservableList<Image> images = FXCollections.observableArrayList();

    public void Bilgiler(ArrayList<String> newBilgi){
        this.newBilgi = newBilgi;
    }
    // profilePageController metodundan çağrılmaktadır
    public void UserWrite(){ // profilePageController sınıfından çağrılan bir metottur
        // profilePageController dan bilgi arraylistinin hepsi newBilgi arraylistine atanmaktaydı
        // newBilgi içerisindeki bilgiler default olarak TextField'lara set edilmektedir
        newNameTextField.setText(newBilgi.get(0));
        newLastnameTextField.setText(newBilgi.get(1));
        newMailAdressTextField.setText(newBilgi.get(2));
        newUserNameTextField.setText(newBilgi.get(3));
        newPasswordTextField.setText(newBilgi.get(4));
        newConfirmPasswordTextField.setText(newBilgi.get(5));
        for (int i = 1; i < 5; i++) {
            images.add(new Image("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\resources\\images\\profilePic"+i+".png"));
            // seçilecek resimler for döngüsü ile images adlı arrayliste eklenmektedir
        }
        chooseNewImage.getItems().addAll(images);
        chooseNewImage.setButtonCell(new ImageListCell());
        chooseNewImage.setCellFactory(listView -> new ImageListCell());
        chooseNewImage.getSelectionModel().select(0);
    }
    class ImageListCell extends ListCell<Image> { // combobox kontrolü yapılmaktadır
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

    private String newUsername;
    public void update(ActionEvent event){
        String newName = newNameTextField.getText();
        String newLastName = newLastnameTextField.getText();
        String newMail = newMailAdressTextField.getText();
        newUsername = newUserNameTextField.getText();
        String newPass = newPasswordTextField.getText();
        String newConPass = newConfirmPasswordTextField.getText();
        String newImage = chooseNewImage.getValue().getUrl();
        // TextField'lardaki bilgiler değişse de değişmese alınarak ayrı ayrı yeni değişkenlere atanmaktadır
        if (newName.isBlank() || newLastName.isBlank() || newMail.isBlank() || newUsername.isBlank() || newPass.isBlank() || newConPass.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information");
            alert.setContentText("Please make sure to fill in all fields!");
            alert.showAndWait();
        } else { // tüm girişler doldurulduysa
            try {
                setNewUsername(newUsername);
                if (newConPass.equals(newPass)) { // doğrulama şifresi ile şifre aynı ise
                    File friFileOld = new File("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\friends\\"+newBilgi.get(3)+".txt");
                    File fileOld = new File("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\"+newBilgi.get(3)+".txt");
                    File fileOldLike = new File("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\likes\\"+ newBilgi.get(3) + ".txt");
                    // kullanıcı bilgileri güncelleme talebiyle beraber kullanıcının dosyadaki adı değişeceği için
                    // kullanıcının friends, database ve likes daki bilgileri dosyadan okunmak üzere açılmaktadır
                    Scanner friOldData = new Scanner(friFileOld);
                    ArrayList<String> oldFriend = new ArrayList<>();
                    while (friOldData.hasNext()){
                        oldFriend.add(friOldData.nextLine()+"\n");
                    }
                    friOldData.close();
                    Scanner likeFileData = new Scanner(friFileOld);
                    ArrayList<String> likeData = new ArrayList<>();
                    while (likeFileData.hasNext()){
                        likeData.add(likeFileData.nextLine()+"\n");
                    }
                    likeFileData.close();

                    File friFile = new File("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\friends\\"+getNewUsername()+".txt");
                    File file = new File("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\" + getNewUsername() + ".txt");
                    File fileLike = new File("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\likes\\"+ getNewUsername() + ".txt");
                    // yeni kullanıcı adında friends, database ve likes dosyaları oluşturulmaktadır
                    if (fileOld.exists()){
                        fileOld.delete();
                    }
                    if (friFileOld.exists()){
                        friFileOld.delete();
                    }
                    if(fileOldLike.exists()){
                        fileOldLike.delete();
                    }
                    if (!(file.exists())) { // yoksa
                        file.createNewFile();
                    }
                    if (!(friFile.exists())) { // yoksa
                        friFile.createNewFile();
                    }
                    if(!(fileLike.exists())){
                        fileLike.createNewFile();
                    }

                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    BufferedWriter friWriter = new BufferedWriter(new FileWriter(friFile));
                    for (int i = 0; i < oldFriend.size(); i++){
                        friWriter.write(oldFriend.get(i)+"\n");
                        //Arkadaş bilgileri değişmediği için sadece yeni kullanıcı adıyla var olan arkadaş bilgileri yeni txt dosyasına yazdırılmaktadır.
                    }
                    friWriter.close();

                    BufferedWriter  likewriter = new BufferedWriter(new FileWriter(file));
                    for (int i = 0; i < likeData.size(); i++){
                        likewriter.write(likeData.get(i)+"\n");
                    }
                    likewriter.close();

                    writer.write(newName + "\n");
                    writer.write(newLastName + "\n");
                    writer.write(newMail + "\n");
                    writer.write(newUsername+"\n");
                    writer.write(newPass + "\n");
                    writer.write(newConPass + "\n");
                    writer.write(newImage+"\n");
                    writer.write(newBilgi.get(7));
                    writer.close();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setContentText("User has been registered successfully!");
                    if (alert.showAndWait().get() == ButtonType.OK) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("profilePage.fxml"));
                        root = loader.load();
                        profilePageController girisController = loader.getController();
                        girisController.LoginController(newUsername, newPass);
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

    public void setNewUsername(String newUsername) throws sameUser {
        File file = new File("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\" + newUsername + ".txt");
        if (!(file.exists())) { // yoksa
            this.newUsername = newUsername;
        } else {
            throw new sameUser(newUsername);
        }
    }
    @Override
    public void cancelButton(ActionEvent event) throws IOException, wrongPassword, FileError, UserAbsent {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("profilePage.fxml"));
        root = loader.load();
        profilePageController loginController = loader.getController();

        loginController.LoginController(newBilgi.get(3), newBilgi.get(4));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
