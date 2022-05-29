package menuGUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ManualController {

    @FXML
    private Label manualText;

    @FXML
    private ListView manualMenu;

    /** We fill the menu list in the initialize method*/
    public void initialize(){
        manualMenu.getItems().add("Encryption");
        manualMenu.getItems().add("Decryption");
        manualMenu.getItems().add("Keys and pins");
    }
}
