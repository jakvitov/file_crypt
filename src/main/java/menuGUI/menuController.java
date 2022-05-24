package menuGUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class menuController {

    @FXML
    private Menu help;

    @FXML
    private MenuItem action;

    @FXML
    protected void showHelp(){
        System.out.println("Show help.");
    }
}