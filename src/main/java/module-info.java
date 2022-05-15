module com.example.cryptsw {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
                        
    opens menuGUI to javafx.fxml;
    exports menuGUI;
}