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

public class seeFriendWallController implements ProfileInfo{
    @FXML
    private Label friendLastnameLabel;

    @FXML
    private Label friendMailLabel;

    @FXML
    private Label friendWallLabel;

    @FXML
    private Label friendnameLabel;

    @FXML
    private Button likeButton;

    @FXML
    private Label likeCounter;

    @FXML
    private ImageView profileImage;

    @FXML
    private Label usernameLabel;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String hesapUsername;
    private String hesapSifre;
    private int likeNumber;

    private boolean buttonClicked;
    boolean durum;
    String likeUser;
    ArrayList<String> friendInfo = new ArrayList<>();
    HashMap<String, Boolean> likeValue = new HashMap<String, Boolean>();
    int sayac;

    public void DegerAl(ArrayList<String> Info, String hesapUsername, String hesapSifre){
        // seeMyFriendController sınıfından çağrılmaktadır
        // seçilen kullanıcının bilgilerini tutan arraylist başta o.ü. diğer bilgileri de alınmaktadır
        this.friendInfo = Info;
        this.hesapUsername = hesapUsername;
        this.hesapSifre = hesapSifre;
        sayac = 0; // ilk giren kişi için false atanıyor, sonrakiler için sayaç değeri arttırılmaktadır
    }

    @Override
    public void bilgiler() {
        // profileInfo interface sınıfından override edilmektedir
        ArrayList <AddFriendInfo> arkadasListe=new ArrayList<AddFriendInfo>(); // arraylistte nesne saklama
        // AddFriendInfo sınıfına ait nesneyi tutacak olan arkadasListe adlı arraylisti oluşturulmaktadır
        arkadasListe.add(new AddFriendInfo(friendInfo.get(0),friendInfo.get(1),friendInfo.get(2),friendInfo.get(6),friendInfo.get(7)));
        // isim,soyisim,mail, resmin uzantısı, beğeni sayısı
        AddFriendInfo friend = arkadasListe.get(0);

        usernameLabel.setText(friend.name); // AddFriendInfo nesnesi üzerinden ilgili çağrımlarla labellara bilgiler set edilmektedir
        String url = friend.url;
        Image  image= new Image(url);
        profileImage.setImage(image);
        friendnameLabel.setText(friend.name);
        friendLastnameLabel.setText(friend.lastname);
        friendMailLabel.setText(friend.mail);
        friendWallLabel.setText("Hello to all my future friends from "+friend.name+" "+friend.lastname+".");
        friendWallLabel.setWrapText(true);
        likeCounter.setText(friend.like);
        likeNumber = Integer.parseInt(likeCounter.getText());
    }

