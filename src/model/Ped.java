package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static params.Config.SCALE;

public class Ped {

    private double x, y;

    private int dir = 0;

    public Ped(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void run() {
        switch (dir) {
            case 0:
                x += Math.random() * 2;
                if (x > 50) dir = 1;
                break;
            case 1:
                y -= Math.random() * 2;
                if (y < 10) dir = 2;
                break;
            case 2:
                x -= Math.random() * 2;
                if (x < 10) dir = 3;
                break;
            case 3:
                y += Math.random() * 2;
                if (y > 50) dir = 0;
                break;
        }
    }

    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillOval(x * SCALE - 1.5 * SCALE, y * SCALE - 1.5 * SCALE, 3 * SCALE, 3 * SCALE);
    }

}
