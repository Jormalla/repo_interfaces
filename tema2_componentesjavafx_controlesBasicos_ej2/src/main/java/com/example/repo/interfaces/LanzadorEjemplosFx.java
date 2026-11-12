package com.example.repo.interfaces;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LanzadorEjemplosFx extends Application {

    private File archivoSeleccionado;
    private FileChooser fileChooser;
    private File ultimoDirectorio = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir clases ejemplo JavaFX");
        fileChooser.setInitialDirectory(new File("."));

        FileChooser.ExtensionFilter filtro =
                new FileChooser.ExtensionFilter("Ejemplos Clases FX", "*.java");

        fileChooser.getExtensionFilters().add(filtro);
        Button botonAbrir = new Button("Abrir FileChooser");
        botonAbrir.setOnAction(evento -> abrirEjemplo(primaryStage));
        String imageUrl =
                "https://upload.wikimedia.org/wikipedia/commons/3/30/JavaFX_text_logo.png";

        Image imagen = new Image(imageUrl);
        ImageView imageView = new ImageView(imagen);
        StackPane raiz = new StackPane(imageView, botonAbrir);
        Scene escena = new Scene(raiz, 430, 200);
        primaryStage.setTitle("Lanzador de Ejemplos JavaFX - 2DAM");
        primaryStage.setScene(escena);
        primaryStage.show();
    }

    private void abrirEjemplo(Stage primaryStage) {

        archivoSeleccionado = fileChooser.showOpenDialog(primaryStage);

        if (archivoSeleccionado == null) {
            System.out.println("Cancelado por el usuario.");
            return;
        }

        ultimoDirectorio = archivoSeleccionado.getParentFile();
        fileChooser.setInitialDirectory(ultimoDirectorio);
        System.out.println("Archivo seleccionado: "
                + archivoSeleccionado.getAbsolutePath());
        String nombreSimple = archivoSeleccionado.getName().replace(".java", "");
        String paquete = obtenerPaquete(archivoSeleccionado);
        if (paquete.isEmpty()) {
            mostrarError(
                    "El archivo seleccionado no declara un paquete.\n\n"
                    + "Añade al principio del archivo una línea como:\n"
                    + "package com.example.prueba;"
            );
            return;
        }

        String nombreCompleto = paquete + "." + nombreSimple;
        System.out.println("Paquete detectado: " + paquete);
        System.out.println("Clase: " + nombreCompleto);
        Class<?> clase;
        try {
            clase = Class.forName(nombreCompleto);
        } catch (ClassNotFoundException ex) {
            mostrarError(
                    "No se ha encontrado la clase compilada:\n\n"
                    + nombreCompleto
                    + "\n\nComprueba que:\n\n"
                    + "1. El archivo está dentro de src/main/java.\n"
                    + "2. La primera línea declara el paquete correcto.\n"
                    + "3. La estructura de carpetas coincide con el paquete.\n"
                    + "4. El proyecto se ha compilado correctamente."
            );
            return;
        }
        Object instancia;
        try {
            instancia = clase.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | SecurityException
                | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ex) {

            Logger.getLogger(LanzadorEjemplosFx.class.getName())
                    .log(Level.SEVERE, null, ex);

            mostrarError(
                    "No se ha podido crear una instancia de la clase:\n\n"
                    + nombreCompleto
                    + "\n\nLa clase debe ser pública y tener un constructor sin parámetros."
            );
            return;
        }
        if (!(instancia instanceof Application)) {
            mostrarError(
                    "La clase seleccionada no extiende javafx.application.Application.\n\n"
                    + "Clase: " + nombreCompleto
            );
            return;
        }
        Application aplicacion = (Application) instancia;
        Stage ventanaEjemplo = new Stage();
        ventanaEjemplo.initModality(Modality.APPLICATION_MODAL);

        try {
            System.out.println("Abriendo aplicación: " + nombreCompleto);
            aplicacion.start(ventanaEjemplo);
        } catch (Exception ex) {
            Logger.getLogger(LanzadorEjemplosFx.class.getName())
                    .log(Level.SEVERE, null, ex);

            mostrarError(
                    "Se ha producido un error al abrir la aplicación:\n\n"
                    + nombreCompleto
                    + "\n\nDetalle:\n"
                    + ex.toString()
            );
        }
    }

    private String obtenerPaquete(File archivoJava) {

        try (BufferedReader lector = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(archivoJava),
                        StandardCharsets.UTF_8
                )
        )) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                linea = linea.trim();
                if (linea.startsWith("package ")) {
                    return linea
                            .replace("package", "")
                            .replace(";", "")
                            .trim();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(LanzadorEjemplosFx.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return "";
    }

    private void mostrarError(String mensaje) {

        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error al abrir el ejemplo");
        alerta.setHeaderText("No se pudo abrir la clase JavaFX");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}