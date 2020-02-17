package model;

// Created by srdczk on 2020/1/2

import params.Config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import static util.CustomizeUtil.*;

public class Space {

    public static double[][] sff = generateField(1.5);

    private List<Ped> peds;

    private List<Wall> walls;

    private HashMap<Integer, Floor> map = new HashMap<>();

    public Space() {
        // 只需要插入和 <>
        peds = new LinkedList<>();
        walls = new LinkedList<>();
    }

    public Space init() {
        if (Config.maxFloor >= 10) Config.SCALE = 14;
        else Config.SCALE = 14 + (10 - Config.maxFloor) * 2;
        // 通过 maxFloor <---> 控制
        if (Config.maxFloor > 40) {
            Config.WIDTH = 0;
            Config.HEIGHT = 0;
        } else if (Config.maxFloor > 30) {
            Config.WIDTH = 0;
            Config.HEIGHT = 7;
        } else if (Config.maxFloor > 20) {
            Config.WIDTH = 0;
            Config.HEIGHT = 9;
        } else if (Config.maxFloor > 10) {
            Config.WIDTH = 0;
            Config.HEIGHT = 15;
        } else {
            Config.WIDTH = (Config.maxFloor - 10) * 5;
            Config.HEIGHT = 20 + (Config.maxFloor - 10) * 2;
        }
        for (int i = 0; i < Config.maxFloor; i++) {
            if (i == 0) map.put(i, new Floor(0, true));
            else map.put(i, new Floor(i, false));
        }
        for (int i = 0; i < Config.maxFloor; i++) {
            addRandomPed(i);
        }
        // 每层随机加入 15 个人 --->
        return this;
    }
    private void addRandomPed(int floor) {
        HashSet<Ped> peds = map.get(floor).getPeds();
        while (peds.size() < 15) {
            double x = 0.2 + Math.random() * 1.1, y = -1.3 + Math.random() * 1.1;
            boolean pd = true;
            while (pd) {
                pd = false;
                for (Ped ped : peds) {
                    if (getDistance(ped.getCurPos().getX(), ped.getCurPos().getY(),
                            x, y) < 0.4) {
                        x = 0.2 + Math.random() * 1.1;
                        y = -1.3 + Math.random() * 1.1;
                        pd = true;
                        break;
                    }
                }
            }
            Vector sub = calXY(floor);
            peds.add(new Ped(Config.pedId++, x + sub.getX(), y + sub.getY(), Config.stepLen, Config.R, 1, 0, this, floor));
        }
    }

    public void add(Ped ped) {
        peds.add(ped);
    }

    public void add(Wall wall) {
        walls.add(wall);
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Ped> getPeds() {
        return peds;
    }

    public HashMap<Integer, Floor> getMap() {
        return map;
    }
}
