module com.example.wordscrambler {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens com.example.wordscrambler to javafx.fxml;
    exports com.example.wordscrambler;
}