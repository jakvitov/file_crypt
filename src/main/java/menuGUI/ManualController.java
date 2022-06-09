package menuGUI;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ManualController {

    @FXML
    private Label manualText;

    @FXML
    private ListView manualMenu;

    /** We fill the menu list in the initialize method*/
    public void initialize(){
        manualMenu.getItems().add("Encryption and Decryption with key.");
        manualMenu.getItems().add("Encryption and decryption with pin.");
        manualMenu.getItems().add("Keys and pins");
        String keyManual = "Error while loading the file";
        String encryptManual = "Error while loading the file";
        String decryptManual = "Error while loading the file";
        manualText.setText(encryptManual);
        try {
            keyManual = new String(Files.readAllBytes(Path.of("/home/jakub/Java/CryptSw/src/main/manual/keyManual.txt")));
            encryptManual = new String(Files.readAllBytes(Path.of("/home/jakub/Java/CryptSw/src/main/manual/encryptManual.txt")));
            decryptManual = new String(Files.readAllBytes(Path.of("/home/jakub/Java/CryptSw/src/main/manual/decryptManual.txt")));
        }
        catch (IOException IOE){
            fireAlert("Error, some manual files cannot be loaded.", "Error");
        }
        String finalEncryptManual = encryptManual;
        String finalDecryptManual = decryptManual;
        String finalKeyManual = keyManual;
        manualMenu.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                String choice = (String) manualMenu.getSelectionModel().getSelectedItem();
                System.out.println(choice);
                if (choice.equals("Encryption and Decryption with key.")) {
                    manualText.setText(finalEncryptManual);
                } else if (choice.equals("Encryption and decryption with pin.")) {
                    manualText.setText(finalDecryptManual);
                }
                //Keys and pins then
                else {
                    manualText.setText(finalKeyManual);
                }
            }
        });
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
