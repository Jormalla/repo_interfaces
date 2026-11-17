package com.example.repo.interfaces;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Ejercicio10Menu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Submenu Fichero
        Menu ficheroSubMenu = new Menu("Fichero");
        MenuItem nuevo = new MenuItem("Nuevo");
        MenuItem abrir = new MenuItem("Abrir");
        MenuItem guardar =new MenuItem("Guardar");
        MenuItem salir =new MenuItem("Salir");
        ficheroSubMenu.getItems().addAll(nuevo, abrir, guardar, new SeparatorMenuItem(), salir);
        
        // Submenu Clientes
        Menu clientesSubMenu = new Menu("Clientes");
        MenuItem listarClientes = new MenuItem("Listar");
        MenuItem informeClientes = new MenuItem("Informe Clientes");
        clientesSubMenu.getItems().addAll(listarClientes, new SeparatorMenuItem(), informeClientes);
        
        // Submenu Productos
        Menu productosSubMenu = new Menu("Productos");
        MenuItem listarProductos = new MenuItem("Listar");
        MenuItem informeProductos = new MenuItem("Informe Productos");
        productosSubMenu.getItems().addAll(listarProductos, new SeparatorMenuItem(), informeProductos);
        
        // Creación barra menu
        MenuBar barraMenu = new MenuBar();
        barraMenu.getMenus().addAll(ficheroSubMenu, clientesSubMenu, productosSubMenu);
        
        // STAGE
        VBox contenedorPrincipal = new VBox(5, barraMenu);
        stage.setTitle("Menú");
        stage.setScene(new Scene(contenedorPrincipal, 800, 400));
        stage.show();
    }
}
