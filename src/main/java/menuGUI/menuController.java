package menuGUI;

import backend.ControllBackend;
import crypt_functions.CryptKey;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class menuController {

    private ControllBackend backend;

    @FXML
    private Label loadedLabel;

    @FXML
    private Menu help;

    @FXML
    private MenuItem action;

    @FXML
    private Menu keys;

    @FXML
    private MenuItem genKey;

    @FXML
    private MenuItem loadKey;

    public menuController(){
        backend = new ControllBackend();
    }

    @FXML
    protected void generateKey(){
        FileChooser fileChoser = new FileChooser();
        fileChoser.setTitle("Generate new key");
        File chosen = fileChoser.showSaveDialog((Stage) loadedLabel.getScene().getWindow());
        if (chosen != null){
            try {
                backend.generateKey();
                backend.saveKey(chosen.toPath());
            }
            catch (NoSuchAlgorithmException | IOException NSAE){
                fireAlert("Error while creating the key, check if you have write permission \n " +
                        "in the given directory", "Error");
                return;
            }
            fireAlert("Key " + chosen.getName() + ".key " + " sucessfully saved", "Success");
        }
    }

    @FXML
    protected void loadKeyAction(){
        FileChooser fileChoser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Key files (*.key)", "*.key");
        fileChoser.getExtensionFilters().add(extFilter);
        fileChoser.setTitle("Load key");
        File chosen = fileChoser.showOpenDialog((Stage) loadedLabel.getScene().getWindow());

        if (chosen != null){
            try {
                System.out.println(chosen.getAbsolutePath().toString());
                backend.loadKey(chosen.toPath());
            }
            catch (ClassNotFoundException | IOException NSAE){
                fireAlert("Error while loading, damaged key.", "Error");
                return;
            }
        }
        loadedLabel.setText("Loaded: " + chosen.getName().toString());

    }

    @FXML
    protected void showHelp(){
        System.out.println("Show help.");
    }

    /** Fire a basic alert with given text*/
    public void fireAlert(String text, String title){
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.show();
    }
}