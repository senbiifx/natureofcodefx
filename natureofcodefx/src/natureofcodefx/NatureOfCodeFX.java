package natureofcodefx;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class NatureOfCodeFX extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("MainView.fxml"));
        Parent root = (Parent) loader.load();
        Rectangle2D rec = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, rec.getWidth(), rec.getHeight());
        MainController controller = loader.getController();
        primaryStage.setOnShown(e -> {
            controller.handleWindowShowEvent();
        });
        controller.setHostServices(getHostServices());
        primaryStage.setTitle("The Nature of Code in JavaFX Canvas");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        NatureOfCodeFX.launch((String[]) args);
    }
}
