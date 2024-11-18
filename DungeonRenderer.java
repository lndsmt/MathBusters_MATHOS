import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.ArrayList; // Explicit import
import java.util.Arrays;    // Explicit import

public class DungeonRenderer extends JPanel {
    private int[][] dungeonLayout;
    private BufferedImage[] tileImages;

    public DungeonRenderer(int[][] dungeonLayout, String[] tilePaths) throws IOException {
        this.dungeonLayout = dungeonLayout;
        tileImages = new BufferedImage[tilePaths.length];
        for (int i = 0; i < tilePaths.length; i++) {
            try {
                File file = new File(tilePaths[i]);
                System.out.println("Attempting to load file: " + file.getAbsolutePath());
                tileImages[i] = ImageIO.read(file);
                if (tileImages[i] == null) {
                    throw new IOException("File format not supported or file is null: " + file.getAbsolutePath());
                }
            } catch (IOException e) {
                System.err.println("Error loading image at index " + i + ": " + tilePaths[i]);
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int tileSize = 48; // Assuming tiles are 48x48
        for (int y = 0; y < dungeonLayout.length; y++) {
            for (int x = 0; x < dungeonLayout[y].length; x++) {
                int tileNumber = dungeonLayout[y][x];
                if (tileNumber > 0 && tileNumber <= tileImages.length) {
                    g.drawImage(tileImages[tileNumber - 1], x * tileSize, y * tileSize, null);
                } else {
                    System.out.println("Tile number out of range or missing image: " + tileNumber);
                }

            }
        }
    }

    public static int[][] loadDungeon(String filePath) throws IOException {
        java.util.List<int[]> rows = new java.util.ArrayList<>(); // Fully qualified name
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            // Split each line by commas and convert to integers
            String[] values = line.split(",");
            int[] row = Arrays.stream(values).mapToInt(Integer::parseInt).toArray();
            rows.add(row);
        }
        br.close();

        // Convert List<int[]> to a 2D int array
        return rows.toArray(new int[0][]);
    }

    public static void main(String[] args) {
        try {
            // Load the dungeon layout
            int[][] dungeon = loadDungeon("src/dungeon_layout.txt");

            // Paths to tile images
            String[] tilePaths = {
                "src/javaProjectAssets/dungeon tiles and floors/tile16.png", 
                "src/javaProjectAssets/dungeon tiles and floors/tile17.png", 
                "src/javaProjectAssets/dungeon tiles and floors/tile18.png", 
                "src/javaProjectAssets/dungeon tiles and floors/tile19.png", 
                "src/javaProjectAssets/dungeon tiles and floors/tile20.png",
                "src/javaProjectAssets/dungeon tiles and floors/tile21.png", 
                "src/javaProjectAssets/dungeon tiles and floors/tile22.png", 
                "src/javaProjectAssets/dungeon tiles and floors/tile23.png", 
                "src/javaProjectAssets/dungeon tiles and floors/tile24.png",
                "src/javaProjectAssets/dungeon tiles and floors/floordesign 1.png", //10
                "src/javaProjectAssets/dungeon tiles and floors/floordesign 2.png", //11
                "src/javaProjectAssets/dungeon tiles and floors/floordesign 3.png", //12
                

            };

            // Create and display the window
            JFrame frame = new JFrame("Dungeon");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(782, 612);
            frame.add(new DungeonRenderer(dungeon, tilePaths));
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
