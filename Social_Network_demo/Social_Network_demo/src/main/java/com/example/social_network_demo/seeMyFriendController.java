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
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class seeMyFriendController extends CancelButton  {
    ObservableList<String> list = FXCollections.observableArrayList();
    @FXML
    private ListView<String> seeMyfriendsListView;
    private Parent root;
    private Stage stage;
    private Scene scene;
    private String username;
    private String pass;
    ArrayList<String> Arkadaslarim = new ArrayList<>();
    public void KullaniciAl() throws FileNotFoundException {
        File friData = new File("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\friends\\"+getUsername()+".txt");
        Scanner read = new Scanner(friData);
        while (read.hasNext()){
            Arkadaslarim.add(read.nextLine());
        }
        read.close();
        for (int j = 0; j < Arkadaslarim.size();j++){

            if (Arkadaslarim.get(j).equals(getUsername())){
                continue;
            }else {
                list.add(Arkadaslarim.get(j));
            }
        }
        seeMyfriendsListView.getItems().addAll(list);
    }

    public void seeMyFriendWall(ActionEvent event) throws IOException{
        try {
            String selectionUser = seeMyfriendsListView.getSelectionModel().getSelectedItem().toString();
            File selectionFile = new File("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\"+selectionUser+".txt");
            Scanner userRead = new Scanner(selectionFile);
            ArrayList<String> selectionUserInfo = new ArrayList<>();
            while (userRead.hasNext()){
                selectionUserInfo.add(userRead.nextLine());
            }
            userRead.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SeeMyFriendWall.fxml"));
            root = loader.load();
            seeMyFriendWallController seeMyFriendWallController = loader.getController();
            seeMyFriendWallController.DegerAl(selectionUserInfo,getUsername(),getPass());
            seeMyFriendWallController.bilgiler();
            seeMyFriendWallController.LikeInfo();
            seeMyFriendWallController.likeButtonDurum();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }catch (NullPointerException np){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata Mesajı");
            alert.setContentText("Seçilmeyen Değer eklemeye Çalıştınız!");
            alert.showAndWait();
            System.out.println(np.getMessage());
        }
    }

    @Override
    public void cancelButton(ActionEvent event) throws IOException, wrongPassword, FileError, UserAbsent {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("profilePage.fxml"));
        root = loader.load();
        profilePageController girisController = loader.getController();

        girisController.GirisController(getUsername(), getPass());
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void Bilgiler(String username,String pass){
        setUsername(username);
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
