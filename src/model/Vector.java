package model;

import util.CustomizeUtil;

public class Vector {
    private double x, y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public double distanceTo(Vector vector) {
        return Math.sqrt((x - vector.x) * (x - vector.x)
                + (y - vector.y) * (y - vector.y));
    }

    public Vector reverse() {
        this.x = -this.x;
        this.y = -this.y;
        return this;
    }

    public Vector newAdd(Vector vector) {
        return new Vector(x + vector.x, y + vector.y);
    }

    public Vector add(Vector vector) {
        x += vector.x;
        y += vector.y;
        return this;
    }

    public Vector newSubtract(Vector vector) {
        return new Vector(x - vector.x, y - vector.y);
    }

    public Vector subtract(Vector vector) {
        x -= vector.x;
        y -= vector.y;
        return this;
    }

    public Vector newMultiply(Vector vector) {
        return new Vector(x * vector.x, y * vector.y);
    }

    public Vector multiply(Vector vector) {
        x *= vector.x;
        y *= vector.y;
        return this;
    }

    public Vector newMultiply(double d) {
        return new Vector(x * d, y * d);
    }

    public Vector multiply(double d) {
        x *= d;
        y *= d;
        return this;
    }

    public Vector normalize(Vector vector) {
        if (CustomizeUtil.isSameVector(vector, this)) return new Vector(1, 0);
        double distance = distanceTo(vector);
        double x = this.x - vector.x;
        double y = this.y - vector.y;
        x /= distance;
        y /= distance;
        return new Vector(x, y);
    }
    // 逆时针 旋转 角度制的值
    public Vector rotate(int angle) {
        return rotate((angle / 180.0 * Math.PI));
    }

    // 逆时针旋转弧度制的值
    public Vector rotate(double angle) {
        double tx = x * Math.cos(angle) - y * Math.sin(angle);
        double ty = y * Math.cos(angle) + x * Math.sin(angle);
        x = tx;
        y = ty;
        return this;
    }

    public Vector newRotate(int angle) {
        return newRotate((angle / 180.0 * Math.PI));
    }

    public Vector newRotate(double angle) {
        return new Vector(x * Math.cos(angle) - y * Math.sin(angle)
                , y * Math.cos(angle) + x * Math.sin(angle));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
