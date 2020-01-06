package model;

// Created by srdczk on 2020/1/1

import static util.CustomizeUtil.*;
import static params.Config.*;

public class Ped {

    private boolean isGetTarget;

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
            , Space space) {
        this.id = id;
        curPos = new Vector(x, y);
        dir = new Vector(dirX, dirY);
        radius = r;
        stepLen = sl;
        this.space = space;
        isGetTarget = false;
    }

    public void move() {
        Vector target = curPos.newAdd(dir.newMultiply(stepLen));
        if (canMove(curPos, target)) curPos = target;
        if (curPos.getX() >= 6.5 && curPos.getX() <= 7.0
                && curPos.getY() <= 6.5 && curPos.getY() >= 5.0) {
            curPos.setX(curPos.getX() + 5.0);
        }
    }

    public void updateDir() {
        // 在 边缘上 取 10 个点
        double res = Double.MAX_VALUE;
        Vector resTarget = curPos.newAdd(new Vector(1, 0).multiply(stepLen));
        Vector begin = new Vector(1, 0).rotate((int)(Math.random() * 360 / Q));
        for (int i = 0; i < Q; i++) {
            Vector vector = curPos.newAdd(begin.rotate(360 / Q).newMultiply(stepLen));
            double field = 0;
            for (Ped ped : space.getPeds()) {
                if (!ped.equals(this)) field += calculateFromPed(vector, this);
            }
            for (Wall wall : space.getWalls()) {
                if (wall.isIn(vector, R)) field += calculateFromWall(vector, wall);
            }
            field += getField(vector);
            if (canMove(curPos, vector) && field < res) {
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

}
