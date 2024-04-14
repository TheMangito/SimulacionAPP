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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.apache.commons.math3.distribution.NormalDistribution;
public class Corridas {
    @FXML
    private AnchorPane mainmenu;
    double j;
    double p2;
    public static double x,y,z;
    @FXML
    private TextField datos;
    @FXML private TextField nc;
    @FXML private Label corrida;
    @FXML private Label longi;
    @FXML private Label nco;
    @FXML private Label ndatos;
    @FXML private Label media;
    @FXML private Label sigma;
    @FXML private Label zo;
    @FXML private Label zalfa;
    @FXML private Label ncor;
    @FXML private Label inv;
    @FXML private Label resu;
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
        return numbers;
    }
    public void Alto(){
        List<Double> lista = new ArrayList<>();
        if (!nc.getText().isEmpty() && !datos.getText().isEmpty()){
            lista=parseNumbers(this.datos.getText());
            j= Double.parseDouble(nc.getText());
            p2= (1 - j/100) /2;
            List<Integer> valores = listaS(lista);
            HashMap<Integer, Integer> map = corridas(valores);
            double c = Media(lista);
            double a = Sigma(lista);
            double h = Z(lista);
            double f = invNormEstand(1-p2);

            corrida.setText(String.valueOf(valores));
            longi.setText(String.valueOf(map));
            ndatos.setText(String.valueOf(lista.size()));
            nco.setText(String.valueOf(map.size()));
            media.setText(String.valueOf(c));
            sigma.setText(String.valueOf(a));
            zo.setText(String.valueOf(h));
            ncor.setText(String.valueOf(j));
            zalfa.setText(String.valueOf(p2));
            inv.setText(String.valueOf(f));
            if(p2>f){
                resu.setText("Ho se rechaza y los datos no son de una serie U(0, 1)");
            } else{
                resu.setText("Ho se acepta y los datos son de una serie U(0, 1)");
            }
        }
        else {
            System.out.println("Error");
        }

    }
    public static List<Integer> listaS(List<Double> listaDeDatos){
        List<Integer> listaS = new ArrayList<>();
        for(int i = 1; i<listaDeDatos.size();i++){
            if(listaDeDatos.get(i) > listaDeDatos.get(i-1)){
                listaS.add(1);
            }else
                listaS.add(0);
        }

        return listaS;
    }
    public static HashMap<Integer,Integer> corridas(List<Integer> lista){
        HashMap<Integer,Integer> corridas = new HashMap<>();
        int numCorrida = 1;
        int valorActual;
        int contador = 1;
        for (int i = 0; i<lista.size();){
            valorActual = lista.get(i);
            while (i + contador < lista.size() && lista.get(i + contador) == valorActual) {
                contador++;
            }
            corridas.put(numCorrida, contador);
            i = i+contador;
            contador=1;
            numCorrida++;
        }
        return corridas;
    }
    public double Media(List<Double> lista){
        x= (double) ((2 * lista.size()) - 1) / 3;
        return x;
    }
    public double Sigma(List<Double> lista){
        y = (double) ((16* lista.size())-29)/90;
        return y;
    }
    public double Z(List<Double> lista){
        List<Integer> valores = listaS(lista);
        HashMap<Integer, Integer> map = corridas(valores);
        z= (double) (map.size()-x/(Math.sqrt(y)));
        return z;
    }
    public static double invNormEstand(double probability) {
        if (probability <= 0.0 || probability >= 1.0) {
            throw new IllegalArgumentException("La probabilidad debe estar entre 0 y 1.");
        }
        NormalDistribution normalDistribution = new NormalDistribution();
        return normalDistribution.inverseCumulativeProbability(probability);
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
    public void cerrar(){
        Platform.exit();
    }
}