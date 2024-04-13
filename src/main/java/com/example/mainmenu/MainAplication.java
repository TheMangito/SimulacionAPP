package com.example.mainmenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainAplication extends Application {
    private double xOffset = 0;
    private double yOffset = 0;
    @Override
    public void start(Stage stage) throws IOException {
        System.setProperty("prism.lcdtext", "false");
        Font font1 = Font.loadFont(getClass().getResourceAsStream("/fonts/SF-Pro-Text-Regular.otf"), 15);
        Font font2 = Font.loadFont(getClass().getResourceAsStream("/fonts/SF-Pro-Text-Bold.otf"), 15);
        Font font3 = Font.loadFont(getClass().getResourceAsStream("/fonts/SF-Pro-Text-Medium.otf"), 15);
        Font font4 = Font.loadFont(getClass().getResourceAsStream("/fonts/SF-Pro-Text-Light.otf"), 15);

        FXMLLoader fxmlLoader = new FXMLLoader(MainAplication.class.getResource("main-menu.fxml"));
        Pane root = fxmlLoader.load();
        Scene scene = new Scene(root, 943, 702);


        root.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("SimulacionApp");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/metodosFXML/icon.png")));
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}