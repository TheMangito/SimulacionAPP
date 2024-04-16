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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

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
    private File ksFile = new File("src/main/resources/metodosFXML/PruebaKS.xlsx");
    private File productosMediosFile = new File("src/main/resources/metodosFXML/productosMediosFile.xlsx");
    private File multiplicacionConstanteFile = new File("src/main/resources/metodosFXML/multiplicacionConstanteFile.xlsx");
    private File chiCuadradaFile = new File("src/main/resources/metodosFXML/chiCuadradaFile.xlsx");
    private File corridasFile = new File("src/main/resources/metodosFXML/corridasFile.xlsx");
    private File lehmerFile = new File("src/main/resources/metodosFXML/lehmerFile.xlsx");
    private File cuadradosMediosFIle = new File("src/main/resources/metodosFXML/cuadradosMediosFIle.xlsx");
    private File pruebaMediaFile = new File("src/main/resources/metodosFXML/pruebaMediaFile.xlsx");
    private File pruebaVarianzaFile = new File("src/main/resources/metodosFXML/pruebaVarianzaFile.xlsx");

    public void initialize(){
    }

    private double xOffset = 0;
    private double yOffset = 0;

    public void setMostrarMenu(){
        menu.setVisible(true);
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

    public void descargar (ActionEvent event) throws  IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Excel");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Hoja de Calculo", "*.xlsx"));

        File file = fileChooser.showSaveDialog(mainmenu.getScene().getWindow());
        Node source = (Node) event.getSource();

        String id = source.getId();
        if (file != null) {
            switch (id){
                case "productosMediosD" ->{
                    Files.copy(productosMediosFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                case "multiplicacionContanetD" ->{
                    Files.copy(multiplicacionConstanteFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                case "KsD" ->{
                    Files.copy(ksFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                case "chiCuadradaD" ->{
                    Files.copy(chiCuadradaFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                case "corridasD" ->{
                    Files.copy(corridasFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                case "lehmerD" ->{
                    Files.copy(lehmerFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                case "cuadradosMediosD" ->{
                    Files.copy(cuadradosMediosFIle.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                case "pruebaMediaD" ->{
                    Files.copy(pruebaMediaFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                case "pruebaVarianzaD" ->{
                    Files.copy(pruebaVarianzaFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }

    public void informacion(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        String id = source.getId();

        switch (id){
            case "productosMediosI" ->{
                String informacion = "El método de los productos medios es un método para generar números pseudoaleatorios. El cual su procedimiento consta de los siguientes pasos:\n" +
                        "\n1. Se seleccionan al azar dos números iniciales, Xo y Y, que tienen D dígitos cada uno.\n" +
                        "\n2. Se multiplica Xo por Y para obtener Z.\n" +
                        "\n3. Xi+1 será el número formado por los D dígitos centrales de Z.\n" +
                        "\n4. El número pseudoaleatorio generado, R, es igual a 0.Xi+1.\n" +
                        "\n5. Para generar el siguiente número, se multiplica Xi+1 por Xi (el último número generado), y se sigue el proceso desde el paso 3.\n" +
                        "\n6. Este proceso se repite hasta obtener la cantidad deseada de números pseudoaleatorios.\n";
                cargarInformacion(event, "/com/example/mainmenu/info.fxml", InfoMenu.class, "Productos Medios", informacion);
            }
            case "multiplicacionContanetI" ->{
                String informacion = "Este método es conocido como el multiplicador constante. El metodo consiste en los siguiente pasos:\n" +
                        "\n1. Seleccionar al azar un número inicial Xo y una constante A, ambos con D dígitos.\n" +
                        "\n2. Calcular Z como el producto de Xo  por A.\n" +
                        "\n3. Xi+1 es el número formado por los D dígitos centrales de Z.\n" +
                        "\n4. El número pseudoaleatorio R generado es Xi+1.\n" +
                        "\n5. Para el siguiente número, calcular Z como el producto de Xi+1 por A.\n" +
                        "\n6. Repetir desde el paso 3 para obtener más números en la secuencia deseada.\n" +
                        "\nEn el caso de que no sea posible obtener los D dígitos centrales directamente (si Z tiene menos de 2D dígitos), se añaden ceros a la izquierda de Z para completarlo.\n" +
                        "\nEste método también busca generar números pseudoaleatorios, pero en lugar de usar dos números iniciales como en el método de productos medios, usa un número inicial y una constante multiplicadora. Esto evita la correlación entre números sucesivos.\n";
                cargarInformacion(event, "/com/example/mainmenu/info.fxml", InfoMenu.class, "Multiplicación Constante", informacion);
            }
            case "KsI" ->{
                String informacion ="La prueba de Kolmogórov-Smirnov (K-S), es un método estadístico no paramétrico utilizado para determinar si dos distribuciones de probabilidad son significativamente diferentes. Aquí está el procedimiento en resumen:\n" +
                        "\nOrdenar los datos: Se ordenan de menor a mayor los números aleatorios generados.\n" +
                        "\nCalcular D+ y D-: Se calculan los valores D+ y D−, que son las máximas diferencias absolutas entre la función de distribución acumulada (FDA) empírica de la muestra y la FDA teórica que se espera bajo la hipótesis nula, tanto por encima como por debajo respectivamente.\n" +
                        "\nDeterminar el valor crítico: Se busca en tablas de valores críticos el valor de dα,n para el nivel de confianza deseado (generalmente 0.05 para un 95% de confianza) y el tamaño de la muestra n. Si el máximo entre D+ y D− es mayor que el valor crítico dα,n, hay evidencia para rechazar la hipótesis nula.\n" +
                        "\nLa hipótesis nula de la prueba K-S es que no hay diferencia significativa entre las distribuciones comparadas. Si la hipótesis nula se rechaza, significa que las muestras provienen de distribuciones diferentes. La prueba es sensible a las diferencias cerca de la mediana de las distribuciones.\n";
                cargarInformacion(event, "/com/example/mainmenu/info.fxml", InfoMenu.class, "Kolmogórov\nSmirnov (K-S)", informacion);
            }
            case "chiCuadradaI" ->{
                String informacion ="La prueba chiCuadrada consta de los siguientes pasos\n" +
                        "1. Agrupe los n números aleatorios generados en K clases disjuntas de igual\n" +
                        "amplitud A.\n" +
                        "2. Halle la frecuencia de cada clase fi.\n" +
                        "3. A = 1/K, Se sigues una distribución con (K-1) grados de libertad.\n" +
                        "4. Determine el valor critico X2\n" +
                        "a, k-1 en la tabla de valores críticos de x2para un nivel\n" +
                        "de confianza (1-a) y (k-1) grados de libertad Si x2>x2\n" +
                        "a, k-1 existe una diferencia\n" +
                        "significativa entre la distribución del conjunto de números generado y la\n" +
                        "distribución uniforme, debe pues rechazar la hipótesis Ho de que la secuencia\n" +
                        "de números generada proviene de una población distribuida uniforme (0,1), en\n" +
                        "caso contrario al no existir diferencia significativa no puede rechazar la\n" +
                        "hipótesis nula Ho.";
                cargarInformacion(event, "/com/example/mainmenu/info.fxml", InfoMenu.class, "Chi Cuadrada", informacion);
            }
            case "corridasI" ->{
                String informacion = "PASO 1 Determine una secuencia S de unos y ceros colocando un cero si el número aleatorio generado i es menor o igual que el número aleatorio generado anterior (i - 1), en caso contrario ponga un uno. La secuencia S contiene (n - 1) números debido a que el primer número no tiene anterior con el que compararlo.\n" +
                        "PASO 2 Halle el número de corridas (rachas) observadas Co. Una corrida viene dada por el número de unos o ceros consecutivos que la forman.\n" +
                        "PASO 3 Calcule el valor esperado y la variancia del número de corridas así como el estadístico Zo \n" +
                        "Dado que Co sigue una distribución normal de media  y variancia, y Zo una distribución normal (0,1)\n" +
                        "Si el estadístico Zo se encuentra dentro de los límites de aceptación no puede rechazar la hipótesis nula (H0) de que los números aleatorios generados son independientes, en caso contrario debe rechazar la hipótesis nula (H0).\n";
                cargarInformacion(event, "/com/example/mainmenu/info.fxml", InfoMenu.class, "Corridas", informacion);
            }
            case "lehmerI" ->{
                String informacion = "El Método de Lehmer, también conocido como algoritmo de multiplicación lineal, que es otro método para generar números pseudoaleatorios. Su procedimiento consta de 7 pasos:\n" +
                        "\n1. Seleccionar un número inicial X0 de n dígitos y una constante A que tenga k dígitos.\n" +
                        "\n2. Calcular Z=X0×A, que resultará en un número de n+k dígitos.\n" +
                        "\n3. Descartar los k dígitos de la izquierda de Z, dejando Y, un número de n dígitos.\n" +
                        "\n4. Xi+1 es el número formado por los dígitos que quedaron después de quitar los k dígitos de la izquierda a Z.\n" +
                        "\n5. El número pseudoaleatorio R generado es 0.1Xi+1.\n" +
                        "\n6. Para el siguiente número, calcular Z=Xi+1×A.\n" +
                        "\n7. Repetir desde el paso 3 para obtener la cantidad deseada de números pseudoaleatorios.\n" +
                        "\nEl método de Lehmer se basa en la selección de una constante multiplicadora y un proceso iterativo que genera números pseudoaleatorios al eliminar una cierta cantidad de dígitos de la izquierda de cada nuevo producto obtenido. Sin embargo, el método tiene un problema inherente: tiende a degenerar a cero.\n";
                cargarInformacion(event, "/com/example/mainmenu/info.fxml", InfoMenu.class, "Lehmer", informacion);
            }
            case "cuadradosMediosI" ->{
                String informacion = "Fue propuesto en los años 40 por el matemático John von Neumann. El proceso consiste en tomar un número inicial (semilla) de 2n cifras y elevarlo al cuadrado para obtener un resultado de hasta 4n cifras. Si es necesario, se añaden ceros a la izquierda para asegurarse de que el resultado tenga exactamente 4n cifras. Luego, se seleccionan las 2n cifras centrales de este resultado, y se coloca un punto decimal delante para formar el nuevo número pseudoaleatorio. Este número se usa como la nueva semilla y el proceso se repite para generar más números.\n" +
                        "El método tiene dos inconvenientes principales:\n" +
                        "\n1. Tiene una fuerte tendencia a degenerar a cero rápidamente. Por ejemplo, si se inicia con una semilla de 1009, los números generados tienden a disminuir en magnitud hasta que todos los números subsecuentes son cero.\n" +
                        "\n2. Los números generados pueden empezar a repetirse en ciclos después de una secuencia corta, lo que limita la cantidad de números pseudoaleatorios únicos que se pueden generar antes de que el ciclo comience de nuevo.\n" +
                        "\nEstos problemas hacen que el método de los cuadrados medios no sea el más adecuado para aplicaciones que requieren una gran cantidad de números aleatorios o para aquellas en las que la aleatoriedad de alta calidad es crucial.\n";
                cargarInformacion(event, "/com/example/mainmenu/info.fxml", InfoMenu.class, "Cuadrados Medios", informacion);
            }
            case "pruebaMediaI" ->{
                String informacion = "La prueba de la media es un procedimiento estadístico usado para determinar si la media de una población corresponde a un valor específico propuesto. Los pasos básicos son:\n" +
                        "\n1. Establecer hipótesis:" +
                        "• Hipótesis nula (H0): La media de la población es igual al valor propuesto (μ = μ0).\n" +
                        "\n• Hipótesis alternativa (H1): La media de la población es diferente del valor propuesto (μ diferente de μ0), pudiendo ser mayor o menor según el análisis.\n" +
                        "\n2. Seleccionar el nivel de significancia: Comúnmente 0.05, probabilidad de rechazar la hipótesis nula siendo verdadera.\n" +
                        "\n3. Calcular el estadístico de prueba: Utilizando la media de la muestra (x̄), la desviación estándar de la muestra (s) y el tamaño de la muestra (n).\n" +
                        "\n4. Comparar este valor con un valor crítico de la distribución t (si la varianza poblacional no es conocida y el tamaño de la muestra es pequeño) o de la distribución normal estándar (si la varianza poblacional es conocida o el tamaño de la muestra es grande).\n" +
                        "\n5. Decisión:" +
                        "Si el valor absoluto del estadístico de prueba es mayor que el valor crítico, se rechaza la hipótesis nula." +
                        "\nSi el valor absoluto del estadístico de prueba es menor que el valor crítico, no hay suficiente evidencia para rechazar la hipótesis nula.\n";
                cargarInformacion(event, "/com/example/mainmenu/info.fxml", InfoMenu.class, "Prueba Media", informacion);
            }
            case "pruebaVarianzaI" ->{
                String informacion = "La prueba de varianza Chi-cuadrado es un método estadístico para determinar si la varianza de una población es igual a un valor específico propuesto. Los pasos principales son:\n" +
                        "\n1. Establecer hipótesis:\n" +
                        "\n• Hipótesis nula (H0): La varianza de la población es igual al valor propuesto (σ² = σ₀²).\n" +
                        "\n• Hipótesis alternativa (H1): La varianza de la población no es igual al valor propuesto (σ² diferente de σ₀²), pudiendo ser mayor o menor.\n" +
                        "\n2. Seleccionar el nivel de significancia: Usualmente 0.05, indica la probabilidad de rechazar la hipótesis nula siendo esta verdadera.\n" +
                        "\n3. Calcular el estadístico de prueba:\n" +
                        "\n• Se utiliza la varianza de la muestra (s²) y el tamaño de la muestra (n) para calcular el estadístico:\n" +
                        "\n• Este valor se compara contra los valores críticos de la distribución Chi-cuadrado con n-1 grados de libertad.\n" +
                        "\n4. Decisión:\n" +
                        "\n• Rechazar la hipótesis nula si el estadístico de prueba cae fuera del rango especificado por los valores críticos.\n";
                cargarInformacion(event, "/com/example/mainmenu/info.fxml", InfoMenu.class, "Prueba Varianza", informacion);
            }

        }
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
    public void pruebaMedia(ActionEvent event) throws IOException {
        cargarMetodo(event, "/metodosFXML/PruebaMedia.fxml", PruebaMedia.class);
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
    public void cargarInformacion(ActionEvent event, String fxmlPath, Class<?> controllerClass, String titulo, String descripcion) throws IOException {
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
        if (controller instanceof InfoMenu) {
            ((InfoMenu) controller).definirTexto(titulo, descripcion);
        }
    }

}