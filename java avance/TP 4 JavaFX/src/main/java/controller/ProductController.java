package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Product;

public class ProductController {

    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private ListView<Product> productListView;

    private ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        productListView.setItems(productList);
    }

    @FXML
    private void handleAddProduct() {
        String name = nameField.getText();
        String priceText = priceField.getText();

        if (name.isEmpty() || priceText.isEmpty()) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            Product product = new Product(name, price);
            productList.add(product);
            nameField.clear();
            priceField.clear();
        } catch (NumberFormatException e) {
            showAlert("Le prix doit être un nombre.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}