package params;

import model.Ped;

import java.util.HashMap;

public class Config {

    public static final double WIDTH = 2, HEIGHT = 0;

    public static boolean isRunning = false;

    public static final double SCALE = 25.0;

    private static int pedId = 0;

    // 通过行人 id  索引行人
    public static final HashMap<Integer, Ped> pedMap = new HashMap<>();

    public static final int Q = 10;

    public static final double R = 0.2;

    public static final double stepLen = 0.3;

    public static double miuP = 10000;
    public static double vP = 0.4;
    public static double aP = 1;
    public static double bP = 0.2;
    public static double gP = 0.4;
    public static double hP = 1;



    public static double miuO = 10000;
    public static double vO = 0.2;
    public static double aO = 3;
    public static double bO = 2;
    public static double hO = 6;

    public static double FI = Math.PI / 2.0;

    public static double delt = 0.2;

}
