package org.cis1200.brickBreaker;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Level {

    private Coordinates[][] brickPos; // the positions of the brick

    private BufferedReader br; // the buffered reader used to read the files

    public Level(String filePath) {
        Coordinates[][] brickPos = new Coordinates[10][10];
        if (filePath == null) {
            throw new IllegalArgumentException("Filepath is null");
        }
        try {
            br = new BufferedReader(new FileReader(filePath));
            String line = br.readLine();
            while (line != null) {
                for (int i = 0; i < brickPos.length; i++) {
                    for (int j = 0; j < brickPos[i].length; j++) {
                        if (line != null) {
                            String coordinates = line.substring(6, line.length() - 1);
                            int xPos = Integer.parseInt(coordinates.substring
                                (0, coordinates.indexOf(",")));
                            int yPos = Integer.parseInt(coordinates.substring
                                (coordinates.indexOf(",") + 1));
                            brickPos[i][j] = new Coordinates(xPos, yPos);
                            line = br.readLine();
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found!");
        } catch (IOException e) {
            closeReader();
            System.out.println("No more data to read!");
        }
        this.brickPos = brickPos;
    }

        public Coordinates[][] getBrickPos() {
            return brickPos;
        }

        public void closeReader() {
            try {
                br.close();
            } catch (IOException e) {
                System.out.println("Could not close the reader!");
            }
        }
}
