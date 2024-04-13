package com.example.mainmenu;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class PruebaMedia {
    @FXML private AnchorPane mainmenu;
    @FXML private TextField textField;
    @FXML private Label nValue;
    @FXML private Label media;
    @FXML private Label nc;
    @FXML private Label alfa;
    @FXML private Label alfa_2;
    @FXML private Label za_2;
    @FXML private Label li;
    @FXML private Label ls;
    @FXML private Label conclusion;
    @FXML private TextField nivelConfianza;

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

    public void cerrar(){
        Platform.exit();
    }

    public void initialize(){
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        };

        nivelConfianza.setTextFormatter(new TextFormatter<>(filter));

    }

    public static List<Double> parseNumbers(String data) {
        List<Double> numbers = new ArrayList<>();
        StringBuilder currentNumber = new StringBuilder();

        // Recorre cada caracter en la cadena de datos
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);

            // Agrega el caracter al número actual
            currentNumber.append(c);

            // Si el siguiente caracter es un punto, el actual es un dígito, y hay más caracteres por delante,
            // eso significa que hemos llegado al final de un número.
            if (i + 1 < data.length() && data.charAt(i + 1) == '.' && Character.isDigit(c)) {
                try {
                    double number = Double.parseDouble(currentNumber.toString());
                    numbers.add(number);
                    // Reinicia el StringBuilder para el siguiente número
                    currentNumber.setLength(0);
                } catch (NumberFormatException e) {
                    // Manejar la excepción según sea necesario
                    System.out.println("Error al parsear: " + currentNumber.toString());
                }
            }
        }

        // Añade el último número si es que queda alguno
        if (currentNumber.length() > 0) {
            try {
                double number = Double.parseDouble(currentNumber.toString());
                numbers.add(number);
            } catch (NumberFormatException e) {
                System.out.println("Error al parsear: " + currentNumber.toString());
            }
        }

        return numbers;
    }
    public static double varS(List<Double> data) {
        if (data == null || data.size() < 2) {
            throw new IllegalArgumentException("La lista debe contener al menos dos elementos.");
        }

        double mean = data.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double variance = data.stream().mapToDouble(x -> Math.pow(x - mean, 2)).sum() / (data.size() - 1);
        return variance;
    }

    // Método para calcular el valor inverso de la distribución normal estándar (INV.NORM.ESTAND)
    public static double invNormEstand(double probability) {
        if (probability <= 0.0 || probability >= 1.0) {
            throw new IllegalArgumentException("La probabilidad debe estar entre 0 y 1, exclusivo.");
        }

        try {
            NormalDistribution normalDistribution = new NormalDistribution();
            return normalDistribution.inverseCumulativeProbability(probability);
        } catch (Exception e) {
            // Captura excepciones específicas o generales que podrían surgir desde la biblioteca de distribución normal
            throw new RuntimeException("Error al calcular la inversa de la distribución normal estándar: " + e.getMessage(), e);
        }
    }

    // Método para calcular el valor inverso de la distribución chi-cuadrado (INV.CHICUAD)
    public static double invChiCuad(double probability, int degreesOfFreedom) {
        if (probability <= 0.0 || probability >= 1.0) {
            throw new IllegalArgumentException("La probabilidad debe estar entre 0 y 1.");
        }
        if (degreesOfFreedom <= 0) {
            throw new IllegalArgumentException("Los grados de libertad deben ser un número entero positivo.");
        }

        ChiSquaredDistribution chiSquaredDistribution = new ChiSquaredDistribution(degreesOfFreedom);
        return chiSquaredDistribution.inverseCumulativeProbability(probability);
    }

    public void button(){
        List<Double> lista = parseNumbers(textField.getText().substring(1));
        double mediaValue = 0;
        for (double number : lista){
            System.out.println(number);
            System.out.println(mediaValue);
            mediaValue += number;
        }

        int nValue = lista.size();
        mediaValue = mediaValue/nValue;
        double nivelConfianza = (double) Integer.parseInt(this.nivelConfianza.getText()) /100;
        double alpha = 1-nivelConfianza;
        double alpha_2 = alpha/2;
        double probabilidad = 1-alpha_2;
        double za_2 = invNormEstand(probabilidad);
        double li = ((double) nValue /100)-(za_2/ Math.sqrt(12*(nValue)));
        double ls = ((double) nValue /100)+(za_2/ Math.sqrt(12*(nValue)));
        this.nValue.setText(String.valueOf(nValue));
        this.media.setText(String.format("%.4f", mediaValue));
        this.alfa.setText(String.format("%.4f", alpha));
        this.alfa_2.setText(String.format("%.4f", alpha_2));
        this.za_2.setText(String.format("%.4f", za_2));
        this.li.setText(String.format("%.4f", li));
        this.ls.setText(String.format("%.4f", ls));
        this.nc.setText(String.valueOf(nivelConfianza*100));

        if (mediaValue>=li && mediaValue<=ls){
            this.conclusion.setText("Se acepta Ho");
        }else
            this.conclusion.setText("No se acepta Ho");
        int gradosLibertad = lista.size()-1;
    }
}