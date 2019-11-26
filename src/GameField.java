import Config.Config;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.awt.*;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;

import Effect.AbstractEffect;
import Effect.Smoke;
import entity.tile.*;
import entity.tile.Bullet.*;
import entity.tile.*;
import entity.tile.Point;
import java.awt.Font;
import drawer.Road.*;
import drawer.drawTower;
import javafx.scene.input.KeyCode;

class Surface extends JPanel implements ActionListener{
    private GameStage gameStage;
    private int HP;
    private int REWARD;
    private int count;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private List<Tower> towers;
    private Point point;
    private int spawnTower;
    private int type;
    private int num;
    private File text;
    private Scanner scanner;
    private int countSpawn;
    private GameTile[][] map;
    private int trangThai;
    private Point point0;
    private List<AbstractEffect> effects;
    private Image startImg;
    private Image newGameButtonImg;
    private Image normalTower, machineGunTower, sniperTower, normalTower_Gun, machineGunTower_Gun, sniperTower_Gun;
    private Image[] enemyImg;
    private Image[] bulletImg;
    private Image mountainImg;
    private Image tabImg;
    private Image coinImg;
    private Image HPImg;
    private Image gameOverImg;
    private Image removeTowerImg;
    private Image winImg;
    private AlphaComposite alcom;
    private int delay;

