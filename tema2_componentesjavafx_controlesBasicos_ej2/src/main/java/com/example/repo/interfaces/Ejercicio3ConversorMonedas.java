package com.example.repo.interfaces;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Ejercicio3ConversorMonedas extends Application {
      
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Creacion de elementos del programa
        Label lbCantidadOriginal = new Label("Cantidad de euros a convertir:");
        TextField txCantidadOriginal = new TextField();
        Label lbTiposMonedas = new Label("Tipo de moneda");
        ChoiceBox<String> cbTipoMonedas = new ChoiceBox<>(){
            {
                getItems().addAll("Dólares($)", "Libras (£)", "Yenes (¥)");
            }
        };
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
                String opcionElegida = cbTipoMonedas.getValue();
                
                if (opcionElegida == null) {
                    throw new Exception();
                }
                
                // Dólares
                if(opcionElegida.equals("Dólares($)")){
                    lbConversion.setText(String.format("%.2f", cantidad * DOLARES) + " $");
                // Libras
                } else if(opcionElegida.equals("Libras")){
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
        VBox contenedorCantidad = new VBox(lbCantidadOriginal, txCantidadOriginal);
        VBox contenedorTipoMoneda = new VBox(lbTiposMonedas, cbTipoMonedas);
        VBox contenedorPrincipal = new VBox(10, contenedorCantidad, contenedorTipoMoneda, lbConversion, btnTransformar);
        contenedorPrincipal.setAlignment(Pos.CENTER);
        
        // Ventana donde se muestra
        contenedorPrincipal.setPadding(new Insets(20));  // margen interior de 20 píxeles
        // Stage = ventana, Scene = su contenido
        stage.setTitle("Conversor monedas");
        stage.setScene(new Scene(contenedorPrincipal, 300, 200));
        stage.show();
    }
}
