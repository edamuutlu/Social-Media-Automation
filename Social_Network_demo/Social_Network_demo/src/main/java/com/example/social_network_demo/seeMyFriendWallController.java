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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

public class seeMyFriendWallController implements ProfilBilgiler{
    @FXML
    private Label friendMailLabel;
    @FXML
    private Label friendUsernameLabel;
    @FXML
    private Label friendWallLabel;
    @FXML
    private Label likeCounter;
    @FXML
    private ImageView profilImage;
    @FXML
    private Button likeButton;
    ArrayList<String> friendInfo = new ArrayList<>();
    private String hesapUsername;
    private String hesapSifre;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int likeNumber;

    private boolean buttonClicked;
    boolean durum;
    String likeUser;
    HashMap<String, Boolean> likeValue = new HashMap<String, Boolean>();
    //ArrayList<String> value = new ArrayList<>();
    int sayac;

    public void DegerAl(ArrayList<String> Info, String hesapUsername, String hesapSifre){
        this.friendInfo = Info;
        this.hesapUsername = hesapUsername;
        this.hesapSifre = hesapSifre;
        sayac = 0;
    }
    public void LikeInfo() throws IOException {
        File likeFile = new File("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\likes\\"+friendInfo.get(3)+".txt");
        Scanner read = new Scanner(likeFile);
        while (read.hasNext()){
            List<String> val = new ArrayList<>(Arrays.asList(read.nextLine().split(":")));
            for (int j = 0; j<val.size(); j++){
                System.out.println(val.get(j));
                if (val.get(j).equals(hesapUsername)){
                    this.likeUser = val.get(j);
                    this.durum = Boolean.parseBoolean(val.get(j+1));
                    this.buttonClicked = durum;
                    break;
                }else {
                    this.likeUser = "";
                    this.durum = false;
                    this.buttonClicked = durum;
                }
            }
            sayac++;
        }
        read.close();
        System.out.println(sayac);
        ilkLike(sayac);
        // diğer Durum
        if (sayac != 0){
        FileWriter writerUser = new FileWriter("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\likes\\"+friendInfo.get(3)+".txt");
        if (likeUser.equals("")){
            likeValue.put(hesapUsername, Boolean.FALSE);
            for (Map.Entry<String, Boolean> entry : likeValue.entrySet()) {
                writerUser.write(entry.getKey() + ":" + entry.getValue()+"\n");
            }
            buttonClicked = false;
        }
            writerUser.close();
        }

    }
    private void ilkLike(int sayac) throws IOException {
        if (sayac == 0){ //ilk ziyaret
            System.out.println("ilk ziyaret");
            FileWriter writer = new FileWriter("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\likes\\"+friendInfo.get(3)+".txt",true);
            likeValue.put(hesapUsername,Boolean.FALSE);
            for (Map.Entry<String, Boolean> entry : likeValue.entrySet()) {
                // put key and value separated by a colon
                writer.write(entry.getKey() + ":" + entry.getValue()+"\n");
            }
            buttonClicked = false;
            writer.close();
        }
    }

