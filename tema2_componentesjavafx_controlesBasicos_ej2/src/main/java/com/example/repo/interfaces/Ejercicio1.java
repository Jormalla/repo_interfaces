package com.example.repo.interfaces;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Ejercicio1 extends Application {

    @Override
    public void start(Stage stage) {
        Label lblPrecio = new Label("Precio:");
        TextField txtPrecio = new TextField();

        Label lblDescuento = new Label("% de descuento:");
        TextField txtDescuento = new TextField();

        Button btnCalcular = new Button("Calcular");

        btnCalcular.setOnAction(e -> {
            try {
                double precio = Double.parseDouble(txtPrecio.getText().replace(',', '.'));
                double descuento = Double.parseDouble(txtDescuento.getText().replace(',', '.'));

                double precioFinal = calcularPrecioFinal(precio, descuento);
                double importeDescuento = precio - precioFinal;

                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Resultado");
                alerta.setHeaderText("Descuento aplicado");
                alerta.setContentText(String.format(
                        "Descuento: %.2f € (%.2f%%)%nPrecio final: %.2f €",
                        importeDescuento, descuento, precioFinal));
                alerta.showAndWait();

            } catch (NumberFormatException ex) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Datos no válidos");
                error.setContentText("Introduce números válidos en el precio y el descuento.");
                error.showAndWait();
            }
        });
        
        HBox elementoBoton = new HBox(btnCalcular);
        elementoBoton.setAlignment(Pos.CENTER);

        VBox raiz = new VBox(10, lblPrecio, txtPrecio, lblDescuento, txtDescuento, elementoBoton);
        raiz.setPadding(new Insets(20));

        stage.setTitle("Calculadora de descuento");
        stage.setScene(new Scene(raiz, 300, 250));
        stage.show();
    }

    static double calcularPrecioFinal(double precio, double descuento) {
        return precio - precio * descuento / 100;
    }

    public static void main(String[] args) {
        launch(args);
    }
}