package util;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Ped;
import model.Space;
import model.Vector;
import model.Wall;
import params.Config;

import java.util.ArrayList;
import java.util.List;

import static params.Config.*;

public class CustomizeUtil {

    public static boolean isZero(double d) {
        return Math.abs(d) <= 1e-6;
    }

    public static boolean isSameVector(Vector a, Vector b) {
        return isZero(a.getX() - b.getX())
                && isZero(a.getY() - b.getY());
    }

    public static void drawWall(GraphicsContext graphicsContext, Wall wall) {
        graphicsContext.strokeLine(SCALE * WIDTH + wall.getBegin().getX() * SCALE
                , SCALE * HEIGHT + wall.getBegin().getY() * SCALE
                , SCALE * WIDTH + wall.getEnd().getX() * SCALE
                , SCALE * wall.getEnd().getY() + SCALE * HEIGHT);
    }

    public static void drawPed(GraphicsContext graphicsContext, Ped ped) {
        graphicsContext.setFill(Color.RED);
        graphicsContext.fillOval(SCALE * WIDTH + (ped.getCurPos().getX() - ped.getRadius()) * SCALE
                , SCALE * HEIGHT + (ped.getCurPos().getY() - ped.getRadius()) * SCALE
                , 2 * ped.getRadius() * SCALE
                , 2 * ped.getRadius() * SCALE);
    }

    private static class Node {
        double len, s;
        public Node(double l, double s) {
            len = l;
            this.s = s;
        }
    }

