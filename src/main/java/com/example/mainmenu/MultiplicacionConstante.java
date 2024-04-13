package com.example.mainmenu;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MultiplicacionConstante {

    public class MulltiplicacionCo {
        private String iteracion;
        private long X0;
        private long AX0;
        private long Ext;
        private double Psu;

        public MulltiplicacionCo(String iteracion, long x0, long AX0, long ext, double psu) {
            this.iteracion = iteracion;
            this.X0 = x0;
            this.AX0 = AX0;
            this.Ext = ext;
            this.Psu = psu;
        }

        public String getIteracion() {
            return iteracion;
        }

        public void setIteracion(String iteracion) {
            this.iteracion = iteracion;
        }

        public long getX0() {
            return X0;
        }

        public void setX0(int x0) {
            this.X0 = x0;
        }

        public long getAX0() {
            return AX0;
        }

        public void setAX0(int AX0) {
            this.AX0 = AX0;
        }

        public long getExt() {
            return Ext;
        }

        public void setExt(int ext) {
            this.Ext = ext;
        }

        public double getPsu() {
            return Psu;
        }

        public void setPsu(double psu) {
            this.Psu = psu;
        }
    }
    @FXML private AnchorPane mainmenu;
    @FXML private Label LabelA;
    @FXML private TableView<MulltiplicacionCo> Tabla;
    @FXML private TableColumn<MulltiplicacionCo, String> ColX;
    @FXML private TableColumn<MulltiplicacionCo, Long> ColNA;
    @FXML private TableColumn<MulltiplicacionCo, Long> ColAX0;
    @FXML private TableColumn<MulltiplicacionCo, Long> ColExt;
    @FXML private TableColumn<MulltiplicacionCo, Double> ColPsu;
    private ObservableList<MulltiplicacionCo> mult;
    @FXML private Button boton1;
    @FXML private TextField TextFieldA;
    @FXML private TextField TextFieldX0;
    @FXML private TextField TextFieldIT;

    public void multplicacion(long A, long X0, int It) {
        LabelA.setText(String.valueOf(A));
        long Xi = X0;

        for (int i = 0; i < It+1; i++) {
            long AX0 = A*Xi;
            long Extraido = Extraccion(A,AX0);
            double Pseudo = (double) Extraido / Math.pow(10, String.valueOf(A).length());

            MulltiplicacionCo numero = new MulltiplicacionCo("X"+i, Xi, AX0, Extraido, Pseudo);
            mult.add(numero);
            this.Tabla.setItems(mult);

            Xi = Extraido;
        }
    }

    public static long Extraccion(long A, long AX0){
        String A1 = String.valueOf(A);
        String AX = String.valueOf(AX0);

        if (AX.length() < A1.length() + 2){
            return Long.parseLong(AX.substring(AX.length()-(A1.length()+1), AX.length()-2));
        } else {
            return Long.parseLong(AX.substring(AX.length()-(A1.length()+2), AX.length()-2));
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        mult = FXCollections.observableArrayList();
        ColX.setCellValueFactory(new PropertyValueFactory<MulltiplicacionCo, String>("iteracion"));
        ColNA.setCellValueFactory(new PropertyValueFactory<MulltiplicacionCo, Long>("X0"));
        ColAX0.setCellValueFactory(new PropertyValueFactory<MulltiplicacionCo, Long>("AX0"));
        ColExt.setCellValueFactory(new PropertyValueFactory<MulltiplicacionCo, Long>("Ext"));
        ColPsu.setCellValueFactory(new PropertyValueFactory<MulltiplicacionCo, Double>("Psu"));
        Tabla.setItems(mult);
    }

    public void boton1(ActionEvent evento) {
        Alert alert = new Alert(Alert.AlertType.NONE);

        try {
            long A = Long.parseLong(TextFieldA.getText());
            long X0 = Long.parseLong(TextFieldX0.getText());
            int It = Integer.parseInt(TextFieldIT.getText());

            multplicacion(A, X0, It);

        } catch (NumberFormatException e) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("El valor tiene que ser un numero");
            alert.show();
        }
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
