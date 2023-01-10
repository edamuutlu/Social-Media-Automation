package com.example.social_network_demo;

import com.example.social_network_demo.error.FileError;
import com.example.social_network_demo.error.UserAbsent;
import com.example.social_network_demo.error.wrongPassword;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField usernameTextField;

    // Pages
    public void registerButtonClicked(ActionEvent e) throws IOException {
        FXMLLoader loader = new  FXMLLoader(getClass().getResource("register.fxml"));
        root = loader.load();
        RegisterController register = loader.getController(); //RegisterController sınıfının bir nesnesi oluşturulmaktadır
        register.addImage(); // oluşturulan bu nesne üzerinden RegisterController sınıfındaki addImage() metodu başlangıçta çalıştırılacaktır
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
}
    public void loginCode(Event event) throws IOException{
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("profilePage.fxml"));
        root = loader.load();
        profilePageController loginController = loader.getController();
        // profilePageController sınıfının loginController adlı nesnesi oluşturulmaktadır
        try {
            if(username.isBlank() || password.isBlank()) {
                loginController.LoginController();  // OVERLOAD KULLANILMAKTADIR
                // loginController nesnesi üzerinden LoginController() parametresiz metot çağrılmaktadır
            }else {
                loginController.LoginController(username, password);  // OVERLOAD KULLANILMAKTADIR
                // loginController nesnesi üzerinden LoginController() parametreli metot çağrılmaktadır
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }catch (UserAbsent u) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information");
            alert.setContentText(u.getMessage());
            alert.showAndWait();
        } catch (wrongPassword w) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information");
            alert.setContentText(w.getMessage());
            alert.showAndWait();
        } catch (FileError fe) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information");
            alert.setContentText(fe.getMessage());
            alert.showAndWait();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata Mesajı");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            System.out.println(ex.getMessage());
        }
    }

    // Control of login event
    public void Login1(ActionEvent event) throws IOException {
            loginCode(event);
    }
    @FXML
    private void Login(KeyEvent event) throws IOException {
        if (event.getCode() == event.getCode().ENTER){
            loginCode(event);
        }
    }

    public void message(Alert alert){
        // profilePageController sınıfında polimorfizmi kullanabilmek üzere oluşturulan bir metot
    }
}
