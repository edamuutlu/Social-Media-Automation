package com.example.social_network_demo;

import com.example.social_network_demo.error.FileError;
import com.example.social_network_demo.error.UserAbsent;
import com.example.social_network_demo.error.wrongPassword;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class profilePageController extends LoginController implements ProfileInfo{
    @FXML
    private Label myWallLabel;
    @FXML
    private Label mailLabel;
    @FXML
    private  Label usernameLabel;
    @FXML
    private ImageView profilImage;

    private Stage stage;
    private Scene scene;
    private Parent root;
    ArrayList<String> bilgi = new ArrayList<>();

    // parametreli ve parametresiz constructorlar aşağıda bulunmaktadır
    public void LoginController(String username,String password) throws FileNotFoundException, UserAbsent, wrongPassword, FileError {
        loginProcess(username,password);
        // kullanıcı adı ve şifre kısmı boş bırakılmadıysa loginProcess kısmında şifre kontrolü gibi işlemler yapılacaktır
    }
    public void LoginController() throws FileNotFoundException, UserAbsent, wrongPassword, FileError {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        LoginController message =  new profilePageController(); // POLİMORFİZM
        // LoginController sınıfının message nesnesi new anahtar kelimesiyle profilePageController sınıfı üzerinden oluşturulmaktadır
        message.message(alert); // message nesnesi üzerinden alert mesajı verilmektedir
    }
    @Override
    public void message(Alert alert){ // LoginController sınıfındaki mesaj metodu override ediliyor
        alert.setTitle("Hata Mesajı");
        alert.setContentText("Please make sure to fill in all fields");
        alert.showAndWait();
    }


    public void loginProcess(String username,String pass) throws FileNotFoundException, UserAbsent, wrongPassword, FileError {
        File file = new File("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\" + username + ".txt");
        if (file.exists()) {
                Scanner scanner = new Scanner(file);
                while(scanner.hasNext()){
                    bilgi.add(scanner.nextLine()); // kullanıcı bilgileri, bilgi adlı arrayliste eklenmektedir
                }
                scanner.close();

                if(bilgi.get(4).equals(pass)){ // girilen şifre doğruysa
                bilgiler(); // profilePage.fxml deki labellere ilgili bilgiler set edilmek üzere bilgiler() metoduna gönderilmektedir
                }else{
                    throw new wrongPassword(pass); // yanlış şifre hatası fırlatılmaktadır
                }

        }else {
            throw new UserAbsent(username); // kullanıcı bulunamadı hatası verilir
        }
    }
    // ProfileInfo interface sınıfındaki bilgiler() metodu override edilmektedir.
    @Override
    public void bilgiler() { // profilePage.fxml deki labellere ilgili bilgiler set edilmektedir
        mailLabel.setText(bilgi.get(2));
        usernameLabel.setText(bilgi.get(3));
        String url = bilgi.get(6);
        Image image = new Image(url);
        profilImage.setImage(image);
        myWallLabel.setText("Hello to all my future friends from "+bilgi.get(0)+" "+bilgi.get(1)+".");
    }


    public void logOutButtonClicked(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Social Media Automation");
        stage.setScene(scene);
        stage.show();
    }

    // Pages
    public void AddFriendButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addFriendPage.fxml"));
        root = loader.load();
        addFriendController friendController = loader.getController();
        try {
            friendController.Bilgiler(bilgi.get(3),bilgi.get(4)); // kullanıcı adı ve şifre bilgisi addFriendController'a gönderilmektedir
            friendController.KullaniciAl(); // KullaniciAl() metodu çağrılmaktadır
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            System.out.println(ex.getMessage());
        }
    }
    public void seeMyFriendButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("seeMyFriends.fxml"));
        root = loader.load();
        seeMyFriendController seeMyfriendController = loader.getController();
        try {
            seeMyfriendController.Bilgiler(bilgi.get(3),bilgi.get(4));
            seeMyfriendController.KullaniciAl();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            System.out.println(ex.getMessage());
        }
    }

    public void editMyProfileButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("editMyProfile.fxml"));
        root = loader.load();
        editMyProfileController editController = loader.getController();
        try {
            editController.Bilgiler(bilgi); // kullanıcı bilgilerinin hepsi gönderilmektedir
            editController.UserWrite(); // UserWrite() metodu çağrılmaktadır
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            System.out.println(ex.getMessage());
        }
    }

    public void removeFriendButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("removeFriend.fxml"));
        root = loader.load();
        removeFriendController removeController = loader.getController();
        try {
            removeController.GetUserInfo(bilgi.get(3),bilgi.get(4));
            removeController.KullaniciAl();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            System.out.println(ex.getMessage());
        }
    }

}