    public Surface(GameStage gameStage) throws IOException {
        this.gameStage = gameStage;
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        towers = new ArrayList<>();
        effects = new ArrayList<>();
        point = new Point(600, 600);
        point0 = new Point(0 ,0);
        spawnTower = 0;
        num = 0;
        HP = 1;
        REWARD = 800;
        count = 0;
        type = 1;
        delay = 30;
        enemyImg = new Image[4];
        bulletImg = new Image[3];
        alcom = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, (float) 0.2);
        winImg = new ImageIcon("out/production/Picture/Other/Win.png").getImage();
        gameOverImg = new ImageIcon("out/production/Picture/Other/Game Over.png").getImage();
        removeTowerImg = new ImageIcon("out/production/Picture/Other/removeTowerButton.png").getImage();
        HPImg = new ImageIcon("out/production/Picture/Other/HP.png").getImage();
        coinImg = new ImageIcon("out/production/Picture/Other/Coin.png").getImage();
        startImg = new ImageIcon("out/production/Picture/Other/Start Game.png").getImage();
        newGameButtonImg = new ImageIcon("out/production/Picture/Other/New Game Button.png").getImage();
        tabImg = new ImageIcon("out/production/Picture/Other/Tab.png").getImage();
        mountainImg = new ImageIcon("out/production/Picture/Map/Mountain.png").getImage();
        bulletImg[0] = new ImageIcon("out/production/Picture/Bullet/NormalBullet.png").getImage();
        bulletImg[1] = new ImageIcon("out/production/Picture/Bullet/MachineGunBullet.png").getImage();
        bulletImg[2] = new ImageIcon("out/production/Picture/Bullet/SniperBullet.png").getImage();
        enemyImg[0] = new ImageIcon("out/production/Picture/Enemy/NormalEnemy.png").getImage();
        enemyImg[1] = new ImageIcon("out/production/Picture/Enemy/SmallerEnemy.png").getImage();
        enemyImg[2] = new ImageIcon("out/production/Picture/Enemy/TankerEnemy.png").getImage();
        enemyImg[3] = new ImageIcon("out/production/Picture/Enemy/BossEnemy.png").getImage();
        normalTower = new ImageIcon("out/production/Picture/Tower/NormalTower.png").getImage();
        normalTower_Gun = new ImageIcon("out/production/Picture/Tower/NormalTower_Gun.png").getImage();
        machineGunTower = new ImageIcon("out/production/Picture/Tower/MachineGunTower.png").getImage();
        machineGunTower_Gun = new ImageIcon("out/production/Picture/Tower/MachineGunTower_Gun.png").getImage();
        sniperTower = new ImageIcon("out/production/Picture/Tower/SniperTower.png").getImage();
        sniperTower_Gun = new ImageIcon("out/production/Picture/Tower/SniperTower_Gun.png").getImage();
        map = new GameTile[12][12];
        trangThai = 0;
        for (int i = 0; i < 12; i++)
            for (int j = 0; j < 12; j++) map[i][j] = null;
        try {
            File text = new File("C:\\Users\\ASUS\\IdeaProjects\\The Game\\src\\Enemy.txt");
            scanner = new Scanner(text);
        } catch (Exception e) {
            System.out.println(" + " + e);
        }
        for (Road road : gameStage.getRoads()) {
            map[(road.getPosX() - 50) / 100][(road.getPosY() - 50) / 100] = road;
        }
        for (Mountain mountain : gameStage.getMountains()) {
            map[(mountain.getPosX() - 50) / 100][(mountain.getPosY() - 50) / 100] = mountain;
        }
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyCode.A)
            }
        });
        addMouseListener(new MouseAdapter() {
            public int check(int x, int y) {
                if (y <= 945 && y >= 895 ) {
                    if (x >= 100 - normalTower.getWidth(null)/2 && x <= 100 + normalTower.getWidth(null)/2) return 100;
                    else if (x >= 320 - machineGunTower.getWidth(null)/2 && x <= 320 + machineGunTower.getWidth(null)/2) return 200;
                    else if (x >= 540 - sniperTower.getWidth(null)/2 && x <= 540 + sniperTower.getWidth(null)/2) return 300;
                    else if ( x >= 750 && x <= 800) return -1;
                }
                return 0;
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = e.getX();
                int y = e.getY();
                if (trangThai == 0) {
                    if (x >= 400 && x <= 800 && y >= 500 && y <= 600) trangThai = 1;
                }
                else if (trangThai == 1){
                    if (y < 800) {
                        if (spawnTower == -1) {
                            if (map[x/100][y/100] instanceof Tower) {
                                Tower tmp_tower = (Tower) map[x/100][y/100];
                                REWARD += tmp_tower.getType() * 50;
                                towers.remove(tmp_tower);
                                spawnTower = 0;
                                map[x/100][y/100] = null;
                            }
                        }
                        else if (spawnTower > 0){
                            if (map[x/100][y/100] == null)  {
                                Tower tower = null;
                                if (spawnTower == 100) {
                                    tower = new Tower(1, normalTower, normalTower_Gun, point.getPosX(), point.getPosY(), Config.NORMAL_TOWER_RANGE, Config.NORMAL_TOWER_SPEED);
                                }
                                else if (spawnTower == 200) {
                                    tower = new Tower(2, machineGunTower, machineGunTower_Gun, point.getPosX(), point.getPosY(), Config.MACHINEGUN_TOWER_RANGE, Config.MACHINEGUN_TOWER_SPEED);
                                }
                                else {
                                    tower = new Tower(3, sniperTower, sniperTower_Gun, point.getPosX(), point.getPosY(), Config.SNIPER_TOWER_RANGE, Config.SNIPER_TOWER_SPEED);
                                }
                                towers.add(tower);
                                map[x/100][y/100] = tower;
                                REWARD -= spawnTower;
                                spawnTower = 0;
                            }
                        }
                    }
                    else {
                        int tmp = check(x, y);
                        if (tmp == spawnTower) spawnTower = 0;
                        else if (tmp <= REWARD) spawnTower = tmp;
                    }
                }

            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                point.set(e.getPoint());
                repaint();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                point0.set(e.getPoint());
                if (e.getY() >= 850) point.set(e.getPoint());
                else {
                    point.setPosX((e.getX() / 100) * 100 + 50);
                    point.setPosY((e.getY() / 100) * 100 + 50);
                }
            }
        });
        initTimer();
    }
    private void initTimer() {
        Timer timer = new Timer(delay, this);
        timer.start();
    }
    public Point nextPoint(Point p) {
        for (int i = 0; i < gameStage.getRoads().length; i++) {
            if (p.equals(gameStage.getRoads()[i].getPoint())) {
                if (i < gameStage.getRoads().length - 1) return new Point(gameStage.getRoads()[i + 1].getPoint());
            }
        }
        return null;
    }
    private void doDrawing(Graphics g) throws IOException {
        Graphics2D g2d = (Graphics2D) g;
        gameStage.getSpawer().doDrawing(g2d);
        gameStage.getTarget().doDrawing(g2d);
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                drawGrass.draw(i*100, j*100, g2d);
                if (map[i][j] instanceof Mountain) {
                    drawMountain.draw(i * 100 + 50, j * 100 + 50, g2d);
                }
                else if (map[i][j] instanceof Road) drawRoad.draw(i*100, j*100, g2d);
            }
        }
        if (trangThai == 0) {
            g2d.drawImage(startImg, 200, 200, null);
            g2d.drawImage(newGameButtonImg, 400, 500, null);
        }
        else if (trangThai == 1) {
            g2d.setPaint(Color.BLACK);
            g2d.drawImage(tabImg, 0, 850, null);
            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            g2d.drawString("     : " + REWARD, 900, 900);
            g2d.drawImage(coinImg, 890, 853, null);
            g2d.drawString("     : " + HP, 900, 970);
            g2d.drawImage(HPImg, 890, 923, null);
            drawTower.draw(normalTower, normalTower_Gun, new Point(100, 920), 0, null, g);
            g2d.drawImage(coinImg, 40, 935, null);
            g2d.drawString("      :100", 40, 980);
            drawTower.draw(machineGunTower, machineGunTower_Gun, new Point(320, 920), 0, null, g);
            g2d.drawImage(coinImg, 260, 935, null);
            g2d.drawString("      :200", 260, 980);
            drawTower.draw(sniperTower, sniperTower_Gun, new Point(540, 920), 0, null, g);
            g2d.drawImage(coinImg, 480, 935, null);
            g2d.drawString("      :300", 480, 980);
            g2d.drawImage(removeTowerImg, 750, 900, null);
            try {
                if (type != 0) {
                    if (num == 0) {
                        type = scanner.nextInt();
                        System.out.println(type);
                        if (type != 0) {
                            num = scanner.nextInt();
                            countSpawn = scanner.nextInt();
                        }
                    }
                    if (type >= 0) if (count == countSpawn) {
                        count = 0;
                        if (type <= 4) enemies.add(new Enemy(type, enemyImg[type-1], gameStage.getSpawer().getPoint(), gameStage.getRoads()[1].getPoint()));
                        num--;
                    }
                } else {
                    if (enemies.isEmpty()) {
                        trangThai = 2;
                        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 50));
                        g2d.drawString("Congratulation! You Win!", 300, 300);
                    }
                }
            } catch (Exception e) {
                System.out.println(" - " + e);
            }
            for (Bullet bullet : bullets) {
                bullet.doDrawing(g);
                bullet.Catch();
                if (!bullet.life()) bullets.remove(bullet);
            }
            for (Tower tower : towers) {
                if (tower.canShoot()) {
                    Enemy tmp = tower.targetEnemy(enemies);
                    if (tmp != null) bullets.add(new Bullet(tower.getType(), bulletImg[tower.getType()-1], tower.getPoint(), tmp));
                }
                tower.doDrawing(g);
            }
            for (AbstractEffect effect : effects) {
                if (effect.life()) effect.doDrawing(g);
                else effects.remove(effect);
            }
            for (Enemy enemy : enemies) {
                boolean life = enemy.life();
                if (life) {
                    if (enemy.getPoint().equals(enemy.getNextPoint())) {
                        Point tmp = nextPoint(enemy.getNextPoint());
                        if (tmp == null) {
                            HP--;
                            if (HP == 0) {
                                trangThai = 3;
                                break;
                            }
                            enemies.remove(enemy);
                            life = false;
                        } else enemy.setNextPoint(tmp);
                    }
                    if (life) enemy.doDrawing(g);
                } else {
                    REWARD += enemy.getReward();
                    if (REWARD > 9999) REWARD = 9999;
                    effects.add(new Smoke(enemy.getPoint()));
                    enemies.remove(enemy);
                }
            }
            if (spawnTower == 100) {
                drawTower.draw(normalTower, normalTower_Gun, point, 0, null, g);
                if (map[point.getPosX()/100][point.getPosY()/100] == null) g2d.setPaint(Color.blue);
                else g2d.setPaint(Color.red);
                g2d.setComposite(alcom);
                g2d.fillOval(point.getPosX() - Config.NORMAL_TOWER_RANGE, point.getPosY() - Config.NORMAL_TOWER_RANGE, Config.NORMAL_TOWER_RANGE * 2, Config.NORMAL_TOWER_RANGE * 2);
            } else if (spawnTower == 200) {
                drawTower.draw(machineGunTower, machineGunTower_Gun, point, 0, null, g);
                if (map[point.getPosX()/100][point.getPosY()/100] == null) g2d.setPaint(Color.blue);
                else g2d.setPaint(Color.red);
                g2d.setComposite(alcom);
                g2d.fillOval(point.getPosX() - Config.MACHINEGUN_TOWER_RANGE, point.getPosY() - Config.MACHINEGUN_TOWER_RANGE, Config.MACHINEGUN_TOWER_RANGE * 2, Config.MACHINEGUN_TOWER_RANGE * 2);
            } else if (spawnTower == 300) {
                drawTower.draw(sniperTower, sniperTower_Gun, point, 0, null, g);
                if (map[point.getPosX()/100][point.getPosY()/100] == null) g2d.setPaint(Color.blue);
                else g2d.setPaint(Color.red);
                g2d.setComposite(alcom);
                g2d.fillOval(point.getPosX() - Config.SNIPER_TOWER_RANGE, point.getPosY() - Config.SNIPER_TOWER_RANGE, Config.SNIPER_TOWER_RANGE * 2, Config.SNIPER_TOWER_RANGE * 2);
            }
            else if (spawnTower == - 1) {
                g2d.drawImage(removeTowerImg, point.getPosX() - removeTowerImg.getHeight(null)/2, point.getPosY() - removeTowerImg.getWidth(null)/2, null);
                g2d.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            }
        }
        else if (trangThai == 2) {
            g2d.drawImage(winImg, 200, 200, null);
        }
        else if (trangThai == 3) {
            g2d.drawImage(gameOverImg, 200, 200, null);
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            doDrawing(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (trangThai == 1) count++;
        repaint();
    }
}

public class GameField extends JFrame {
    public GameField() throws IOException {
        initUI();
    }
    private void initUI() throws IOException {
        // Đường link đến file
        final Surface surface = new Surface(GameStage.load("C:\\Users\\ASUS\\IdeaProjects\\The Game\\src\\demo.txt"));
        add(surface);
        setTitle("Tower Defense");
        setBackground(Color.green);
        setSize(1200, 1200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) throws IOException {
        new GameField();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameField gameField = null;
                try {
                    gameField = new GameField();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                gameField.setVisible(true);
            }
        });
    }
}