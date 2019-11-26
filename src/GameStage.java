import entity.tile.*;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameStage {
    private Spawner spawer;
    private Target target;
    private Road[] roads;
    private Mountain[] mountains;
    public GameStage(Spawner spawner, Target target, Road[] roads, Mountain[] mountains) {
        this.spawer = spawner;
        this.target = target;
        this.roads = roads;
        this.mountains = mountains;
    }

    public Spawner getSpawer() {
        return this.spawer;
    }
    public Target getTarget() {
        return this.target;
    }
    public Road[] getRoads() {
        return this.roads;
    }
    public Mountain[] getMountains() {
        return this.mountains;
    }
    public static GameStage load(String path) {
        Road[] roads;
        Mountain[] mountains;
        try {
            File text = new File(path);
            Scanner scanner = new Scanner(text);
            try {
                int num = scanner.nextInt();
                roads = new Road[num];
                int posX = scanner.nextInt();
                int posY = scanner.nextInt();
                Spawner spawner = new Spawner(posX, posY);
                roads[0] = spawner;
                for (int i = 1;i < num - 1; i++) {
                    posX = scanner.nextInt();
                    posY = scanner.nextInt();
                    roads[i] = new Road(posX, posY);
                }
                posX = scanner.nextInt();
                posY = scanner.nextInt();
                Target target = new Target(posX, posY);
                roads[num - 1] = target;
                num = scanner.nextInt();
                System.out.println(num);
                mountains = new Mountain[num];
                for (int i = 0; i < num; i++) {
                    posX = scanner.nextInt();
                    posY = scanner.nextInt();
                    mountains[i] = new Mountain(posX, posY);
                }

                scanner.close();
                return new GameStage(spawner, target, roads, mountains);
            } catch (Exception e) {
                System.out.println(" - " + e);
            }
        } catch (Exception e) {
            System.out.println(" + " + e);
        }
        return null;
    }

}