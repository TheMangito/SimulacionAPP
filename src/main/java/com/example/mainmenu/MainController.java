package com.example.mainmenu;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class MainController {

    @FXML
    private AnchorPane mainroot;
    @FXML
    private AnchorPane mainmenu;
    private Parent root;
    private ProductosMedios productosMediosClass;
    private Stage stage;
    private Scene scene;
    @FXML
    private Button start;
    @FXML
    private AnchorPane menu;
    @FXML
    private Button cerrar;


    @FXML private Button productosMedios;
    @FXML private Button lehmer;
    @FXML private Button ks;
    @FXML private Button chiCuadrada;
    @FXML private Button pruebaCorridas;
    @FXML private Button pruebaMedia;
    @FXML private Button pruebaVarianza;
    @FXML private Button montecarlo;
    @FXML private Button multiplicacionConstante;
    @FXML private Button cuadradosMedios;

    private double xOffset = 0;
    private double yOffset = 0;

    private boolean mostrarMenu = false;


    public void initialize(){
        if (mostrarMenu){
            menu.setVisible(true);
        }
    }

    public void start(ActionEvent event) throws IOException {
        menu.setVisible(!menu.isVisible());
        if(menu.isVisible())
            start.setText("Ocultar");
        else
            start.setText("Mostrar");
        /*
        FXMLLoader loader = new FXMLLoader(getClass().getResource("options-menu.fxml"));
        root = loader.load();
        OptionsMenu optionsMenu = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        optionsMenu.dragged(root, stage);
         */

    }
    public void cerrar(){
        Platform.exit();
    }
    public void descargar(){}
    public void informacion(){}

    public void setRoot(AnchorPane anchorPane){
        mainroot = anchorPane;
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

    public void productosMedios(ActionEvent event) throws IOException {
        cargarMetodo(event, "/metodosFXML/productosMedios.fxml", ProductosMedios.class);
    }

    public void cuadradosMedios(ActionEvent event) throws IOException {
        cargarMetodo(event, "/metodosFXML/CuadradosMedios.fxml", CuadradosMedios.class);
    }
    public void corridas(ActionEvent event) throws IOException {
        cargarMetodo(event, "/metodosFXML/corridas.fxml", Corridas.class);
    }

    public void chiCuadrada(ActionEvent event) throws IOException {
        cargarMetodo(event, "/metodosFXML/chiCuadrado.fxml", ChiCuadrado.class);
    }

    public void lehmer(ActionEvent event) throws IOException {
        cargarMetodo(event, "/metodosFXML/Lehmer.fxml", Lehmer.class);
    }
    public void MultiplicacionContante(ActionEvent event) throws IOException {
        cargarMetodo(event, "/metodosFXML/MultiplicacionConstante.fxml", MultiplicacionConstante.class);
    }

    public void pruebaKS(ActionEvent event) throws IOException {
        cargarMetodo(event, "/metodosFXML/PruebaKS.fxml", PruebaKS.class);
    }
    public void pruebaVarianza(ActionEvent event) throws IOException {
        cargarMetodo(event, "/metodosFXML/PruebaVarianza.fxml", PruebaVarianza.class);
    }

    public void cargarMetodo(ActionEvent event, String fxmlPath, Class<?> controllerClass) throws IOException {
        mainmenu.setDisable(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Object controller = loader.getController();

        Scene scene = ((Node) event.getSource()).getScene();
        root.translateYProperty().set(scene.getHeight());

        Stage stage = (Stage) scene.getWindow();
        mainroot = (AnchorPane) scene.getRoot();

        mainroot.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            mainroot.getChildren().remove(mainmenu);
            mainmenu.setDisable(false);
        });
        timeline.play();

        // Asegúrate de que todos los controladores tengan el método 'dragged'
        if (controller instanceof ProductosMedios) {
            ((ProductosMedios) controller).dragged(root, stage);
        }
    }

}