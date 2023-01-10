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

public class addFriendController extends CancelButton implements Initializable {
    ObservableList<String> list = FXCollections.observableArrayList();
    @FXML
    private ListView<String> friendsListView;
    @FXML
    private Label secilenUser;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String username;
    private String pass;
    ArrayList<String> tumKullanicilar = new ArrayList<>();
    ArrayList<String> Arkadaslarim = new ArrayList<>();


    public void KullaniciAl() throws FileNotFoundException {
        File file = new File("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase");
        List<File> users = Arrays.stream(file.listFiles()).toList();
        for(int i = 0; i<file.list().length;i++){
            if (users.get(i).isFile()){
                tumKullanicilar.add(users.get(i).getName().replace(".txt",""));
            }
        }
        File friData = new File("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\friends\\"+getUsername()+".txt");
        Scanner read = new Scanner(friData);
        while (read.hasNext()){
            Arkadaslarim.add(read.nextLine());
        }
        read.close();
        for (int j = 0; j < tumKullanicilar.size();j++){
            if (tumKullanicilar.get(j).equals(getUsername())){
                continue;
            }else {
                list.add(tumKullanicilar.get(j));
            }
        }
        for (int i = 0; i < Arkadaslarim.size(); i++){
            list.remove(Arkadaslarim.get(i));
        }
    }
    //ListView kontrol.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        friendsListView.setItems(list);
        friendsListView.getSelectionModel().selectedItemProperty().addListener(this::selectionChanged);

    }
    @FXML
    private void selectionChanged(ObservableValue<? extends String> observable, String o, String o1){
        if (list.size() != 0) {
            String secilenKullanici = friendsListView.getSelectionModel().getSelectedItem().toString();
            secilenUser.setText(secilenKullanici);
        }
    }

    public void addFriendButtonClicked(ActionEvent event) throws IOException {
        String secilenKullanici = friendsListView.getSelectionModel().getSelectedItem().toString();
        File file = new File("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\friends\\"+getUsername()+".txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
        writer.write(secilenKullanici+"\n");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(secilenKullanici+ " added as friend");
        alert.showAndWait();
        writer.close();
        list.clear();
        tumKullanicilar.clear();
        Arkadaslarim.clear();
        KullaniciAl();
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
}
