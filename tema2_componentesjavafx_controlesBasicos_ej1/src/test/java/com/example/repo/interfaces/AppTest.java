package com.example.repo.interfaces;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void descuentoDel20PorCientoSobre100() {
        assertEquals(80.0, App.calcularPrecioFinal(100, 20), 0.001);
    }

    @Test
    void sinDescuentoElPrecioNoCambia() {
        assertEquals(50.0, App.calcularPrecioFinal(50, 0), 0.001);
    }
}