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
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import menu_cli.ClientInterface;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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

    @FXML
    private MenuItem dirEncryptKey;

    @FXML
    private MenuItem encrFileWithPin;

    @FXML
    private MenuItem dirEncryptWithPin;

    @FXML
    private Menu decryption;

    @FXML
    private MenuItem decryptKey;

    @FXML
    private MenuItem dirDecryptKey;

    @FXML
    private MenuItem decrFileWithPin;

    @FXML
    private MenuItem dirDecryptWithPin;


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
            fireConfirm("Key " + chosen.getName() + ".key " + " sucessfully saved", "Success");
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
            loadedLabel.setText("Loaded: " + chosen.getName().toString());
        }
    }

    @FXML
    protected void showHelp() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("manual.fxml"));
        PinController controller = new PinController();
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        Stage primaryStage = (Stage)loadedLabel.getScene().getWindow();
        stage.initOwner(primaryStage);
        stage.setX(primaryStage.getX() + 200);
        stage.setY(primaryStage.getY() + 100);
        stage.showAndWait();
    }

    @FXML
    protected void keyEncryptAction(){
        if (backend.isKeyLoaded() == false){
            fireAlert("No key is loaded. Load a new key or ecrypt with pin.", "No loaded key error");
            return;
        }
        FileChooser fileChoser = new FileChooser();
        fileChoser.setTitle("Load document to encrypt");
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

    @FXML
    protected void decryptKeyAction(){
        if (backend.isKeyLoaded() == false){
            fireAlert("No key is loaded. Load a new key or ecrypt with pin.", "No loaded key error");
            return;
        }
        FileChooser fileChoser = new FileChooser();
        fileChoser.setTitle("Load encrypted document");
        File chosen = fileChoser.showOpenDialog((Stage) loadedLabel.getScene().getWindow());
        if (chosen != null){
            try {
                backend.decryptFile(chosen.toPath());
            }
            catch (NoSuchAlgorithmException | IOException IOE){
                fireAlert("Cannot read from the file.", "Error");
                return;
            }
            catch (Exception e){
                fireAlert("Wrong key!", "Error");
                return;
            }
            fireConfirm(chosen.getName().toString() + " has been decrypted", "Success");
        }
    }

    @FXML
    protected void dirEncryptAction(){
        if (backend.isKeyLoaded() == false){
            fireAlert("No key is loaded. Load a new key or ecrypt with pin.", "No loaded key error");
            return;
        }
        DirectoryChooser fileChoser = new DirectoryChooser();
        fileChoser.setTitle("Load directory to encrypt");
        File chosen = fileChoser.showDialog((Stage) loadedLabel.getScene().getWindow());
        if (chosen != null){
            try {
                backend.encryptDirectory(chosen.toPath());
            }
            catch (NoSuchAlgorithmException | IOException IOE){
                fireAlert("Cannot read from the directory.", "Error");
                return;
            }
            catch (Exception e){
                fireAlert("Wrong key!", "Error");
                return;
            }
            fireConfirm(chosen.getName().toString() + " has been encrypted", "Success");
        }
    }

    @FXML
    protected void dirDecryptKeyAction(){
        if (backend.isKeyLoaded() == false){
            fireAlert("No key is loaded. Load a new key or ecrypt with pin.", "No loaded key error");
            return;
        }
        DirectoryChooser fileChoser = new DirectoryChooser();
        fileChoser.setTitle("Load encrypted directory");
        File chosen = fileChoser.showDialog((Stage) loadedLabel.getScene().getWindow());
        if (chosen != null){
            try {
                backend.decrpytDirectory(chosen.toPath());
            }
            catch (NoSuchAlgorithmException | IOException IOE){
                fireAlert("Cannot read from the directory.", "Error");
                return;
            }
            catch (Exception e){
                fireAlert("Wrong key!", "Error");
                return;
            }
            fireConfirm(chosen.getName().toString() + " has been decrpyted", "Success");
        }
    }

    @FXML
    protected void encrFileWithPinAction() throws IOException, InvalidAlgorithmParameterException,
            NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException,
            InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        FileChooser fileChoser = new FileChooser();
        fileChoser.setTitle("Load document to encrypt");
        File chosen = fileChoser.showOpenDialog((Stage) loadedLabel.getScene().getWindow());
        if (chosen == null){
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pinView.fxml"));
        PinController controller = new PinController();
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initModality(Modality.WINDOW_MODAL);
        Stage primaryStage = (Stage)loadedLabel.getScene().getWindow();
        stage.initOwner(primaryStage);
        stage.setX(primaryStage.getX() + 200);
        stage.setY(primaryStage.getY() + 100);
        stage.showAndWait();

        if (ControllBackend.enteredPin != null){
            try {
                this.backend.encryptFile(chosen.toPath(), this.backend.enteredPin);
            }
            catch (IOException IOE){
                fireAlert("Cannot read the given file!", "Error");
            }
            catch (Exception e){
                fireAlert("Error while encrypting the file", "Error");
            }
        }
        else {
            return;
        }
        fireConfirm("File successfully encrypted.", "Success!");
    }

    @FXML
    protected void decrFileWithPinAction() throws IOException {
        FileChooser fileChoser = new FileChooser();
        fileChoser.setTitle("Load document to decrypt");
        File chosen = fileChoser.showOpenDialog((Stage) loadedLabel.getScene().getWindow());
        if (chosen == null){
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pinView.fxml"));
        PinController controller = new PinController();
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initModality(Modality.WINDOW_MODAL);
        Stage primaryStage = (Stage)loadedLabel.getScene().getWindow();
        stage.initOwner(primaryStage);
        stage.setX(primaryStage.getX() + 200);
        stage.setY(primaryStage.getY() + 100);
        stage.showAndWait();

        if (ControllBackend.enteredPin != null){
            try {
                this.backend.decryptFile(chosen.toPath(), this.backend.enteredPin);
            }
            catch (NoSuchAlgorithmException | IOException IOE){
                fireAlert("Cannot read from the directory.", "Error");
                return;
            }
            catch (Exception e){
                fireAlert("Wrong key!", "Error");
                return;
            }
        }
        else {
            return;
        }
        fireConfirm("File successfully decrypted.", "Success!");
    }

    @FXML
    protected void dirEncryptWithPinAction() throws IOException {
        DirectoryChooser direChoser = new DirectoryChooser();
        direChoser.setTitle("Load directory to encrypt");
        File chosen = direChoser.showDialog((Stage) loadedLabel.getScene().getWindow());
        if (chosen == null){
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pinView.fxml"));
        PinController controller = new PinController();
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initModality(Modality.WINDOW_MODAL);
        Stage primaryStage = (Stage)loadedLabel.getScene().getWindow();
        stage.initOwner(primaryStage);
        stage.setX(primaryStage.getX() + 200);
        stage.setY(primaryStage.getY() + 100);
        stage.showAndWait();

        if (ControllBackend.enteredPin != null){
            try {
                this.backend.encryptDirectory(chosen.toPath(), this.backend.enteredPin);
            }
            catch (IOException IOE){
                fireAlert("Cannot read the given directory!", "Error");
            }
            catch (Exception e){
                fireAlert("Error while encrypting the directory", "Error");
            }
        }
        else {
            return;
        }
        fireConfirm("Directory encrypted.", "Success!");
    }

    @FXML
    protected void dirDecryptWithPinAction() throws IOException {
        DirectoryChooser direChoser = new DirectoryChooser();
        direChoser.setTitle("Load directory to decrypt");
        File chosen = direChoser.showDialog((Stage) loadedLabel.getScene().getWindow());
        if (chosen == null){
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pinView.fxml"));
        PinController controller = new PinController();
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.initModality(Modality.WINDOW_MODAL);
        Stage primaryStage = (Stage)loadedLabel.getScene().getWindow();
        stage.initOwner(primaryStage);
        stage.setX(primaryStage.getX() + 200);
        stage.setY(primaryStage.getY() + 100);
        stage.setResizable(false);
        stage.showAndWait();

        if (ControllBackend.enteredPin != null){
            try {
                this.backend.decrpytDirectory(chosen.toPath(), this.backend.enteredPin);
            }
            catch (NoSuchAlgorithmException | IOException IOE){
                fireAlert("Cannot read from the directory.", "Error");
                return;
            }
            catch (Exception e){
                fireAlert("Wrong key!", "Error");
                return;
            }
        }
        else {
            return;
        }
        fireConfirm("Directory successfully decrypted.", "Success!");
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