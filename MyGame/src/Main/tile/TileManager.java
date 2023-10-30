package Main.tile;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp){
        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();

        loadMap("src/Main/maps/map01.txt");
    }

    public void getTileImage(){
        try {
            tile[0] =  new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tile[1] =  new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt.png"));

            tile[2] =  new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stone_bricks.png"));
            tile[2].collision = true;

            tile[3] =  new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/asphalt_superior.png"));

            tile[4] =  new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/asphalt_inferior.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/asphalt_izquierdo.png"));

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/asphalt_derecho.png"));

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/asphalt.png"));

            tile[8] = new Tile();
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sidewalk.png"));

            tile[9] = new Tile();
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/tiles/bush.png"));
            tile[9].collision = true;




        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath) {

        try (FileReader is = new FileReader(filePath);
             BufferedReader br = new BufferedReader(is)) {

            int row = 0;

            String line;

            while ((line = br.readLine()) != null && row < gp.maxWorldRow) {
                System.out.println("linea: " + row + "- " + line);

                String numbers[] = line.split(" ");

                for (int col = 0; col < gp.maxWorldCol; col++) {
                    mapTileNum[col][row] = Integer.parseInt(numbers[col]);
                }

                row++;
            }
        } catch (IOException e) {
            System.out.println("Aquí está el error papu: " + e);
            e.printStackTrace();
        }
    }


    public void draw(Graphics2D g2){

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize> gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            }
            worldCol++;

            if (worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
