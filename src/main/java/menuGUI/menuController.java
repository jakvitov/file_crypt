package menuGUI;

import backend.ControllBackend;
import crypt_functions.CryptKey;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

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

    @FXML
    private Menu encryption;

    @FXML
    private MenuItem encryptKey;

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

    }

    @FXML
    protected void keyEncryptAction(){
        if (backend.isKeyLoaded() == false){
            fireAlert("No key is loaded. Load a new key or ecrypt with pin.", "No loaded key error");
            return;
        }
        FileChooser fileChoser = new FileChooser();
        fileChoser.setTitle("Load encrypted document or a directory");
        File chosen = fileChoser.showOpenDialog((Stage) loadedLabel.getScene().getWindow());
        if (chosen != null){
            try {
                backend.encryptFile(chosen.toPath());
            }
            catch (NoSuchAlgorithmException | IOException IOE){
                fireAlert("Cannot read from the file.", "Error");
                return;
            }
            catch (Exception e){
                fireAlert("Wrong key!", "Error");
                return;
            }
            fireConfirm(chosen.getName().toString() + " has been encrypted", "Success");
        }
    }

    /** Fire a basic alert with given text*/
    public void fireAlert(String text, String title){
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setAlertType(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.show();
    }

    /** Fire a basic confirmation with given text*/
    public void fireConfirm(String text, String title){
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.show();
        return;
    }
}