    public void LikeInfo() throws IOException {
        File likeFileUser = new File("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\likes\\"+friendInfo.get(3)+".txt"); // gelen kullanıcı için gittiğimiz arkadaşın dosyası açılıyor
        // duvarı görüntülenen kullanıcının likes dosyası verileri arrayliste atamak üzere açılmaktadır
        Scanner recentData = new Scanner(likeFileUser);
        ArrayList<String> valUsernameUser = new ArrayList<>();
        ArrayList<String> valLikeDurumUser = new ArrayList<>();
        int i=0; // verileri ayırma kısmı
        while(recentData.hasNext()){ // eda:true
            for(String s : recentData.nextLine().split(":")){ // : dan ayırıp s stringine atayacak
                if(i%2==0){
                    valUsernameUser.add(s); // çiftse kullanıcı adları ayrı bir arrayliste aktarılıyor
                }else{
                    valLikeDurumUser.add(s); // tekse true false bilgileri depolanıyor
                }
                i++;
            }
        }
        recentData.close();

        File likeFile = new File("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\likes\\"+friendInfo.get(3)+".txt");
        // duvarı görüntülenen kişinin likes dosyası açılıyor
        Scanner read = new Scanner(likeFile);
        int j = 0;
        while (read.hasNext()){
            for (String s: read.nextLine().split(":")){ // username ve true-false durumları iki kullanıcı için dört adet olduğundan
                if (j >= valUsernameUser.size()){ // kullanıcı sayısından daha fazla döngünün tekrar etmemesi için son kullanıcıya geldiğinde döngünün bitişini sağlar
                    j--;  // index out of range hatası vermemesi için j, bir azaltılmaktadır
                    break; // son kullanıcıya da baktığı için break ile döngüden çıkılıyor
                }
               // System.out.println(valUsernameUser.get(j)); // hangi kullanıcıların geldiğini gösteriyor
                if (valUsernameUser.get(j).equals(hesapUsername)){ // hesabına giren kullanıcı önceden profile bakmışsa
                    /* System.out.println(hesapUsername);
                    System.out.println(valUsernameUser.get(j)); */
                    this.likeUser = valUsernameUser.get(j); // önceden girmiş olan kullanıcının adı
                    this.durum = Boolean.parseBoolean(valLikeDurumUser.get(j)); // önceden girmiş olan kullanıcının durumu
                    this.buttonClicked = durum; // kullanıcının yaptığı işlemler genel değişkenlere atanmakta
                    break; // hesabına girilen kullanıcıyı bulduğu için döngüden çıkılıyor
                }else { // daha önce profili ziyaret etmemiş olan kullanıcının değerleri
                    this.likeUser = "";
                    this.durum = false;
                    this.buttonClicked = durum;

                }
                j++;
            }
            sayac++; // kaç kişinin profile girdiğini gösteriyor
        }
        read.close();

        ilkLike(sayac); // ilk ziyaret metodu çağrılıyor

        // diğer Durum- profile daha önce giren kullanıcı olmamışsa
        if (sayac != 0){
            FileWriter writerUser = new FileWriter("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\likes\\"+friendInfo.get(3)+".txt",true);
            if (likeUser.equals("")){ // ilk defa giren kullanıcı için "" değeri atanmıştı
               // Eğer duvar ilk kez ziyaret edilmiyorsa yani sayaç sıfır değilse ilk kez giren kullanıcının buton durumu false olarak atanmaktadır
                likeValue.put(hesapUsername, Boolean.FALSE); // ilk kez giren kullanıcı için false durumu atanmaktadır
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
            FileWriter writer = new FileWriter("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\likes\\"+friendInfo.get(3)+".txt",true);
            likeValue.put(hesapUsername,Boolean.FALSE); // likeValue dictionary listesidir ve hesabına giren kullanıcının adı ile ilk defa ziyaret ettiği için false-beğenmeme durumunu atanmaktadır
            for (Map.Entry<String, Boolean> entry : likeValue.entrySet()) {
                // put key and value separated by a colon
                writer.write(entry.getKey() + ":" + entry.getValue()+"\n"); // dictionary listesinde tutulan key ve value değerleri dosyaya yazdırılmaktadır
            }
            buttonClicked = false; // buton durumu false olarak atanmaktadır, ilk defa profili görüntülediği için
            writer.close();
        }
    }

    public void likeButtonDurum(){
        if (buttonClicked == Boolean.TRUE) { // daha önceden beğenmiş ve tekrar giriş yapmışsa da buton durumu değerleri daha önceden alınmış olduğu için tekrar girildiğinde de buton durumu yansıyacaktır
            Image initialImage = new Image("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\resources\\images\\heartRed.png");
            ImageView likeView = new ImageView(initialImage);
            likeView.setFitWidth(50);
            likeView.setFitHeight(50);
            likeButton.setGraphic(likeView);
            buttonClicked = true;
        }else {
            Image newImage = new Image("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\resources\\images\\heartWhite.png");
            ImageView disLikeView = new ImageView(newImage);
            disLikeView.setFitWidth(50);
            disLikeView.setFitHeight(50);
            likeButton.setGraphic(disLikeView);
            buttonClicked = false;
        }
    }
    public void LikeButton(ActionEvent event) throws IOException { // butona basıldığında olacak olaylar
        // Kullanıcı hesabının database dosyasını açma
        FileWriter fileData = new FileWriter("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\"+friendInfo.get(3)+".txt");

        if (!buttonClicked) {  // önceden beğenmediysen durumu için gerekli işlemler
            // (butona basılmadan önceki durumu için) buttonclicked == false olma durumu, beğenilmemiş
            System.out.println("Button like!");
            Image initialImage = new Image("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\resources\\images\\heartRed.png");
            ImageView likeView = new ImageView(initialImage);
            likeView.setFitWidth(50);
            likeView.setFitHeight(50);
            likeButton.setGraphic(likeView);

            likeNumber++; // beğeni sayısını arttırılmaktadır.
            likeCounter.setText(likeNumber+"");
            friendInfo.set(7,likeNumber+"");
            for (int i = 0; i < friendInfo.size(); i++){
                fileData.write(friendInfo.get(i)+"\n"); // yeni beğenilme sayısını dosyaya yazdırıyor
            }
            fileData.close();

            // like true olma
            File likeFile = new File("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\likes\\"+friendInfo.get(3)+".txt");
            Scanner read = new Scanner(likeFile);
            ArrayList<String> valUsername = new ArrayList<>();
            ArrayList<String> valLikeDurum = new ArrayList<>();
            int i = 0;
            while (read.hasNext()){
                for (String s: read.nextLine().split(":")){
                    if (i % 2 == 0){
                        valUsername.add(s); // kullanici adı
                    }else{
                        valLikeDurum.add(s);
                    }
                    i++;
                }
            }
            read.close();
            FileWriter writerLike = new FileWriter("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\likes\\"+friendInfo.get(3)+".txt");
            for (int j = 0; j<valUsername.size(); j++){
                System.out.println(j);
                if (valUsername.get(j).equals(hesapUsername)){ // hesapUsername, giren kullanıcıyı tutan değişken
                    writerLike.write(valUsername.get(j)+":"+"true"+"\n"); // default olarak true değerini atamış oluyoruz
                }else {
                   // System.out.println(valUsername.get(j));
                    writerLike.write(valUsername.get(j)+":"+valLikeDurum.get(j)+"\n");
                    // duvarı görüntüleyen kullanıcı, beğenme butonuna basmışken likes dosyasında girmeyenlerin key-value değerleri değiştirilmeden dosyaya yazdırılıyor
                }
            }
            writerLike.close();
            buttonClicked = true;

        }else {
            // buttonclicked == true olma durumu, yani beğenilmiş ve geri çekilirse
            Image newImage = new Image("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\resources\\images\\heartWhite.png");
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

            File likeFile = new File("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\likes\\"+friendInfo.get(3)+".txt");
            Scanner read = new Scanner(likeFile);
            ArrayList<String> valUsername = new ArrayList<>();
            ArrayList<String> valLikeDurum = new ArrayList<>();
            int i = 0;
            while (read.hasNext()){
                for (String s: read.nextLine().split(":")){
                    if (i % 2 == 0){
                        valUsername.add(s); // kullanici adı
                    }else{
                        valLikeDurum.add(s);
                    }
                    i++;
                }
            }
            read.close();

            FileWriter writerLike = new FileWriter("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\likes\\"+friendInfo.get(3)+".txt");
            for (int j = 0; j<valUsername.size(); j++){
                System.out.println(j);
                if (valUsername.get(j).equals(hesapUsername)){  // likes dosyasında beğenen kişiyi bulup true değerini false olarak değiştiriyor
                    writerLike.write(valUsername.get(j)+":"+"false"+"\n");
                }else {
                    System.out.println(valUsername.get(j));
                    writerLike.write(valUsername.get(j)+":"+valLikeDurum.get(j)+"\n");
                }
            }
            writerLike.close();
            buttonClicked = false;
        }
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
