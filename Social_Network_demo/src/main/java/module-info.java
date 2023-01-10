module com.example.social_network_demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.social_network_demo to javafx.fxml;
    exports com.example.social_network_demo;
}