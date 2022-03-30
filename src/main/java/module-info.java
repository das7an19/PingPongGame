module com.example.lesson {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gane to javafx.fxml;
    exports com.example.gane;
}