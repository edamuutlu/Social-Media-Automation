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
    @FXML
    private ListView<String> friendsListView;
    @FXML
    private Label secilenUser;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String username;
    private String pass;
    ObservableList<String> list = FXCollections.observableArrayList(); // listview için oluşturulmaktadır
    ArrayList<String> tumKullanicilar = new ArrayList<>();
    ArrayList<String> Arkadaslarim = new ArrayList<>();


    public void KullaniciAl() throws FileNotFoundException {
        // profilePageController sınıfından çağrılmaktadır
        File file = new File("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase");
        List<File> users = Arrays.stream(file.listFiles()).toList();
        for(int i = 0; i<file.list().length;i++){
            if (users.get(i).isFile()){
                tumKullanicilar.add(users.get(i).getName().replace(".txt",""));
                // kullanıcı isimleri dosya adından çekildiği için .txt kısmı boş stringle yer değiştirilmekte yani silinmektedir
            }
        }
        File friData = new File("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\friends\\"+getUsername()+".txt");
        // kullanıcıların arkadaşlarının tutulduğu dosya, arkadaş ekleme kısmında zaten arkadaşı olanları göstermemek için açılmaktadır
        Scanner read = new Scanner(friData);
        while (read.hasNext()){
            Arkadaslarim.add(read.nextLine());
        }
        read.close();
        for (int j = 0; j < tumKullanicilar.size();j++){
            if (tumKullanicilar.get(j).equals(getUsername())){
                continue; // arkadaşları görüntülerken kendi isminin listelenmemesi  sağlanmaktadır
            }else {
                list.add(tumKullanicilar.get(j));
            }
        }
        for (int i = 0; i < Arkadaslarim.size(); i++){
            list.remove(Arkadaslarim.get(i));
            // arkadaş ekleme listesine, giriş yapan kullanıcı ve kendi arkadaşları haricindeki kullanıcılar listelenicektir
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
        try {
            String secilenKullanici = friendsListView.getSelectionModel().getSelectedItem().toString();
            File file = new File("C:\\Users\\edamu\\Desktop\\Social_Network_demo\\src\\main\\java\\com\\example\\social_network_demo\\dataBase\\friends\\"+getUsername()+".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
            writer.write(secilenKullanici+"\n");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText(secilenKullanici+ " added as friend");
            alert.showAndWait();
            writer.close();
            list.clear(); // arkadaş ekleme işleminden sonra listview bilgileri temizleniyor
            tumKullanicilar.clear(); // kişinin kendisinin de dahil olduğu liste temizleniyor
            Arkadaslarim.clear(); // kullanıcının arkadaşlarının olduğu liste temizleniyor
            KullaniciAl(); // güncellenmiş listview için KullaniciAl() metodu ile gösterilecek kullanıcılar tekrar belirlenmektedir
        }catch (NullPointerException npe){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("You do not make a choise!");
            alert.showAndWait();
        }
    }
    public void Bilgiler(String username,String pass){
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
}
