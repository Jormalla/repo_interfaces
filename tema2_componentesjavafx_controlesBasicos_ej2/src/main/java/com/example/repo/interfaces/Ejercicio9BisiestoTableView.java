package com.example.repo.interfaces;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Ejercicio9BisiestoTableView extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Clase Anio que simula un año
     */
    public static class Anio {

        private int anio;
        private boolean bisiesto;
        private String mensajeBisiesto;

        Anio(int anio) {
            this.anio = anio;
            this.bisiesto = (anio % 4 == 0 && anio % 100 != 0 || anio % 400 == 0);
        }

        // SETTERS & GETTERS
        public int getAnio() {
            return anio;
        }

        public boolean getBisiesto() {
            return bisiesto;
        }

        public String getMensajeBisiesto() {
            return (getBisiesto()) ? "BISIESTO" : "NO BISIESTO";
        }

    }

    @Override
    public void start(Stage stage) throws Exception {

        // PETICIÓN AÑO
        TextField tfAnio = new TextField();
        Button btnComprobar = new Button("Comprobar AÑO");
        HBox contenedorPeticion = new HBox(tfAnio, btnComprobar);
        contenedorPeticion.setAlignment(Pos.CENTER_LEFT);

        // TABLA
        ObservableList<Anio> anios = FXCollections.observableArrayList();
        TableView tvAnios = new TableView(anios);

        // Columna AÑO
        TableColumn<Anio, Integer> columnaAnio = new TableColumn("AÑO");
        columnaAnio.setCellValueFactory(new PropertyValueFactory("anio"));
        columnaAnio.setPrefWidth(150);

        // COLUMNA BISIESTO
        TableColumn<Anio, String> columnaBisiesto = new TableColumn("BISIESTO");
        columnaBisiesto.setCellValueFactory(new PropertyValueFactory("mensajeBisiesto"));
        columnaBisiesto.setPrefWidth(150);

        // AÑADE LAS COLUMNAS
        tvAnios.getColumns().addAll(columnaAnio, columnaBisiesto);

        // BOTON ACCIÓN
        btnComprobar.setOnAction(e -> {
            try {
                // Crea el año
                int numeroAnio = Integer.parseInt(tfAnio.getText());
                Anio anio = new Anio(numeroAnio);

                // Añade el año a la tabla
                tvAnios.getItems().add(anio);

                // No ingresó un número
            } catch (NumberFormatException ex) {
                Alert errorNumero = new Alert(Alert.AlertType.ERROR);
                errorNumero.setTitle("ERROR");
                errorNumero.setHeaderText("Datos incorrectos o incompletos");
                errorNumero.setContentText("Debes ingresar un número que represente al año");
                errorNumero.getDialogPane().setStyle("-fx-background-color: linear-gradient(to bottom right, #FFFFFF, #FFA1A1);");
                errorNumero.showAndWait();
            }
        });

        // CONTENEDOR PRINCIPAL
        VBox contenedorPrincipal = new VBox(10, tvAnios, contenedorPeticion);
        contenedorPrincipal.setPadding(new Insets(20));
        contenedorPrincipal.setAlignment(Pos.CENTER);

        // STAGE
        stage.setTitle("Calculador años bisiestos");
        stage.setScene(new Scene(contenedorPrincipal, 400, 200));
        stage.show();
    }
}
