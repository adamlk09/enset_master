module com.programstructure.gestiondesproduitsjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.programstructure.gestiondesproduitsjavafx to javafx.fxml;
    exports com.programstructure.gestiondesproduitsjavafx;
    exports controller;
    opens controller to javafx.fxml;
    exports model;
    opens model to javafx.fxml;
}