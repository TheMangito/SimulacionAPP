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
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;


public class ChiCuadrado {
    @FXML private AnchorPane mainmenu;
    @FXML private TextField Datos;
    @FXML private TextField NcText;
    @FXML private TextField KField;
    @FXML private TextField AmpField;
    @FXML private TextField ValorX2;
    @FXML private TextField x2Prueba;
    @FXML private TextField pruebaHip;
    @FXML private TableView<Clase> table;
    @FXML private TableColumn<Clase , Integer> clases1;
    @FXML private TableColumn<Clase , String> clases2;
    @FXML private TableColumn<Clase , Integer> frecAbs;
    @FXML private TableColumn<Clase , Integer> frecAcum;
    @FXML private TableView<KDatos> kTable;
    @FXML private TableColumn<KDatos , Double> valorKColumn;
    @FXML private TableColumn<KDatos , String> kColumn;

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
        valorKColumn.setCellValueFactory(new PropertyValueFactory<KDatos, Double>("ValorKColumn"));
        kColumn.setCellValueFactory(new PropertyValueFactory<KDatos, String>("kColumn"));
        clases1.setCellValueFactory(new PropertyValueFactory<Clase, Integer>("clases1"));
        clases2.setCellValueFactory(new PropertyValueFactory<Clase, String>("clases2"));
        frecAbs.setCellValueFactory(new PropertyValueFactory<Clase, Integer>("frecAbs"));
        frecAcum.setCellValueFactory(new PropertyValueFactory<Clase, Integer>("frecAcum"));
    }

    public void start(javafx.stage.Stage primaryStage) throws Exception {
        // Inicialización de la escena, etc.
    }

    public void button(){
        this.kTable.getItems().clear();
        this.table.getItems().clear();

        Alert alert = new Alert(Alert.AlertType.NONE);
        try {
        List<Double> lista = parseNumbers(Datos.getText().substring(1));

        double k = Double.parseDouble(KField.getText());
        double Ampli = Double.parseDouble(AmpField.getText());

        if(k == 0){
            k = 1/Ampli;
        } else if(Ampli == 0 || Ampli>1){
            Ampli = 1/k;
        }
        double Nc = Double.parseDouble(NcText.getText());

        Metodo1(lista,k, Ampli, Nc);
        } catch (Exception e) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Datos erroneos, por favor compruebe los datos ingresados");
            alert.show();
        }

    }

    public void Metodo1(List<Double> lista, double k, double Ampli, double Nc){
        double min = lista.get(0);
        double max = lista.get(0);
        double rango;
        double Xmedia = 0;
        double desvEstand;
        double varianza;
        double Ai = lista.size()*Ampli;

        double chiCuad;

        for (int i = 1; i < lista.size(); i++) {
            if (lista.get(i) < min) {
                min = lista.get(i);
            }
        }

        for (int i = 1; i < lista.size(); i++) {
            if (lista.get(i) > max) {
                max = lista.get(i);
            }
        }

        rango = max-min;

        for(int i=0; i < lista.get(i); i++){
            Xmedia += lista.get(i);
        }
        Xmedia = Xmedia/lista.size();

        double sumaCuadrados = 0;
        for(int i=0; i < lista.size(); i++){
            sumaCuadrados = sumaCuadrados + Math.pow(lista.get(i)-Xmedia, 2);
        }

        varianza = VarianzaMetod(lista, Xmedia, sumaCuadrados);
        desvEstand = Math.sqrt(varianza);
        chiCuad = FrecuenciasK(k, Ampli, lista, Ai);
        String chiDecimal = String.format("%.4f", chiCuad);
        chiCuad = Double.parseDouble(chiDecimal);

        double alfa = 1-(1 - Nc / 100);
        ChiSquaredDistribution chiSquaredDistribution = new ChiSquaredDistribution(k-1);
        double inverseChiSquare = chiSquaredDistribution.inverseCumulativeProbability(alfa);
        String StringInverseChiSquare = String.format("%.4f", inverseChiSquare);
        double inverseChiSquareMostrar = Double.parseDouble(StringInverseChiSquare);

        String condicion = pruebaDeHip(inverseChiSquare, chiCuad);

        ValorX2.setText(String.valueOf(inverseChiSquareMostrar));
        pruebaHip.setText(condicion);

        int frecuenciaAcumulada = 0;
        double contadorAmpli = Ampli;
        chiCuad = 0;
        double chiCuadTotal = 0;
        String KdatosString = "k"+1;

        String StringContadorAmpliMin = String.format("%.2f", contadorAmpli-Ampli);
        double AmpliMin = Double.parseDouble(StringContadorAmpliMin);
        String StringContadorAmpliMax = String.format("%.2f", contadorAmpli);
        double AmpliMax = Double.parseDouble(StringContadorAmpliMax);


        for(int i=0; i<k; i++){
            int frecuencia = 0;
            for(int u = 0; u< lista.size(); u++){
                if(AmpliMin == 0){
                    if(lista.get(u)<AmpliMax){
                        frecuencia++;
                    }
                } else if(AmpliMax == 1){
                    if(lista.get(u)>=AmpliMin){
                        frecuencia ++;
                    }
                } else {
                    if(lista.get(u) >= (AmpliMin) && lista.get(u) < AmpliMax){
                        frecuencia ++;
                    }
                }

            }

            String intervalos = "("+AmpliMin+", "+AmpliMax+")";
            chiCuad = Math.pow((frecuencia-Ai),2)/Ai;
            chiCuadTotal += chiCuad;
            contadorAmpli += Ampli;

            StringContadorAmpliMin = String.format("%.2f", contadorAmpli-Ampli);
            AmpliMin = Double.parseDouble(StringContadorAmpliMin);
            StringContadorAmpliMax = String.format("%.2f", contadorAmpli);
            AmpliMax = Double.parseDouble(StringContadorAmpliMax);

            String StringChiCuad = String.format("%.4f", chiCuad);
            double chiCuadMostrar = Double.parseDouble(StringChiCuad);

            frecuenciaAcumulada += frecuencia;
            KdatosString = "k"+(i+1);

            KDatos numero = new KDatos (chiCuadMostrar, KdatosString);
            this.kTable.getItems().add(numero);
            Clase numero2 = new Clase((i+1),intervalos, frecuencia, frecuenciaAcumulada);
            this.table.getItems().add(numero2);
        }
        String StringChiCuadTotal = String.format("%.4f", chiCuadTotal);
        double chiCuadTotalMostrar = Double.parseDouble(StringChiCuadTotal);
        x2Prueba.setText(String.valueOf(chiCuadTotalMostrar));

    }

    public double DatoAmpli(){
        if(AmpField.getText() != ""){
            double Dato1 = Double.parseDouble(AmpField.getText());
            return Dato1;
        } else {
            throw new IllegalArgumentException("Faltan datos en Amplitud.");
        }
    }

    public double DatoK(){
        if(KField.getText() != ""){
            double Dato2 = Double.parseDouble(KField.getText());
            return Dato2;
        } else {
            throw new IllegalArgumentException("Faltan datos en K.");
        }
    }

    public double DatoNc(){
        if(NcText.getText() != ""){
            double Dato3 = Double.parseDouble(NcText.getText());
            return Dato3;
        } else {
            throw new IllegalArgumentException("Faltan datos en Nivel de confianza.");
        }
    }

    public static List<Double> parseNumbers(String data) {
        List<Double> numbers = new ArrayList<>();
        StringBuilder currentNumber = new StringBuilder();

        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);

            currentNumber.append(c);

            if (i + 1 < data.length() && data.charAt(i + 1) == '.' && Character.isDigit(c)) {
                try {
                    double number = Double.parseDouble(currentNumber.toString());
                    numbers.add(number);
                    currentNumber.setLength(0);
                } catch (NumberFormatException e) {
                    System.out.println("Error al parsear: " + currentNumber.toString());
                }
            }
        }

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

    public static String pruebaDeHip(double inverseChiSquare, double chiCuad){
        if(chiCuad>inverseChiSquare){
            return("Ho se rechaza y los datos no son de una serie U(0, 1)");
        } else{
            return ("Ho se acepta y los datos son de una serie U(0, 1)");
        }
    }

    public static double VarianzaMetod(List<Double> lista, double Xmedia, double sumaCuadrados){
        double standarDerivation = Math.sqrt(sumaCuadrados/lista.size());
        return standarDerivation;
    }

    public static double FrecuenciasK(double k, double Ampli, List<Double> lista, double Ai){
        int frecuenciaAcumulada = 0;
        double contadorAmpli = Ampli;
        double chiCuad = 0;
        double chiCuadTotal = 0;

        for(int i=0; i<k; i++){
            int frecuencia = 0;
            for(int u = 0; u<lista.size(); u++){
                if(lista.get(u)<contadorAmpli && (contadorAmpli-Ampli)<= lista.get(u)){
                    frecuencia ++;
                }
            }

            chiCuad = Math.pow((frecuencia-Ai),2)/Ai;
            chiCuadTotal += chiCuad;
            contadorAmpli += Ampli;
            frecuenciaAcumulada += frecuencia;
        }
        return chiCuadTotal;
    }

    public class Clase {

        private int clases1;
        private String clases2;
        private int frecAbs;
        private int frecAcum;

        public Clase(int clases1, String clases2, int frecAbs, int frecAcum){
            this.clases1 = clases1;
            this.clases2 = clases2;
            this.frecAbs = frecAbs;
            this.frecAcum = frecAcum;
        }

        public int getClases1() {
            return clases1;
        }

        public void setClases1(int clases1) {
            this.clases1 = clases1;
        }

        public String getClases2() {
            return clases2;
        }

        public void setClases2(String clases2) {
            this.clases2 = clases2;
        }

        public int getFrecAbs() {
            return frecAbs;
        }

        public void setFrecAbs(int frecAbs) {
            this.frecAbs = frecAbs;
        }

        public int getFrecAcum() {
            return frecAcum;
        }

        public void setFrecAcum(int frecAcum) {
            this.frecAcum = frecAcum;
        }
    }
    public class KDatos {

        private double valorKColumn;
        private String kColumn;


        public KDatos(double valorKColumn, String kColumn){
            this.valorKColumn = valorKColumn;
            this.kColumn = kColumn;
        }

        public double getValorKColumn() {

            return valorKColumn;
        }

        public void setValorKColumn(double valorKColumn) {
            this.valorKColumn = valorKColumn;
        }

        public String getKColumn() {

            return kColumn;
        }

        public void setKColumn(String kColumn) {
            this.kColumn = kColumn;
        }

    }
}