    public void likeButtonDurum(){
        if (buttonClicked == Boolean.TRUE) {
            Image initialImage = new Image("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\resources\\images\\heartRed.png");
            ImageView likeView = new ImageView(initialImage);
            likeView.setFitWidth(50);
            likeView.setFitHeight(50);
            likeButton.setGraphic(likeView);
            buttonClicked = true;
        }else {
            Image newImage = new Image("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\resources\\images\\heartWhite.png");
            ImageView disLikeView = new ImageView(newImage);
            disLikeView.setFitWidth(50);
            disLikeView.setFitHeight(50);
            likeButton.setGraphic(disLikeView);
            buttonClicked = false;
        }
    }
    public void LikeButton(ActionEvent event) throws IOException {
            // Kullanıcı hesabının dosyasını açma
            FileWriter fileData = new FileWriter("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\"+friendInfo.get(3)+".txt");

            if (!buttonClicked) {
                System.out.println("Button like!");
                Image initialImage = new Image("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\resources\\images\\heartRed.png");
                ImageView likeView = new ImageView(initialImage);
                likeView.setFitWidth(50);
                likeView.setFitHeight(50);
                likeButton.setGraphic(likeView);
                likeNumber++;
                likeCounter.setText(likeNumber+"");
                friendInfo.set(7,likeNumber+"");
                for (int i = 0; i < friendInfo.size(); i++){
                    fileData.write(friendInfo.get(i)+"\n");
                }
                fileData.close();
                // like true olma
                File likeFile = new File("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\likes\\"+friendInfo.get(3)+".txt");
                Scanner read = new Scanner(likeFile);
                ArrayList<String> valOld = new ArrayList<>();
                while (read.hasNext()){
                    for (String s: read.nextLine().split(":")){
                        valOld.add(s);
                    }
                }
                read.close();
                System.out.println(valOld.size());
                FileWriter writerLike = new FileWriter("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\likes\\"+friendInfo.get(3)+".txt");
                for (int j = 0; j<valOld.size()-1; j++){
                    System.out.println(j);
                    if (valOld.get(j).equals(hesapUsername)){
                        writerLike.write(valOld.get(j)+":"+"true"+"\n");
                    }else {
                        if (valOld.get(j) == "true" && valOld.get(j) == "false"){
                            continue;
                        }else {
                            System.out.println(valOld.get(j));
                            writerLike.write(valOld.get(j)+":"+valOld.get(j+1)+"\n");
                        }
                    }
                }
                writerLike.close();
                buttonClicked = true;
            }else {
                Image newImage = new Image("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\resources\\images\\heartWhite.png");
                ImageView disLikeView = new ImageView(newImage);
                disLikeView.setFitWidth(50);
                disLikeView.setFitHeight(50);
                likeButton.setGraphic(disLikeView);
                System.out.println("Button dislike");
                likeNumber--;
                likeCounter.setText(likeNumber+"");
                friendInfo.set(7,likeNumber+"");
                for (int i = 0; i < friendInfo.size(); i++){
                    fileData.write(friendInfo.get(i)+"\n");
                }
                fileData.close();
                File likeFile = new File("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\likes\\"+friendInfo.get(3)+".txt");
                Scanner read = new Scanner(likeFile);
                ArrayList<String> valOld = new ArrayList<>();
                while (read.hasNext()){
                    for (String s: read.nextLine().split(":")){
                        valOld.add(s);
                    }
                }
                read.close();
                FileWriter writerLike = new FileWriter("C:\\Users\\NuricanB\\Desktop\\Social_Network_demo\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\likes\\"+friendInfo.get(3)+".txt",true);
                for (int j = 0; j<valOld.size()-1; j++){
                    System.out.println(j);
                    if (valOld.get(j).equals(hesapUsername)){
                        writerLike.write(valOld.get(j)+":"+"true"+"\n");
                    }else {
                        if (valOld.get(j) == "true" && valOld.get(j) == "false"){
                            continue;
                        }else {
                            System.out.println(valOld.get(j));
                            writerLike.write(valOld.get(j)+":"+valOld.get(j+1)+"\n");
                        }
                    }
                }
                writerLike.close();
                buttonClicked = false;
            }

    }

    @Override
    public void bilgiler() {
        friendMailLabel.setText(friendInfo.get(2));
        friendUsernameLabel.setText(friendInfo.get(3));
        String url = friendInfo.get(6);
        Image image = new Image(url);
        profilImage.setImage(image);
        friendWallLabel.setText("Hello to all my future friends from "+friendInfo.get(0)+" "+friendInfo.get(1)+".");
        likeCounter.setText(friendInfo.get(7));
        likeNumber = Integer.parseInt(likeCounter.getText());
    }
    public void logOutButtonClicked(ActionEvent event) throws IOException, wrongPassword, FileError, UserAbsent {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("seeMyFriends.fxml"));
        root = loader.load();
        seeMyFriendController seeMyfriendController = loader.getController();

        try {
            seeMyfriendController.Bilgiler(hesapUsername,hesapSifre);
            seeMyfriendController.KullaniciAl();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata Mesajı");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            System.out.println(ex.getMessage());
        }
    }
}
