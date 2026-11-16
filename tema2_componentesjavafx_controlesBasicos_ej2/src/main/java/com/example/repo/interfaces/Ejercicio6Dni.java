package com.example.repo.interfaces;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Ejercicio6Dni extends Application {
    
    final int CANTIDAD_DIGITOS_DNI = 8;
    final int NUMERO_DIVISOR_DNI = 23;
    final char[] LETRAS = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Creación de componentes
        Label lbDni = new Label("DNI:");
        TextField tfDni = new TextField();
        CheckBox cbCalcularLetra = new CheckBox("Calcular letra");
        Button boton = new Button("VALIDAR");
        
        // Acción del botón para validar
        boton.setOnAction(e -> {
            try {
                int dni = Integer.parseInt(tfDni.getText().trim());
                
                // ERROR CANTIDAD DIGITOS != DNI
                if(!(tfDni.getText().trim().length() == CANTIDAD_DIGITOS_DNI)){
                    Alert errorDigitos = new Alert(Alert.AlertType.ERROR);
                    errorDigitos.setTitle("ERROR");
                    errorDigitos.setHeaderText("Datos incorrectos");
                    errorDigitos.setContentText("El dni debe ser tener 8 dígitos");
                    errorDigitos.getDialogPane().setStyle("-fx-background-color: linear-gradient(to bottom right, #FFFFFF, #FFA1A1);");
                    errorDigitos.showAndWait();
                  // DNI VÁLIDO
                } else {
                    Alert correcto = new Alert(Alert.AlertType.INFORMATION);
                    correcto.setTitle("VALIDADO");
                    correcto.setHeaderText("DNI VALIDADO");
                    correcto.setContentText("El dni ha sido validado correctamente");
                    
                    // ESTÁ CHECKED, CALCULA LA LETRA - PERSONALIZA EL MENSAJE
                    if (cbCalcularLetra.isSelected()){
                       int moduloDni = dni % NUMERO_DIVISOR_DNI;
                       char letraDni = LETRAS[moduloDni];
                       correcto.setContentText(correcto.getContentText() + "\nDNI: " + dni + "-" + letraDni);
                    }
                    // Cambio el color a un degradado de la alerta
                    correcto.getDialogPane().setStyle("-fx-background-color: linear-gradient(to bottom right, #FFFFFF, #C9FFC9);");
                    correcto.showAndWait();
                }
            
            // ERROR DNI NO NUMÉRICO
            }catch(NumberFormatException ex){
                Alert errorNumero = new Alert(Alert.AlertType.ERROR);
                errorNumero.setTitle("ERROR");
                errorNumero.setHeaderText("Datos incorrectos o incompletos");
                errorNumero.setContentText("El dni debe ser numérico. No introduzcas letras.");
                errorNumero.getDialogPane().setStyle("-fx-background-color: linear-gradient(to bottom right, #FFFFFF, #FFA1A1);");
                errorNumero.showAndWait();
            }
        });
        // Creación de contenedores
        VBox contenedorDni = new VBox(5, lbDni, tfDni);
        VBox contenedorPrincipal = new VBox(10, contenedorDni, cbCalcularLetra, boton);
        contenedorPrincipal.setPadding(new Insets(20));
        contenedorPrincipal.setAlignment(Pos.CENTER);
        // Creación de la escena
        stage.setTitle("Validador de DNI");
        stage.setScene(new Scene(contenedorPrincipal, 300, 150));
        stage.show();
    }
}
