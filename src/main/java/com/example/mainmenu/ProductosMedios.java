package com.example.mainmenu;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;
import java.util.function.UnaryOperator;

public class ProductosMedios {
    @FXML
    private AnchorPane mainmenu;
    private Parent root;
    private MainController mainControllerClass;

    @FXML private TextField x0;
    @FXML private Button comenzar;
    @FXML private TextField y0;
    @FXML private TextField iteraciones;
    @FXML private TableView<PseudoNumber> table;
    @FXML private TableColumn<PseudoNumber , Integer> iteracionesCol;
    @FXML private TableColumn<PseudoNumber , Integer> xCol;
    @FXML private TableColumn<PseudoNumber , Integer> zCol;
    @FXML private TableColumn<PseudoNumber , Double> decimalesCol;
    @FXML private Label zValue;
    @FXML private Button volver;
    private int x0Value;
    private int y0Value;
    private int iteracionesValue;
    private int largo;

    private double xOffset = 0;
    private double yOffset = 0;

    public void initialize(){
        iteracionesCol.setCellValueFactory(new PropertyValueFactory<PseudoNumber, Integer>("Iteracion"));
        xCol.setCellValueFactory(new PropertyValueFactory<PseudoNumber, Integer>("Valor_X"));
        zCol.setCellValueFactory(new PropertyValueFactory<PseudoNumber, Integer>("Valor_Z"));
        decimalesCol.setCellValueFactory(new PropertyValueFactory<PseudoNumber, Double>("Decimal"));

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        };
        iteraciones.setTextFormatter(new TextFormatter<>(filter));
        y0.setTextFormatter(new TextFormatter<>(filter));
        x0.setTextFormatter(new TextFormatter<>(filter));
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
    public static String extraerCaracteres(String numero, int cantidadCaracteres) {
        int inicio = (numero.length() - cantidadCaracteres) / 2;
        return numero.substring(inicio, inicio + cantidadCaracteres);
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
    public void comenzarMetodo() {
        if (!Objects.equals(iteraciones.getText(), "") && !Objects.equals(x0.getText(), "") && !Objects.equals(y0.getText(), "")) {
            ObservableList<PseudoNumber> items = table.getItems();
            items.clear();
            largo = x0.getLength();
            iteracionesValue = Integer.parseInt(iteraciones.getText());
            x0Value = Integer.parseInt(x0.getText());
            y0Value = Integer.parseInt(y0.getText());
            long xant = x0Value;
            long zant = (long) x0Value * y0Value;
            zValue.setText(String.valueOf(zant));
            int divisor = (int) Math.pow(10, largo);

            for (int i = 1; i <= iteracionesValue; i++) {
                long x = Long.parseLong(extraerCaracteres(String.valueOf(zant), largo));
                long z = x * xant;
                float decimal = (float) x / divisor;
                PseudoNumber number = new PseudoNumber(i, x, z, decimal);
                this.table.getItems().add(number);
                xant = x;
                zant = z;
            }

        }
    }

    public class PseudoNumber {
        private int Iteracion;
        private long Valor_X;
        private long Valor_Z;
        private float decimal;

        public PseudoNumber(int Iteracion, long Valor_X, long Valor_Z, float decimal) {
            this.Iteracion = Iteracion;
            this.Valor_X = Valor_X;
            this.Valor_Z = Valor_Z;
            this.decimal = decimal;
        }
        public int getIteracion() {
            return Iteracion;
        }

        public void setIteracion(int Iteracion) {
            this.Iteracion = Iteracion;
        }

        public long getValor_X() {
            return Valor_X;
        }

        public void setValor_X(int Valor_X) {
            this.Valor_X = Valor_X;
        }

        public long getValor_Z() {
            return Valor_Z;
        }

        public void setValor_Z(int Valor_Z) {
            this.Valor_Z = Valor_Z;
        }

        public float getDecimal() {
            return decimal;
        }

        public void setDecimal(float decimal) {
            this.decimal = decimal;
        }
    }

}
