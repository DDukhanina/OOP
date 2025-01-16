module com.example.go {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.go to javafx.fxml;
    exports com.example.go;
    exports com.example.go.visual;
    opens com.example.go.visual to javafx.fxml;
    exports com.example.go.logic;
    opens com.example.go.logic to javafx.fxml;
}