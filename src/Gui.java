import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Gui extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ui/gui/Main.fxml")));
        primaryStage.setTitle("MediaFileOrganisation");
        primaryStage.setScene(new Scene(root, 1280, 800));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
