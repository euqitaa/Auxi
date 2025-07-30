module com.example.auxi {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.desktop;

    opens com.example.auxi to javafx.fxml;
    exports com.example.auxi;
    exports com.example.auxi.controller;
    opens com.example.auxi.controller to javafx.fxml;
    exports com.example.auxi.Saved;
    opens com.example.auxi.Saved to javafx.fxml;
    exports com.example.auxi.controller.serverandchat;
    opens com.example.auxi.controller.serverandchat to javafx.fxml;
}