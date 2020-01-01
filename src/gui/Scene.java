package gui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import model.Ped;
import params.Config;

import java.net.URL;
import java.util.ResourceBundle;

public class Scene implements Initializable {

    @FXML
    private Canvas canvas;

    @FXML
    private Button start, print;

    @FXML
    private TextField floor;

    private GraphicsContext graphicsContext;

    private Timeline timeline;


    private Ped ped;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initScene();
    }

    private void initScene() {
        graphicsContext = canvas.getGraphicsContext2D();
        ped = new Ped(2, 45);
        canvas.setScaleY(-1);
        canvas.setScaleX(1);
        initLoop();
    }

    private void initLoop() {
        Duration duration = Duration.millis(10);
        KeyFrame frame = getNextKeyFrame(duration);
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(frame);
        timeline.play();
        timeline.pause();
    }

    private KeyFrame getNextKeyFrame(Duration duration) {
        return new KeyFrame(duration, e -> {
            removeAll();
            ped.draw(graphicsContext);
            ped.run();
        });
    }

    private void removeAll() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void onStart() {
        Config.isRunning = !Config.isRunning;
        if (start.getText().equals("Start")) {
            timeline.play();
            start.setText("Pause");
        } else {
            timeline.pause();
            start.setText("Start");
        }
    }

    public void onPrint() {
//        System.out.println("NIASU");
    }

}