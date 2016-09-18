package natureofcodefx;

import natureofcodefx.core.AnimationCanvas;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.HostServices;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class MainController implements Initializable {

    private HostServices hostServices;

    @FXML
    private StackPane contentPane;
    @FXML
    private ListView<String> examplesList;

    private final Map<String, AnimationCanvas> map = new LinkedHashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.map.put("Spring Forces", new SpringForces());
        this.map.put("Hanging Cloth", new HangingCloth());
        this.map.put("Pendulum", new Pendulum());
        this.map.put("Waves", new Waves());
        this.map.put("Direction of Movement", new DirectionOfMovement());
        this.map.put("Force Accumulation", new ForceAccumulation());
        this.map.put("Gravitational Attraction", new GravitationalAttraction());
        this.map.put("Long String", new LongString());
        this.map.put("Oscillation", new OscillationBasics());
        this.map.put("Oscillation2", new OscillationBasics2());
        this.map.put("Rotation", new RotateBaton());
        this.map.put("Spring", new SimpleInteractiveSpring());
        this.map.put("Spiral", new Spiral());
        this.map.put("Vector Acceleration", new VectorAcceleration());
        this.map.put("Vector Division", new VectorDivision());
        this.map.put("Vector Magnitude", new VectorMagnitude());
        this.map.put("Vector Motion", new VectorMotion());
        this.map.put("Vector Multiplication", new VectorMultiplication());
        this.map.put("Vector Normalization", new VectorNormalization());
        this.map.put("Vector Subtraction", new VectorSubtraction());
        this.examplesList.getItems().addAll(this.map.keySet());
        this.examplesList.getSelectionModel().selectedItemProperty().addListener((ob, oldValue, newValue) -> {
            AnimationCanvas oldCanvas = this.map.get(oldValue);
            if (oldCanvas != null) {
                oldCanvas.stop();
            }
            AnimationCanvas newCanvas = this.map.get(newValue);
            newCanvas.widthProperty().bind((ObservableValue) this.contentPane.widthProperty());
            newCanvas.heightProperty().bind((ObservableValue) this.contentPane.heightProperty());
            Optional.ofNullable(newCanvas.setup).ifPresent((Runnable r) -> r.run());
            newCanvas.start();
            this.contentPane.getChildren().remove((Node) oldCanvas);
            this.contentPane.getChildren().add((Node) newCanvas);
        }
        );
    }

    public void handleWindowShowEvent() {
        this.examplesList.getSelectionModel().selectFirst();
    }

    @FXML
    public void githubMouseCliked(MouseEvent event) {
        openURL("https://github.com/francisvalero/natureofcodefx");
    }

    @FXML
    public void fxperimentsMouseCliked(MouseEvent event) {
        openURL(" http://fxperiments.xyz/nature-of-code-in-javafx-canvas/");
    }

    private void openURL(String url) {
        Optional.ofNullable(hostServices).ifPresent(r -> hostServices.showDocument(url));
    }

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

}
