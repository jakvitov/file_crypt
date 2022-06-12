package menuGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class menuInterface extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(menuInterface.class.getResource("menuView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 300);
        stage.setTitle("Caesar encryption");
        try {
            stage.getIcons().add(new Image("/home/jakub/Java/CryptSw/src/main/resources/images/icon.png"));
        }
        catch (Exception e){
            //todo implement logging of this
        }
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}