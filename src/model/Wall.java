package model;

import static util.CustomizeUtil.*;

// Created by srdczk on 2020/1/2


public class Wall {

    private Vector begin, end;

    public Wall(Vector begin, Vector end) {
        this.begin = begin;
        this.end = end;
    }

    public Wall(double beginX, double beginY, double endX, double endY) {
        this.begin = new Vector(beginX, beginY);
        this.end = new Vector(endX, endY);
    }

    // 计算点在直线上的交点
    public Vector crossPoint(Vector vector) {
        // 直线方程 y = a * x + b
        // 考虑 斜率为 0  或者 无穷大
        if (isZero(begin.getX() - end.getX())) {
            // 直线的方程 : x = begin.getX();
            return new Vector(begin.getX(), vector.getY());
        } else if (isZero(begin.getY() - end.getY())) {
            // 直线 方程: y = begin.getY();
            return new Vector(vector.getX(), begin.getY());
        } else {
            double aW = (end.getY() - begin.getY()) / (end.getX() - begin.getX())
                    , bW = begin.getY() - aW * begin.getX()
                    , aV =  (-1) / aW
                    , bV = vector.getY() - aV * vector.getX();
            // 交点
            return new Vector((bV - bW) / (aW - aV), aW * ((bV - bW) / (aW - aV)) + bW);
        }
    }

    // 判断 半径 r 作用下 , 点 是否在 Wall 的作用范围内
    public boolean isIn(Vector vector, double r) {
        Vector metaDir = end.normalize(begin).multiply(r / 2.0);
        Vector xBegin = begin.newSubtract(metaDir), xEnd = end.newAdd(metaDir);

        return vector.getX() >= Math.min(xBegin.getX(), xEnd.getX())
                && vector.getX() <= Math.max(xBegin.getX(), xEnd.getX())
                && vector.getY() >= Math.min(xBegin.getY(), xEnd.getY())
                && vector.getY() <= Math.max(xBegin.getY(), xEnd.getY());

    }

    public double distanceTo(Vector vector) {
        // 点到线的距离
        return vector.distanceTo(crossPoint(vector));
    }

    public Vector getBegin() {
        return begin;
    }

    public Vector getEnd() {
        return end;
    }
}
