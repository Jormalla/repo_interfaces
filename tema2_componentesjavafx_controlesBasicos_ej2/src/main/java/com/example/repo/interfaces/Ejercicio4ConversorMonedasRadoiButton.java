package com.example.repo.interfaces;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Ejercicio4ConversorMonedasRadoiButton extends Application {
      
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Creacion de elementos del programa
        Label lbCantidadOriginal = new Label("Cantidad de euros a convertir:");
        TextField txCantidadOriginal = new TextField();
        Label lbTiposMonedas = new Label("Tipo de moneda");
        ToggleGroup grupoRadioButton = new ToggleGroup();
        grupoRadioButton.getToggles().addAll(
            new RadioButton("Dólares($)"),
            new RadioButton("Libras (£)"),
            new RadioButton("Yenes (¥)")
         );
        
        Label lbConversion = new Label();
        Button btnTransformar = new Button("Transformar");
        final double LIBRAS = 0.84;
        final double DOLARES = 1.10;
        final double YENES = 177;
        
        // Calculo de cambio monedas
        btnTransformar.setOnAction(e -> {
            try{
                // Coge los datos
                double cantidad = Double.parseDouble(txCantidadOriginal.getText().replace(",","."));
                RadioButton opcionElegida = (RadioButton) grupoRadioButton.getSelectedToggle();
                
                if (opcionElegida == null) {
                    throw new Exception();
                }
                
                // Dólares
                if(opcionElegida.getText().equals("Dólares($)")){
                    lbConversion.setText(String.format("%.2f", cantidad * DOLARES) + " $");
                // Libras
                } else if(opcionElegida.getText().equals("Libras (£)")){
                    lbConversion.setText(String.format("%.2f", cantidad * LIBRAS) + " £");
                // Yenes
                } else {
                    lbConversion.setText(String.format("%.2f", cantidad * YENES) + " ¥");
                }
                
            }catch(Exception ex){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("ERROR");
                error.setHeaderText("Datos incorrectos o incompletos");
                error.setContentText("Revisa los datos que introdujistes, la cantidad debe ser un número y debes seleccionar un tipo de moneda");
                error.showAndWait();
            }
        });
        
       // Construcción del contenedor
        VBox contenedorCantidad = new VBox(5, lbCantidadOriginal, txCantidadOriginal);
        
        // Se crea el VBox con el Label y luego se añaden los RadioButton del grupo
        VBox contenedorTipoMoneda = new VBox(5, lbTiposMonedas);
        for (Toggle toggleButton : grupoRadioButton.getToggles()) {
            contenedorTipoMoneda.getChildren().add((RadioButton) toggleButton);
        }
        
        VBox contenedorPrincipal = new VBox(10, contenedorCantidad, contenedorTipoMoneda, lbConversion, btnTransformar);
        contenedorPrincipal.setAlignment(Pos.CENTER);
        contenedorPrincipal.setPadding(new Insets(20));
        
        // Configuración de la escena
        stage.setTitle("Conversor monedas");
        stage.setScene(new Scene(contenedorPrincipal, 300, 250));
        stage.show();
    }
}
