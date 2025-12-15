module com.programstructure.gestiondesconsultationsdespatients {
    requires javafx.controls;
    requires javafx.fxml;

    exports model;
    exports controller to javafx.fxml;
    exports com.programstructure.gestiondesconsultationsdespatients;

    opens controller to javafx.fxml;
    opens view to javafx.fxml;
    opens com.programstructure.gestiondesconsultationsdespatients to javafx.graphics;
}