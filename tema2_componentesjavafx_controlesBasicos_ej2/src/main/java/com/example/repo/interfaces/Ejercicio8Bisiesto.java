package com.example.repo.interfaces;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Ejercicio8Bisiesto extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Método para averiguar si un año es bisiesto
     * @param anio anio que se desea saber si es bisiesto
     * @return devuelve true si es bisiesto, false si no lo es
     */
    boolean esBisiesto(int anio){
        return (anio % 4 == 0 && anio % 100 != 0 || anio % 400 == 0);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
      
        // PETICIÓN AÑO
        TextField tfAnio = new TextField();
        Button btnComprobar = new Button("Comprobar AÑO");
        HBox contenedorPeticion = new HBox(tfAnio, btnComprobar);
        contenedorPeticion.setAlignment(Pos.CENTER_LEFT);
        
        // LISTAS
        ObservableList<String> anios = FXCollections.observableArrayList();
        ListView lvAnios = new ListView(anios);
        
        // BOTON ACCIÓN
        btnComprobar.setOnAction(e -> {
            try {
                // Comprueba que sea bisiesto
                int anio = Integer.parseInt(tfAnio.getText());
                String mensajeBisiesto = "NO BISIESTO";
                if(esBisiesto(anio)){
                    mensajeBisiesto = "BISIESTO";
                }
                String mensajeListView = anio + " " + mensajeBisiesto;
                
                // Añade el mensaje al listView
                lvAnios.getItems().add(mensajeListView);
                
            // No ingresó un número
            } catch(NumberFormatException ex){
                Alert errorNumero = new Alert(Alert.AlertType.ERROR);
                errorNumero.setTitle("ERROR");
                errorNumero.setHeaderText("Datos incorrectos o incompletos");
                errorNumero.setContentText("Debes ingresar un número que represente al año");
                errorNumero.getDialogPane().setStyle("-fx-background-color: linear-gradient(to bottom right, #FFFFFF, #FFA1A1);");
                errorNumero.showAndWait();
            }
        });
        
        // CONTENEDOR PRINCIPAL
        VBox contenedorPrincipal = new VBox(10, lvAnios, contenedorPeticion);
        contenedorPrincipal.setPadding(new Insets(20));
        contenedorPrincipal.setAlignment(Pos.CENTER);
 
        // STAGE
        stage.setTitle("Calculador años bisiestos");
        stage.setScene(new Scene(contenedorPrincipal, 400, 200));
        stage.show();
    }
}
