package model;

// Created by srdczk on 2020/1/2

import params.Config;

import java.util.LinkedList;
import java.util.List;
import static util.CustomizeUtil.*;

public class Space {

    public static double[][] sff = generateField(1.5);

    private List<Ped> peds;

    private List<Wall> walls;

    public Space() {
        // 只需要插入和 <>
        peds = new LinkedList<>();
        walls = new LinkedList<>();
    }

    public Space init() {
        add(new Wall(3.5, 5, 6.5, 5));
        add(new Wall(3.5, 6.5, 5, 6.5));
        add(new Wall(5, 6.5, 5,  11.6));
        add(new Wall(5, 11.6, 8.5, 11.6));
        add(new Wall(6.5, 5, 6.5, 10.1));
        add(new Wall(6.5, 10.1, 7, 10.1));
        add(new Wall(7, 10.1, 7, 5));
        add(new Wall(7, 5, 8.5, 5));
        add(new Wall(8.5, 11.6, 8.5, 6.5));
        for (int i = 0; i < 5; i++) {
            boolean pd = true;
            double x = Math.random() * 1.1 + 3.7
                    , y = Math.random() * 1.1 + 5.2;
            while (pd) {
                pd = false;
                for (Ped ped : peds) {
                    if (ped.getCurPos().distanceTo(new Vector(x, y)) < Config.R * 2.0) {
                        pd = true;
                        x = Math.random() * 1.1 + 3.7;
                        y = Math.random() * 1.1 + 5.2;
                        break;
                    }
                }
            }
            add(new Ped(Config.pedId++, x, y, Config.stepLen, Config.R, 1, 0, this));
        }
        return this;
    }

    public void add(Ped ped) {
        peds.add(ped);
    }

    public void add(Wall wall) {
        walls.add(wall);
    }

    public void randomAddPed() {
        boolean pd = true;
        double x = Math.random() * 1.1 + 3.7
                , y = Math.random() * 1.1 + 5.2;
        while (pd) {
            pd = false;
            for (Ped ped : peds) {
                if (ped.getCurPos().distanceTo(new Vector(x, y)) < Config.R * 2.0) {
                    pd = true;
                    x = Math.random() * 1.1 + 3.7;
                    y = Math.random() * 1.1 + 5.2;
                    break;
                }
            }
        }
        add(new Ped(Config.pedId++, x, y, Config.stepLen, Config.R, 1, 0, this));
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Ped> getPeds() {
        return peds;
    }
}
