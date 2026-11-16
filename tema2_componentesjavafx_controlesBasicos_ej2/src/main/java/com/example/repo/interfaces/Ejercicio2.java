package com.example.repo.interfaces;

import java.net.URL;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Ejercicio 2: formulario de login.
 * Contiene una imagen, un campo de usuario, un campo de contraseña y un botón.
 * Informa si se acierta y bloquea el acceso tras varios fallos.
 */
public class Ejercicio2 extends Application {

    // Credenciales válidas
    static final String USUARIO_CORRECTO = "admin";
    static final String CLAVE_CORRECTA = "1234";

    // Número de fallos a partir del cual se bloquea el acceso
    static final int MAX_FALLOS = 3;

    // Contador de intentos fallidos.
    int fallos = 0;

    @Override
    public void start(Stage stage) {

        // IMAGEN 
        ImageView imagen = new ImageView();
        Label lblSinImagen = new Label();

        // Busca logo.png dentro de src/main/resources (devuelve null si no existe)
        URL url = Ejercicio2.class.getResource("/logo.png");
        if (url != null) {
            imagen.setImage(new Image(url.toExternalForm()));
            imagen.setFitWidth(100);          // ancho máximo de 100 píxeles
            imagen.setPreserveRatio(true);    // mantiene las proporciones
        } else {
            // Texto en caso de que no este la imagen
            lblSinImagen.setText("(coloca logo.png en src/main/resources)");
        }

        // CAMPOS DE TEXTO
        Label lblUsuario = new Label("Usuario:");
        TextField txtUsuario = new TextField();     

        Label lblClave = new Label("Contraseña:");
        PasswordField txtClave = new PasswordField(); 

        //bOTÓN Y MENSAJE DE ESTADO
        Button btnEntrar = new Button("Entrar");
        Label lblEstado = new Label(); 

        // ACCIÓN DEL BOTÓN
        btnEntrar.setOnAction(e -> {

            // Comprueba las credenciales
            boolean correcto = credencialesValidas(txtUsuario.getText(), txtClave.getText());

            if (correcto) {
                // ACIERTA
                lblEstado.setText("Acceso concedido");

                Alert ok = new Alert(Alert.AlertType.INFORMATION);
                ok.setTitle("Login");
                ok.setHeaderText("Bienvenido");
                ok.setContentText("Has accedido correctamente.");
                ok.showAndWait(); 

                fallos = 0;
            } else {
                // SE EQUIVOCA
                fallos++;

                if (fallos >= MAX_FALLOS) {
                    // llega al máximo de fallos, se bloquea todo
                    lblEstado.setText("Demasiados intentos. Acceso bloqueado.");

                    // Desactiva los componentes para que no se pueda seguir probando
                    btnEntrar.setDisable(true);
                    txtUsuario.setDisable(true);
                    txtClave.setDisable(true);

                    Alert bloqueo = new Alert(Alert.AlertType.ERROR);
                    bloqueo.setTitle("Login");
                    bloqueo.setHeaderText("Acceso bloqueado");
                    bloqueo.setContentText("Has fallado " + MAX_FALLOS + " veces.");
                    bloqueo.showAndWait();
                } else {
                    // CASO 2b: aún quedan intentos, se informa de cuántos
                    lblEstado.setText("Datos incorrectos. Intentos restantes: "
                            + (MAX_FALLOS - fallos));
                }
            }
        });

        // DISEÑO DE LA VENTANA
        HBox contenedorImagen = new HBox(imagen);
        contenedorImagen.setAlignment(Pos.CENTER);
        
        HBox contenedorBoton = new HBox(btnEntrar);
        contenedorBoton.setAlignment(Pos.CENTER);
        
        VBox raiz = new VBox(5, contenedorImagen, lblSinImagen, lblUsuario, txtUsuario,
                lblClave, txtClave, contenedorBoton, lblEstado);
        raiz.setPadding(new Insets(20)); 

        stage.setTitle("Login");
        stage.setScene(new Scene(raiz, 250, 300));
        stage.show();
    }

    /**
     * Comprueba si el usuario y la contraseña son los correctos.
     * Está fuera de start() para poder probarlo desde AppTest.
     * Se usa equals() para comparar textos, nunca ==.
     * @param usuario usuario que se quiere introducir
     * @param clave contraseña que se va introducir
     * @return devuelve true o false dependiendo de si es correcto o no
     */
    public static boolean credencialesValidas(String usuario, String clave) {
        return USUARIO_CORRECTO.equals(usuario) && CLAVE_CORRECTA.equals(clave);
    }

    // Punto de entrada: launch() arranca JavaFX y llama a start()
    public static void main(String[] args) {
        launch(args);
    }
}
