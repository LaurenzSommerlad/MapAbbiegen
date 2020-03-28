package sample;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main extends Application {

    final int WIDTH = 1400, HEIGHT = 800;

    final int unitDist = 90;

    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    GraphicsContext gc = canvas.getGraphicsContext2D();

    //TODO: replace the files
    private String map0 = "abbiegen0.txt";
    private String map1 = "abbiegen1.txt";
    private String map2 = "abbiegen2.txt";
    private String map3 = "abbiegen3.txt";

    private int numRoads;
    private Point2D start;
    private Point2D end;

    int x;
    int y;
    int x1;
    int y1;

    int[][] map;

    int maxX = 0;
    int maxY = 0;

    int margin = 10;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group(canvas);
        ScrollPane scrollPane = new ScrollPane(root);
        primaryStage.setTitle("Map Abbiegen");
        primaryStage.setScene(new Scene(scrollPane, WIDTH, HEIGHT));
        primaryStage.show();

        String currentMap = map3;
        readData(currentMap);
        draw(currentMap);
    }

    private void readData(String path) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(path)))) {
            numRoads = Integer.parseInt(reader.readLine());
            String[] coords = reader.readLine().replace("(", "").replace(")", "").split(",");
            x = Integer.parseInt(coords[0]);
            y = Integer.parseInt(coords[1]);
            start = new Point2D(x, y);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
            coords = reader.readLine().replace("(", "").replace(")", "").split(",");
            x = Integer.parseInt(coords[0]);
            y = Integer.parseInt(coords[1]);
            end = new Point2D(x, y);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
            for (int i = 1; i <= numRoads; i++) {
                coords = reader.readLine().replace("(", "").replace(")", "").replace(" ", ",").split(",");
                x = Integer.parseInt(coords[0]);
                y = Integer.parseInt(coords[1]);
                x1 = Integer.parseInt(coords[2]);
                y1 = Integer.parseInt(coords[3]);
                maxX = Math.max(maxX, x);
                maxY = Math.max(maxY, y);
                maxX = Math.max(maxX, x1);
                maxY = Math.max(maxY, y1);
            }
            map = new int[maxX+1][maxY+1];
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException nfe) {
            System.out.println("This File does not match the pattern it should.");
        }
    }

    private void draw(String path) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(path)))) {
            numRoads = Integer.parseInt(reader.readLine());
            String[] coords = reader.readLine().replace("(", "").replace(")", "").split(",");
            x = Integer.parseInt(coords[0]);
            y = Integer.parseInt(coords[1]);
            start = new Point2D(x, y);
            map[x][y] = -1;
            coords = reader.readLine().replace("(", "").replace(")", "").split(",");
            x = Integer.parseInt(coords[0]);
            y = Integer.parseInt(coords[1]);
            end = new Point2D(x, y);
            map[x][y] = 0;
            for (int i = 1; i <= numRoads; i++) {
                coords = reader.readLine().replace("(", "").replace(")", "").replace(" ", ",").split(",");
                x = Integer.parseInt(coords[0]);
                y = Integer.parseInt(coords[1]);
                x1 = Integer.parseInt(coords[2]);
                y1 = Integer.parseInt(coords[3]);
                map[x][y] = i;
                map[x1][y1] = i;
                gc.strokeLine(margin+x*unitDist, margin+(maxY-y)*unitDist, margin+x1*unitDist, margin+(maxY-y1)*unitDist);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException nfe) {
            System.out.println("This File does not match the pattern it should.");
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
