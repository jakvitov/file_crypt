module com.example.cryptsw {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
                        
    opens com.example.cryptsw to javafx.fxml;
    exports com.example.cryptsw;
}