package com.example.repo.interfaces;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Ejercicio7CalculadorNotas extends Application {
    
    final int NOTA_MAXIMA = 10;
    final int NOTA_MINIMA = 0;
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        // Creación de componentes
        Label lbNota = new Label("Nota:");
        TextField tfNota = new TextField();
        VBox contenedorPeticion = new VBox(5, lbNota, tfNota);
        Button btnAniadir = new Button("Añadir");
        Button btnCalcular = new Button("Calcular");
        // Lista observable de notas
        ObservableList<Double> notas = FXCollections.observableArrayList();
        // List view de las notas
        ListView lvNotas = new ListView(notas);
        
        // ACCIONES DE BOTONES
        // AÑADIR NOTA
        btnAniadir.setOnMouseClicked(e -> {
            try{
                
                double nota = Double.parseDouble(tfNota.getText().replace(",", "."));
                // No está en el rango adecuado
                if (nota > NOTA_MAXIMA || nota < NOTA_MINIMA){
                    throw new NumberFormatException();
                }
                // Añade la nota
                lvNotas.getItems().add(nota);
            
                // ERROR - El campo no estaba correcto
            }catch(NumberFormatException ex){
                Alert errorNumero = new Alert(Alert.AlertType.ERROR);
                errorNumero.setTitle("ERROR");
                errorNumero.setHeaderText("Datos incorrectos o incompletos");
                errorNumero.setContentText("Debes ingresar un nota numérica entre " + NOTA_MINIMA + " y " + NOTA_MAXIMA);
                errorNumero.getDialogPane().setStyle("-fx-background-color: linear-gradient(to bottom right, #FFFFFF, #FFA1A1);");
                errorNumero.showAndWait();
            }
        });
        // CALCULAR NOTAS
        btnCalcular.setOnMouseClicked(e -> {
            String mensaje = "Aun no hay notas";
            // Hay notas
            if(notas.size() > 0){
                double sumatoria = 0;
                double notaMasAlta = 0;
                double notaMasBaja = notas.get(0);
                
                // Recorre las notas
                for (double nota: notas){
                    sumatoria += nota;
                    // Nota más alta
                    if (notaMasAlta < nota){
                        notaMasAlta = nota;
                    }
                    // Nota más baja
                    if (notaMasBaja > nota){
                        notaMasBaja = nota;
                    }
                }
                // Calculo de la media
                double media = sumatoria / notas.size();
                mensaje = "Media: " + String.format("%.2f", media) + "\nNota más alta: " + String.format("%.2f", notaMasAlta) + "\nNota más baja: " + String.format("%.2f", notaMasBaja);
                // Cuadro de información de las notas
                Alert informacion = new Alert(Alert.AlertType.INFORMATION);
                informacion.setTitle("CALCULAR");
                informacion.setHeaderText("INFORMACIÓN DE LAS NOTAS");
                informacion.setContentText(mensaje);
                informacion.getDialogPane().setStyle("-fx-background-color: linear-gradient(to bottom right, #FFFFFF, #BFE5FF);");
                informacion.showAndWait();
                return;
            }
            
             Alert errorNumero = new Alert(Alert.AlertType.WARNING);
             errorNumero.setTitle("ERROR");
             errorNumero.setHeaderText("¡UPS!");
             errorNumero.setContentText("Parece que aun no hay notas");
             errorNumero.getDialogPane().setStyle("-fx-background-color: linear-gradient(to bottom right, #FFFFFF, #FFCC73);");
             errorNumero.showAndWait();
        });
        
        // Construcción de contenedor principal
        VBox contenedorPrincipal = new VBox(10, contenedorPeticion, btnAniadir, lvNotas, btnCalcular);
        contenedorPrincipal.setPadding(new Insets(20));
        contenedorPrincipal.setAlignment(Pos.CENTER_LEFT);
        
        // STAGE
        stage.setTitle("Calculador notas");
        stage.setScene(new Scene(contenedorPrincipal, 400, 400));
        stage.show();
    }
}
