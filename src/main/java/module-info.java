module com.example.mubashir {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.j;
    requires javafx.base;


    opens com.example.mubashir to javafx.fxml;
    exports com.example.mubashir;
    exports Admin;
    opens Admin to javafx.fxml;
}