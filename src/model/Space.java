package model;

// Created by srdczk on 2020/1/2

import params.Config;

import java.util.LinkedList;
import java.util.List;
import static util.CustomizeUtil.*;

public class Space {

    public static double[][] sff = generateField(3);

    private List<Ped> peds;

    private List<Wall> walls;

    public Space() {
        // 只需要插入和 <>
        peds = new LinkedList<>();
        walls = new LinkedList<>();
    }

    public Space init() {
        add(new Wall(5, 5, 20, 5));
        add(new Wall(20, 5, 20, 20));
        add(new Wall(5, 8, 17, 8));
        add(new Wall(17, 8, 17, 20));
        for (int i = 0; i < 20; i++) {
            boolean pd = true;
            double x = Math.random() * 2.6 + 5.2, y = 5.2 + Math.random() * 2.6;
            while (pd) {
                pd = false;
                for (Ped ped : peds) {
                    if ((ped.getCurPos().distanceTo(new Vector(x, y))) < Config.R * 2.0) {
                        pd = true;
                        x = Math.random() * 2.6 + 5.2;
                        y = 5.2 + Math.random() * 2.6;
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

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Ped> getPeds() {
        return peds;
    }
}
