package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Floor {
    private int floor;
    private boolean isEnd;
    private double startX;
    private double startY;

    private HashSet<Ped> peds = new HashSet<>();

    private List<Wall> walls = new ArrayList<>();
    // 虚拟墙面, 用来绘制 阶梯 <---> 计算场域值的大小
    private List<Wall> virtual = new ArrayList<>();

    public Floor(int floor, boolean isEnd) {
        this.floor = floor;
        this.isEnd = isEnd;
        // startX, 和 startY 可以通过 floor 计算出来
        // 增加墙面
        // 先添加必须添加的
        // 每一行最多 10 个
        int x = floor / 10, y = floor % 10;
        startY = 5 + x * (10);
        startX = 5 + (9 - y) * (5);
        walls.add(new Wall(startX, startY - 1.5, startX, startY + 6.6));
        walls.add(new Wall(startX, startY + 6.6, startX + 3.5, startY + 6.6));
        walls.add(new Wall(startX + 1.5, startY + 5.1, startX + 2.0, startY + 5.1));
        walls.add(new Wall(startX + 2.0, startY + 5.1, startX + 2.0, startY + 1.5));
        walls.add(new Wall(startX + 2.0, startY + 1.5, startX + 1.5, startY + 1.5));
        walls.add(new Wall(startX + 1.5, startY + 1.5, startX + 1.5, startY + 5.1));
        walls.add(new Wall(startX + 1.5, startY - 1.5, startX + 1.5, startY));
        if (isEnd) {
            walls.add(new Wall(startX + 3.5, startY + 6.6, startX + 3.5, startY + 1.5));
            walls.add(new Wall(startX + 3.5, startY + 1.5, startX + 5, startY + 1.5));
            walls.add(new Wall(startX + 1.5, startY, startX + 5, startY));
        } else {
            walls.add(new Wall(startX + 3.5, startY + 6.6, startX + 3.5, startY));
            walls.add(new Wall(startX + 1.5, startY, startX + 3.5, startY));
        }
    }

    public int getFloor() {
        return floor;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public List<Wall> getVirtual() {
        return virtual;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public void removePed(Ped ped) {
        peds.remove(ped);
    }

    public void addPed(Ped ped) {
        peds.add(ped);
    }

    public HashSet<Ped> getPeds() {
        return peds;
    }
}