    // 生成转角场域
    public static double[][] generateField(double x) {
        int len = (int)(x * 10);
        //分成100个角度
        int n = 200;
        double dx = x / len;
        double[][] res = new double[len][len];
        List<Node>[][] nodes = new List[len][len];

        for (int i = 0; i < len; i++) {
            nodes[i] = new List[len];
            for (int j = 0; j < len; j++) {
                nodes[i][j] = new ArrayList<>();
            }
        }
        //计算第i条线会和哪些格点相交, 只需要计算一半
        //由于对称性,只需要计算
        //k == 0 时候, 不计算
        for (int k = 1; k <= n / 2; k++) {
            //当小于45度时候
            double tanA = Math.tan(Math.PI / 2.0 / n * k);
            double sinA = Math.sin(Math.PI / 2.0 / n * k);
            double cosA = Math.cos(Math.PI / 2.0 / n * k);
            if (k < n / 2) {
                //计算和格点相交
                for (int i = 1; i <= len; i++) {
                    double dy = (i * dx) * tanA;
                    int s = (int)(dy / dx);
                    double pd = dx * tanA;
                    double py = dy - s * dx;
                    if (!isZero(py)) {
                        if (py > pd) {
                            nodes[i - 1][s].add(new Node(dx / cosA, ((double)k / n) * Config.FI * x));
                            nodes[s][i - 1].add(new Node(dx / cosA, ((double)(n - k) / n) * Config.FI * x));
                        } else {
                            nodes[i - 1][s].add(new Node(py / sinA, ((double)k / n) * Config.FI * x));
                            nodes[s][i - 1].add(new Node(py / sinA, ((double)(n - k) / n) * Config.FI * x));
                        }
                    }
                }
            } else {
                //当等于45度的时候
                for (int i = 0; i < len; i++) {
                    nodes[i][i].add(new Node(dx / Math.cos(Math.PI / 4), 0.5 * Config.FI * x));
                }
            }
        }
        //当角度等于90度的时候, 还未计算
        for (int i = 0; i < len; i++) {
            nodes[0][i].add(new Node(dx, Config.FI * x));
        }

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (nodes[i][j].size() == 0) {
                    res[i][j] = 0;
                } else {
                    double p = 0;
                    double q = 0;
                    for (Node node : nodes[i][j]) {
                        p += node.len * node.s;
                        q += node.len;
                    }
                    res[i][j] = p / q;
                }
            }
        }
        return res;
    }

    public static double getField(Vector vector) {
        // 计算场域的值
        if (vector.getX() <= 5.0) {
            return 1500000 - (vector.getX()) * 100;
        }
        if (vector.getX() >= 5.0 && vector.getX() <= 6.5
                && vector.getY() <= 6.5 && vector.getY() >= 5.0) {
            int x = (int) ((vector.getX() - 5.0) * 10), y = (int) ((6.5 - vector.getY()) * 10);
            if (x > 14 || y > 14) return 1500000;
            return 1400000 + 50 * Space.sff[x][y];
        }
        if (vector.getX() >= 5.0 && vector.getX() <= 6.5
                && vector.getY() >= 6.5 && vector.getY() <= 10.1) {
            return 1400000 - (vector.getY() - 6.5) * 100;
        }
        if (vector.getX() >= 5.0 && vector.getX() <= 6.5
                && vector.getY() >= 10.1 && vector.getY() <= 11.6) {
            int x = (int) ((vector.getY() - 10.1) * 10), y = (int) ((6.5 - vector.getX()) * 10);
            if (x > 14 || y > 14) return 1500000;
            return 1300000 + 50 * Space.sff[x][y];
        }
        if (vector.getX() >= 6.5 && vector.getX() <= 7.0
                && vector.getY() <= 11.6 && vector.getY() >= 10.1) {
            return 1300000 - (vector.getX() - 6.5) * 100;
        }
        if (vector.getX() >= 7.0 && vector.getX() <= 8.5
                && vector.getY() >= 10.1 && vector.getY() <= 11.6) {
            int x = (int) ((vector.getX() - 7.0) * 10), y = (int) ((vector.getY() - 10.1) * 10);
            if (x > 14 || y > 14) return 1500000;
            return 1200000 + 50 * Space.sff[x][y];
        }
        if (vector.getX() >= 7.0 && vector.getX() <= 8.5
                && vector.getY() <= 10.1 && vector.getY() >= 6.5) {
            return 1200000 - (10.1 - vector.getY()) * 100;
        }
        if (vector.getX() >= 7.0 && vector.getX() <= 8.5
                && vector.getY() >= 5.0 && vector.getY() <= 6.5) {
            int x = (int) ((6.5 - vector.getY()) * 10), y = (int) ((vector.getX() - 7.0) * 10);
            if (x > 14 || y > 14) return 1500000;
            return 1100000 + Space.sff[x][y] * 50;
        }
        if (vector.getX() <= 7.0 && vector.getX() >= 6.5
                && vector.getY() <= 6.5 && vector.getY() >= 5.0) {
            return 1100000 - (7.0 - vector.getX()) * 100;
        }
        if (vector.getX() <= 12.0 && vector.getX() >= 11.5
                && vector.getY() <= 6.5 && vector.getY() >= 5.0) {
            return 1100000 - (12.0 - vector.getX()) * 100;
        }
        if (vector.getX() <= 11.5 && vector.getX() >= 10.0
                && vector.getY() <= 6.5 && vector.getY() >= 5.0) {
            int x = (int) ((11.5 - vector.getX()) * 10), y = (int) ((6.5 - vector.getY()) * 10);
            if (x > 14 || y > 14) return 1500000;
            return 1000000 + Space.sff[x][y] * 50;
        }
        if (vector.getX() <= 11.5 && vector.getX() >= 10.0
                && vector.getY() >= 6.5 && vector.getY() <= 10.1) {
            return 1000000 - (vector.getY() - 6.5) * 100;
        }
        if (vector.getX() <= 11.5 && vector.getX() >= 10.0
                && vector.getY() >= 10.1 && vector.getY() <= 11.6) {
            int x = (int) ((vector.getY() - 10.1) * 10), y = (int) ((11.5 - vector.getX()) * 10);
            if (x > 14 || y > 14) return 1500000;
            return 900000 + Space.sff[x][y] * 50;
        }
        if (vector.getX() >= 11.5 && vector.getX() <= 12.0
                && vector.getY() >= 10.1 && vector.getY() <= 11.6) {
            return 900000 - (vector.getX() - 11.5) * 100;
        }
        if (vector.getX() >= 12.0 && vector.getX() <= 13.5
                && vector.getY() >= 10.1 && vector.getY() <= 11.6) {
            int x = (int) ((vector.getX() - 12.0) * 10), y = (int) ((vector.getY() - 10.1) * 10);
            if (x > 14 || y > 14) return 1500000;
            return 800000 + Space.sff[x][y] * 50;
        }
        if (vector.getX() >= 12.0 && vector.getX() <= 13.5
            && vector.getY() <= 10.1 && vector.getY() >= 6.5) {
            return 800000 - (10.1 - vector.getY()) * 100;
        }
        if (vector.getX() >= 12.0 && vector.getX() <= 13.5
                && vector.getY() <= 6.5 && vector.getY() >= 5.0) {
            int x = (int) ((6.5 - vector.getY()) * 10), y = (int) ((13.5 - vector.getX()) * 10);
            if (x > 14 || y > 14) return 1500000;
            return 700000 + Space.sff[x][y] * 50;
        }
        if (vector.getX() >= 13.5) {
            return 700000 - (vector.getX() - 13.5) * 100;
        }
        return 1500000;
    }

    public static double calculateFromPed(Vector vector, Ped ped) {
        // 计算 位置 到行人之间的作用力
        double dis = vector.distanceTo(ped.getCurPos());
        if (dis <= Config.gP) {
            return Config.miuP;
        } else if (dis > Config.gP && dis <= Config.gP + Config.hP) {
            return Config.vP * Math.exp(-Config.aP * Math.pow(dis, Config.bP));
        } else {
            return 0;
        }
    }

    public static double calculateFromWall(Vector vector, Wall wall) {
        // 墙作用力的大小
        if (wall.isIn(vector, 0)) {
            double dis = wall.distanceTo(vector);
            if (dis < Config.gP / 2.0) {
                return Config.miuO;
            } else if (dis >= Config.gP / 2.0 && dis <= Config.hO) {
                return Config.vO * Math.exp(-Config.aO * Math.pow(dis, Config.bO));
            } else {
                return 0;
            }
        } else return 0;
    }

    public static boolean canMove(Vector cur, Vector target) {
        // pd Enum <--> floor
        if (target.getY() >= 6.5 && target.getX() <= 5) return false;
        if (target.getY() >= 11.6) return false;
        if (target.getY() <= 5) return false;
        if (target.getX() >= 8.5 && target.getX() <= 10) return false;
        if (target.getX() <= 7.0 && target.getX() >= 6.5
                && target.getY() <= 10.1 && target.getY() >= 6.5) return false;
        // 判断 target
        if ((cur.getX() >= 5 && cur.getX() <= 6.5)
                && (target.getX() >= 6.5 && target.getY() <= 10.1)) return false;
        if ((cur.getX() >= 6.5 && cur.getX() <= 7.0 && cur.getY() >= 5 && cur.getY() <= 6.5)
                && (target.getX() <= 6.5)) return false;
        if ((cur.getX() <= 12.0 && cur.getX() >= 11.5 && cur.getY() <= 6.5 && cur.getY() >= 5.0)
                && (target.getX() >= 12.0)) return false;
        if (target.getY() <= 10.1 && target.getY() >= 6.5
                && target.getX() <= 12.0 && target.getX() >= 11.5) return false;
        if (target.getY() >= 6.5 && target.getX() >= 13.5) return false;
        return true;
    }

}
