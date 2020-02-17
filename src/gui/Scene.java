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
import model.Floor;
import model.Ped;
import model.Space;
import params.Config;
import util.CustomizeUtil;
import util.StringUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class Scene implements Initializable {

    @FXML
    private Canvas canvas;

    @FXML
    private Button start, init;

    @FXML
    private TextField floor;

    private GraphicsContext graphicsContext;

    private Timeline timeline;

    private Space space;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initScene();
    }

    private void initScene() {

        graphicsContext = canvas.getGraphicsContext2D();
//        canvas.setScaleY(-1);
//        canvas.setScaleX(1);
        space = new Space().init();
        initLoop();
    }

    private void initLoop() {
        Duration duration = Duration.millis(100);
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
            for (Floor floor : space.getMap().values()) {
                CustomizeUtil.drawFloor(graphicsContext, floor);
                try {
                    for (Ped ped : floor.getPeds()) {
                        ped.updateDir();
                        ped.move();
                        CustomizeUtil.drawPed(graphicsContext, ped);
                    }
                } catch (Exception p) {
                    p.printStackTrace();
                }
            }
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

    public void onInit() {
        int maxFloor = StringUtil.stringToInt(floor.getText());
        if (maxFloor < 1) maxFloor = 1;
        else if (maxFloor > 50) maxFloor = 50;
        Config.maxFloor = maxFloor;
        space = new Space().init();
    }

}