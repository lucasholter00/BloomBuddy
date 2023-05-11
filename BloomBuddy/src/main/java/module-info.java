module com.group18.BloomBuddy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.eclipse.paho.client.mqttv3;
    
    exports com.group18.BloomBuddy to javafx.graphics;
    opens com.group18.BloomBuddy.application to javafx.fxml;
    exports com.group18.BloomBuddy.application to javafx.graphics;
}
