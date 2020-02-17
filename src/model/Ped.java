package model;

// Created by srdczk on 2020/1/1

import javafx.scene.paint.Color;
import params.Config;

import static util.CustomizeUtil.*;
import static params.Config.*;

public class Ped {

    // 行人的楼层数
    private int floor;

    private Color color;

    // 判断是否是 起始楼层 进入汇流 的人群
    private boolean isStart;

    private boolean isGetTarget;

    private double[] rgb;

    private Vector curPos;

    private Vector dir;

    private double radius;

    private double stepLen;
    // id 行人索引
    private int id;
    // space 指针
    private Space space;

    public Ped(int id, double x, double y
            , double sl, double r
            , double dirX, double dirY
            , Space space, int floor) {
        this.id = id;
        curPos = new Vector(x, y);
        dir = new Vector(dirX, dirY);
        radius = r;
        stepLen = sl;
        this.space = space;
        isGetTarget = false;
        isStart = true;
        this.floor = floor;
        // 不同楼层的人用不同的颜色来表示
        int i = floor % 3;
        if (i == 0) color = Color.RED;
        else if (i == 1) color = Color.GREEN;
        else color = Color.BLUE;
    }


    public Color getColor() {
        return color;
    }

    public void move() {
        Vector target = curPos.newAdd(dir.newMultiply(stepLen));
        Vector sub = calXY(floor);
        Vector des = target.newSubtract(sub), now = curPos.newSubtract(sub);
        Block desBlock = block(des, floor), nowBlock = block(now, floor);
        if (floor == 0 && now.getX() > 5) {
            isGetTarget = true;
            return;
        }
        if (desBlock == Block.SECOND_INTERVAL && nowBlock == Block.FOURTH_CORNER) {
            Vector newSub = calXY(--floor);
            isStart = false;
            curPos = newSub.add(des);
            space.getMap().get(floor + 1).wantRemove.add(this);
        } else {
            curPos = target;
        }
    }

    public void updateDir() {
        Floor at = space.getMap().get(floor);
        // 在 边缘上 取 10 个点
        double res = Double.MAX_VALUE;
        Vector resTarget = curPos.newAdd(new Vector(1, 0).multiply(stepLen));
        Vector begin = new Vector(1, 0).rotate((int)(Math.random() * 360 / Q));
        for (int i = 0; i < Q; i++) {
            Vector vector = curPos.newAdd(begin.rotate(360 / Q).newMultiply(stepLen));
            double field = 0;

            for (Ped ped : at.getPeds()) {
                if (!ped.equals(this)) field += calculateFromPed(vector, this);
            }
            for (Wall wall : at.getWalls()) {
                if (wall.isIn(vector, R)) field += calculateFromWall(vector, wall);
            }
            Vector sub = calXY(floor);
            Vector des = vector.newSubtract(sub), now = curPos.newSubtract(sub);
            field += getField(des, now, floor, isStart);
            if (field < res) {
                res = field;
                resTarget = vector;
            }
        }
        // dir  计算
        dir = resTarget.normalize(curPos);
    }

    public double getRadius() {
        return radius;
    }

    public int getId() {
        return id;
    }

    public Vector getCurPos() {
        return curPos;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getFloor() {
        return floor;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public boolean getIsStart() {
        return isStart;
    }

    public boolean isGetTarget() {
        return isGetTarget;
    }

    public void setRgb(double[] rgb) {
        this.rgb = rgb;
    }

    public double[] getRgb() {
        return rgb;
    }

}
