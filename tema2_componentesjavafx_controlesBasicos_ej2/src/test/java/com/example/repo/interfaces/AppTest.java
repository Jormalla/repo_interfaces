package com.example.repo.interfaces;

// assertTrue comprueba que algo es verdadero; assertFalse, que es falso
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// @Test marca un método como prueba automática
import org.junit.jupiter.api.Test;

/**
 * Pruebas de la lógica del login.
 * Solo se prueba credencialesValidas(), porque la ventana en sí
 * no se puede comprobar sin abrirla.
 */
class AppTest {

    // Usuario y contraseña correctos: debe dar acceso
    @Test
    void credencialesCorrectasDanAcceso() {
        assertTrue(App.credencialesValidas("admin", "1234"));
    }

    // Usuario correcto pero contraseña equivocada: no debe dar acceso
    @Test
    void claveIncorrectaNoDaAcceso() {
        assertFalse(App.credencialesValidas("admin", "0000"));
    }

    // Contraseña correcta pero usuario equivocado: no debe dar acceso
    @Test
    void usuarioIncorrectoNoDaAcceso() {
        assertFalse(App.credencialesValidas("pepe", "1234"));
    }
}