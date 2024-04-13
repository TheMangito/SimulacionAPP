package com.example.mainmenu;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class CuadradosMedios {
    @FXML
    private AnchorPane mainmenu;
    @FXML
    private TextField initialNumberField;
    @FXML
    private TextField numberOfDigitsField;
    @FXML
    private TextField numberOfRandomNumbersField;
    @FXML
    private Label resultLabel;

    private double xOffset = 0;
    private double yOffset = 0;

    public void generateRandomNumbers() {
        try {
            int initialNumber = Integer.parseInt(initialNumberField.getText());
            int numberOfDigits = Integer.parseInt(numberOfDigitsField.getText());
            int numberOfRandomNumbers = Integer.parseInt(numberOfRandomNumbersField.getText());

            List<Integer> randomNumbers = generatePseudoRandomNumbers(initialNumber, numberOfDigits, numberOfRandomNumbers);
            resultLabel.setText("Números generados: " + randomNumbers.toString());
        } catch (NumberFormatException e) {
            resultLabel.setText("Por favor, ingrese números válidos en todos los campos.");
        }
    }

    private List<Integer> generatePseudoRandomNumbers(int initialNumber, int numberOfDigits, int count) {
        List<Integer> randomNumbers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int square = initialNumber * initialNumber;
            String squareStr = String.valueOf(square);
            int numDigits = squareStr.length();
            int padding = numberOfDigits - numDigits;
            if (padding > 0) {
                squareStr = "0".repeat(padding) + squareStr;
            }
            int midIndex = (numDigits - numberOfDigits) / 2;
            int randomNumber = Integer.parseInt(squareStr.substring(midIndex, midIndex + numberOfDigits));
            randomNumbers.add(randomNumber);
            initialNumber = randomNumber; // Actualizar el número inicial para la siguiente iteración
        }
        return randomNumbers;
    }

    public void volver(ActionEvent event) throws IOException {
        mainmenu.setDisable(true);
        Parent root = FXMLLoader.load(getClass().getResource("main-menu.fxml"));
        Scene scene = ((Node) event.getSource()).getScene();
        root.translateYProperty().set(scene.getHeight());

        AnchorPane mainroot = (AnchorPane) scene.getRoot();
        mainroot.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            // Asegúrate de que mainmenu esté definido y sea accesible aquí
            mainroot.getChildren().remove(mainmenu);
            mainmenu.setDisable(false);
        });
        timeline.play();
    }
    public void dragged(Parent root, Stage stage){
        root.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    public void cerrar(){
        Platform.exit();
    }
}
