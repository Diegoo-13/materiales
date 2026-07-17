package utng.gtid2.dab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Esta es la clase principal. JavaFX utiliza esta clase para iniciar
 * el ciclo de vida de tu aplicación.
 */
public class App extends Application {

    /**
     * El método start es donde se configura y se muestra la ventana principal.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // FXMLLoader carga el diseño de la interfaz que creaste en el archivo FXML
        // y lo convierte en objetos que Java puede entender.
        Parent root = FXMLLoader.load(getClass().getResource("/utng/gtid2/dab/material.fxml"));
        
        // Configuramos el título y el tamaño de la ventana
        primaryStage.setTitle("Sistema de Gestión - Materiales");
        primaryStage.setScene(new Scene(root, 600, 450));
        
        // Hacemos visible la ventana
        primaryStage.show();
    }

    /**
     * El método main es el punto de inicio que llama al lanzador de JavaFX.
     */
    public static void main(String[] args) {
        launch(args);
    }
}