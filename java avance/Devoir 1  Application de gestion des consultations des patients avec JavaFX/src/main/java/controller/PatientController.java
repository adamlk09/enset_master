package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Patient;

import java.time.LocalDate;

public class PatientController {

    @FXML private TextField nameField;
    @FXML private TextField ageField;
    @FXML private DatePicker datePicker;
    @FXML private ListView<Patient> patientListView;

    private ObservableList<Patient> patientList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        patientListView.setItems(patientList);
    }

    @FXML
    private void handleAddPatient() {
        String name = nameField.getText();
        String ageText = ageField.getText();
        LocalDate date = datePicker.getValue();

        if (name.isEmpty() || ageText.isEmpty() || date == null) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }

        try {
            int age = Integer.parseInt(ageText);
            Patient patient = new Patient(name, age, date);
            patientList.add(patient);
            nameField.clear();
            ageField.clear();
            datePicker.setValue(null);
        } catch (NumberFormatException e) {
            showAlert("L'âge doit être un nombre.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}