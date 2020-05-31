package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class GuiApplication extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        stage.setResizable(false);
        FXMLLoader loader = new FXMLLoader();
        String fileLocation = "gui.fxml";
        FileInputStream fxmlStream = new FileInputStream(fileLocation);
        TabPane root = (TabPane) loader.load(fxmlStream);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("KSR 2: Artur Karbowski & Piotr Ruciński");
        stage.show();
    }
}
