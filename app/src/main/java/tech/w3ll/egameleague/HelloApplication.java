package tech.w3ll.egameleague;

import com.jpro.webapi.JProApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends JProApplication {

    @Override
    public void start(Stage stage) throws IOException {
        URL fxmlLocation = HelloApplication.class.getResource("telas/TelaMenu.fxml");

        if (fxmlLocation == null) {
            System.out.println("Arquivo FXML não encontrado!");
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("eGameLeague - Tabela do Torneio");
        stage.sizeToScene();
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
