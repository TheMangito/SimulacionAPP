module com.example.mainmenu {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires commons.math3;
    requires javafx.media;

    opens com.example.mainmenu to javafx.fxml;
    exports com.example.mainmenu;
}