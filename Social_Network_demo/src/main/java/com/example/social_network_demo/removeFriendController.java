package com.example.social_network_demo;

import com.example.social_network_demo.error.FileError;
import com.example.social_network_demo.error.UserAbsent;
import com.example.social_network_demo.error.wrongPassword;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.util.*;

public class removeFriendController extends CancelButton implements Initializable {

    @FXML
    private ListView<String> seeMyfriendsListView;

    @FXML
    private Label usernameLabel;
    @FXML
    private Label secilenUser;
    private Parent root;
    private Stage stage;
    private Scene scene;
    private String username;

    private String pass;
    ObservableList<String> list = FXCollections.observableArrayList();
    ArrayList<String> tumKullanicilar = new ArrayList<>();
    ArrayList<String> Arkadaslarim = new ArrayList<>();

    public void KullaniciAl() throws FileNotFoundException { // profilePageController sınıfından çağrılmaktadır
        File friData = new File("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\friends\\"+getUsername()+".txt");
        Scanner read = new Scanner(friData);
        while (read.hasNext()){
            Arkadaslarim.add(read.nextLine());
        }
        for (int j = 0; j < Arkadaslarim.size();j++){

            if (Arkadaslarim.get(j).equals(getUsername())){
                continue;
            }else {
                list.add(Arkadaslarim.get(j));
            }
        }
        read.close();
        usernameLabel.setText(getUsername()+ " 's Contacts");
    }


    //ListView control
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        seeMyfriendsListView.setItems(list);
        seeMyfriendsListView.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);

    }
    @FXML
    private void selectionChanged(ObservableValue<? extends String> observable, String o, String o1){
        if (list.size() != 0) {
            String secilenKullanici = seeMyfriendsListView.getSelectionModel().getSelectedItem().toString();
            secilenUser.setText(secilenKullanici);
        }
    }


    public void removeFriendButtonClicked(ActionEvent event) throws IOException {
        try{
            String secilenKullanici = seeMyfriendsListView.getSelectionModel().getSelectedItem().toString();
            File file = new File("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\friends\\"+getUsername()+".txt");

            // Remove the line from the list
            Arkadaslarim.remove(secilenKullanici);

            // Write the lines back to the file, skipping the line that you want to delete
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (int i=0; i<Arkadaslarim.size(); i++) {
                writer.write(Arkadaslarim.get(i)+"\n");
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText(secilenKullanici+ " remove from your friends list");
            alert.showAndWait();
            writer.close();
            list.clear();
            tumKullanicilar.clear();
            Arkadaslarim.clear();
            KullaniciAl();
        }catch (NullPointerException npe){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("You do not make a choise!");
            alert.showAndWait();
    }
    }
    @Override
    public void cancelButton(ActionEvent event) throws IOException, wrongPassword, FileError, UserAbsent {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("profilePage.fxml"));
        root = loader.load();
        profilePageController loginController = loader.getController();

        loginController.LoginController(getUsername(), getPass());
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void GetUserInfo(String username,String pass){
        // profilePageController sınıfından gelen kullanıcı adı ve şifre bu metota gelmektedir
        setUsername(username); // encapsulotion a göre set metoduyla gelen bilgiler değiştirilmektedir
        setPass(pass);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
