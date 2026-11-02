package com.example.repo.interfaces;

import java.net.URL;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Ejercicio 2: formulario de login.
 * Contiene una imagen, un campo de usuario, un campo de contraseña y un botón.
 * Informa si se acierta y bloquea el acceso tras varios fallos.
 */
public class App extends Application {

    // Credenciales válidas (en una app real nunca irían escritas en el código)
    static final String USUARIO_CORRECTO = "admin";
    static final String CLAVE_CORRECTA = "1234";

    // Número de fallos a partir del cual se bloquea el acceso
    static final int MAX_FALLOS = 5;

    // Contador de intentos fallidos.
    int fallos = 0;

    @Override
    public void start(Stage stage) {

        // ---------- IMAGEN ----------
        // ImageView es el componente que muestra una imagen en pantalla
        ImageView imagen = new ImageView();
        Label lblSinImagen = new Label();

        // Busca logo.png dentro de src/main/resources (devuelve null si no existe)
        URL url = App.class.getResource("/logo.png");
        if (url != null) {
            imagen.setImage(new Image(url.toExternalForm()));
            imagen.setFitWidth(100);          // ancho máximo de 100 píxeles
            imagen.setPreserveRatio(true);    // mantiene las proporciones
        } else {
            // Si no hay imagen, avisa con un texto para que el programa no falle
            lblSinImagen.setText("(coloca logo.png en src/main/resources)");
        }

        // ---------- CAMPOS DE TEXTO ----------
        Label lblUsuario = new Label("Usuario:");
        TextField txtUsuario = new TextField();        // texto visible

        Label lblClave = new Label("Contraseña:");
        PasswordField txtClave = new PasswordField();  // oculta lo que se escribe

        // ---------- BOTÓN Y MENSAJE DE ESTADO ----------
        Button btnEntrar = new Button("Entrar");
        Label lblEstado = new Label();  // aquí se muestran los mensajes al usuario

        // ---------- ACCIÓN DEL BOTÓN ----------
        // Este código se ejecuta cada vez que se pulsa "Entrar"
        btnEntrar.setOnAction(e -> {

            // Comprueba las credenciales con el método de más abajo
            boolean correcto = credencialesValidas(txtUsuario.getText(), txtClave.getText());

            if (correcto) {
                // CASO 1: acierta
                lblEstado.setText("Acceso concedido");

                Alert ok = new Alert(Alert.AlertType.INFORMATION);
                ok.setTitle("Login");
                ok.setHeaderText("Bienvenido");
                ok.setContentText("Has accedido correctamente.");
                ok.showAndWait();  // muestra el aviso y espera a que se cierre

                fallos = 0;  // reinicia el contador
            } else {
                // CASO 2: se equivoca
                fallos++;

                if (fallos >= MAX_FALLOS) {
                    // CASO 2a: llega al máximo de fallos, se bloquea todo
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

        // ---------- DISEÑO DE LA VENTANA ----------
        // VBox apila los componentes en vertical, con 10 píxeles entre ellos
        VBox raiz = new VBox(10, imagen, lblSinImagen, lblUsuario, txtUsuario,
                lblClave, txtClave, btnEntrar, lblEstado);
        raiz.setPadding(new Insets(20));  // margen interior de 20 píxeles

        // Stage = ventana, Scene = su contenido
        stage.setTitle("Login");
        stage.setScene(new Scene(raiz, 500, 400));
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
