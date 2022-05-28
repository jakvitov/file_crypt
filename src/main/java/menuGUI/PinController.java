package menuGUI;

import backend.ControllBackend;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.util.InputMismatchException;

public class PinController {

    private String enteredPin;

    @FXML
    private PasswordField pswdField1;

    @FXML
    private PasswordField pswdField2;

    @FXML
    private Button confirmButton;

    @FXML
    protected void pswdEntered2(){
        String firstPswd = pswdField1.getText();
        String secondPswd = pswdField2.getText();
        if (firstPswd.equals(secondPswd) == false){
            fireAlert("Given passwords do not match!", "Error");
            return;
        }
        ControllBackend.enteredPin = secondPswd;
        Stage primaryStage = (Stage)pswdField1.getScene().getWindow();
        primaryStage.close();
        return;
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
