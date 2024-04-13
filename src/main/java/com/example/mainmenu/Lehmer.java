package com.example.mainmenu;

import java.io.IOException;
import java.util.Scanner;
import java.util.function.UnaryOperator;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Lehmer {
    @FXML private AnchorPane mainmenu;
    private Scanner scanner;
    public Lehmer(){}
    private long Xo;
    @FXML private TextField XoText;
    @FXML private TextField AText;
    @FXML private TextField numText;
    @FXML private Button Aceptar;

    @FXML private TableView<Numero> table;
    @FXML private TableColumn<Numero , Integer> iteracionCol;
    @FXML private TableColumn<Numero , Long> YCol;
    @FXML private TableColumn<Numero , Long> Extrac;
    @FXML private TableColumn<Numero , Double> pseudoCol;
    @FXML private TableColumn<Numero , Long> ZCol;
    private long ExtracLong;
    private double m;
    private long A;
    private int num;
    private long z;
    private long z2;
    private long Ylong;

    public Lehmer(long Xo, long A, long ExtracLong, double m, int num, long z, long z2, long Ylong) {
        this.Xo = Xo;
        this.A = A;
        this.ExtracLong = ExtracLong;
        this.m = m;
        this.num = num;
        this.z = z;
        this.z2 = z2;
        this.Ylong = Ylong;

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

    public double next() {
        String mString = Long.toString(Xo);
        int largo = mString.length();

        double m = Math.pow(10, largo);
        z = A*Xo;
        String ZString = Long.toString(z);
        int ZLong = ZString.length();

        String parteExtraida = ZString.substring(0, ZLong-largo);
        ExtracLong = Long.parseLong(parteExtraida);

        int diferencia = ZString.length() - largo;
        String YString = ZString.substring(diferencia, ZLong);
        Ylong = Long.parseLong(YString);

        Xo = (Ylong - ExtracLong);
        z2=A*Xo;
        return (double) Xo / m;
    }

    public void initialize(){
        iteracionCol.setCellValueFactory(new PropertyValueFactory<Numero, Integer>("iteracion"));
        YCol.setCellValueFactory(new PropertyValueFactory<Numero, Long>("YCol"));
        Extrac.setCellValueFactory(new PropertyValueFactory<Numero, Long>("Extrac"));
        pseudoCol.setCellValueFactory(new PropertyValueFactory<Numero, Double>("pseudoNumero"));
        ZCol.setCellValueFactory(new PropertyValueFactory<Numero, Long>("ZCol"));

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        };
        XoText.setTextFormatter(new TextFormatter<>(filter));
        AText.setTextFormatter(new TextFormatter<>(filter));
        numText.setTextFormatter(new TextFormatter<>(filter));
    }


    public void Aceptar(){
        this.table.getItems().clear();

        long Xo = DatoXo();

        long A = DatoA();

        int num = DatoNum();

        String mString = Long.toString(Xo);
        int largo = mString.length();

        double m = Math.pow(10, largo);
        long z = A*Xo;
        String ZString = Long.toString(z);
        int ZLong = ZString.length();

        String parteExtraida = ZString.substring(0, ZLong-largo);
        Long ExtracLong = Long.parseLong(parteExtraida);

        int diferencia = ZString.length() - largo;
        String YString = ZString.substring(diferencia, ZLong);
        Long Ylong = Long.parseLong(YString);

        Lehmer rng = new Lehmer(Xo, A, 0, 0, num, 0, 0,0);

        // Generar y mostrar algunos números pseudoaleatorios
        for (int i = 1; i <= num; i++) {

            Numero numero = new Numero (i, rng.next(), rng.getYlong(), rng.getExtracLong(), rng.getZ2());
            this.table.getItems().add(numero);
        }

    }
    public long DatoXo(){
        if(XoText.getText() != ""){
            long Dato1 = Long.parseLong(XoText.getText());
            return Dato1;
        } else {
            throw new IllegalArgumentException("Faltan datos en Xo.");
        }
    }
    public long DatoA(){
        if(AText.getText() != ""){
            long Dato2 = Long.parseLong(AText.getText());
            return Dato2;
        } else {
            throw new IllegalArgumentException("Faltan datos en A.");
        }
    }
    public int DatoNum(){
        if(numText.getText() != ""){
            int Dato3 = Integer.parseInt(numText.getText());
            return Dato3;
        } else {
            throw new IllegalArgumentException("Faltan datos en Numero de iteraciones.");
        }
    }
    public long getYlong() {
        return Ylong;
    }
    public long getExtracLong() {
        return ExtracLong;
    }
    public long getZ2() {
        return z2;
    }
    public class Numero {
        private int iteracion;
        private double pseudoNumero;
        private long YCol;
        private long Extrac;
        private long ZCol;
        public Numero(int iteracion, double pseudoNumero, long YCol, long Extrac, long ZCol) {
            this.iteracion = iteracion;
            this.pseudoNumero = pseudoNumero;
            this.YCol = YCol;
            this.Extrac = Extrac;
            this.ZCol = ZCol;
        }

        public int getIteracion() {
            return iteracion;
        }

        public void setIteracion(int iteracion) {
            this.iteracion = iteracion;
        }

        public double getPseudoNumero() {
            return pseudoNumero;
        }

        public void setPseudoNumero(double pseudoNumero) {
            this.pseudoNumero = pseudoNumero;
        }
        public long getYCol() {
            return YCol;
        }
        public void setY(long YCol) {
            this.YCol = YCol;
        }

        public long getExtrac() {
            return Extrac;
        }
        public void setExtrac(long Extrac) {
            this.Extrac = Extrac;
        }

        public long getZCol() {
            return ZCol;
        }

        public void setZCol(long Z) {
            this.ZCol = ZCol;
        }
    }
}