package Main;

import Main.tile.TileManager;
import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // Screen Settings
    final int originalTileSize = 16; // 16 * 16 pixeles
    final int scale = 3; // reescalar *3
    public final int tileSize = originalTileSize * scale; // 48 * 48 pixeles
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixeles
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixeles

    // WORLD SETTINGS
    public final int maxWorldCol = 64;
    public final int maxWorldRow = 64;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this,keyH);

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
/*
    @Override
    public void run() {

        double drawinterval = 1000000000/FPS; // 0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawinterval;

        while (gameThread != null){

            // 1 UPDATE: Update information such character positions
            update();
            // 2 DRAW: Draw the screen with the updated information
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                if (remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawinterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
 */
@Override
public void run() {

    double drawInterval = 1000000000 / FPS; // 0.01666 seconds
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;
    long timer = 0;
    int drawCount = 0;

    while (gameThread != null){
        currentTime = System.nanoTime();
        delta += (currentTime - lastTime) /drawInterval;
        timer += (currentTime - lastTime);
        lastTime = currentTime;

        if (delta >=1){
            update();
            repaint();
            delta--;
            drawCount++;
        }
        if (timer>=1000000000){
            System.out.println("FPS: " + drawCount);
            drawCount = 0;
            timer = 0;
        }
    }
}

    public void update(){
        player.update();
    }
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        tileM.draw(g2);

        player.draw(g2);

        g2.dispose();
    }
}
