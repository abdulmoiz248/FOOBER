module com.example.foober {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.foober to javafx.fxml;
    exports com.example.foober;
}