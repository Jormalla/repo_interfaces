package com.example.repo.interfaces;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;


public class Ejercicio5Billetes extends Application {
    // Monedas y billetes
    final double[] VALORES = {500, 200, 100, 50, 20, 10, 5, 2, 1, 0.50, 0.20, 0.10, 0.05, 0.02, 0.01};
    final String[] NOMBRES = {"500€", "200€", "100€", "50€", "20€", "10€", "5€","2€", "1€", "50cts", "20cts", "10cts", "5cts", "2cts", "1cts"};
      
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
      // Precio
      Label lbPrecio = new Label("Precio:");
      TextField txPrecio = new TextField();
      VBox contenedorPrecio = new VBox(5, lbPrecio, txPrecio);
      
      // Dinero
      Label lbDinero = new Label("Dinero:");
      TextField txDinero = new TextField();
      VBox contenedorDinero = new VBox(5, lbDinero, txDinero);
      
      // Botón
      Button btnCalcular = new Button("Calcular");
      
      // Respuesta
      Label solucion = new Label();
      
      // Acción del botón
      btnCalcular.setOnAction(e ->{
           // Transformaciones de datos
        try {
            Double precioIntroducido = Double.parseDouble(txPrecio.getText().replace(",","."));
            Double dineroIntroducido = Double.parseDouble(txDinero.getText().replace(",","."));

            // Dinero insuficiente
            if (dineroIntroducido < precioIntroducido) {
                solucion.setText("Dinero insuficiente");
            } else {
                // Para redondear, a veces da fallo al transformar (cosas internas de java)
                int centimos = (int) Math.round((dineroIntroducido - precioIntroducido) * 100);
                // Para que sigan la regla de 2 digitos de decimales
                StringBuilder sb = new StringBuilder(String.format("Cambio: %.2f €", centimos / 100.0));
                // Para que añada una linea si hay cambio
                if (centimos > 0){
                    sb.append("\n");
                }
                for (int i = 0; i < VALORES.length; i++) {
                    int valor = (int) Math.round(VALORES[i] * 100);
                    int cantidad = centimos / valor;
                    if (cantidad > 0) {
                        sb.append(" | ").append(NOMBRES[i]).append(": ").append(cantidad).append("|");
                        centimos %= valor;
                    }
                }
                solucion.setText(sb.toString());
            }
        // Error al transformar datos
        } catch(NumberFormatException ex){
             Alert error = new Alert(Alert.AlertType.ERROR);
             error.setTitle("ERROR");
             error.setHeaderText("Datos incorrectos o incompletos");
             error.setContentText("Revisa los datos que introdujistes, las cantidades debe ser un número.");
             error.showAndWait();
        }
      });
      
      // Creación de contenedor principal
      VBox contenedorPrincipal = new VBox(10, contenedorPrecio, contenedorDinero, solucion, btnCalcular);
      contenedorPrincipal.setAlignment(Pos.CENTER);
      contenedorPrincipal.setPadding(new Insets(20));
      
      // Creación de escena
       // Configuración de la escena
        stage.setTitle("Calculador de cambio");
        stage.setScene(new Scene(contenedorPrincipal, 500, 250));
        stage.show();
    }
}
