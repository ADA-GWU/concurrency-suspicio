import javax.swing.*;
import java.awt.*;

public class Main {

    // printing image in JFame
    private static JFrame window;
    private static JLabel image;
    private static Integer chunkSize;
    private static Picture picture;

    //Gives size of chunk
    public static Integer getChunkSize() {
        return chunkSize;
    }

    //give number of chunks to work for
    public static Integer getChunks() {
        return (picture.height() / chunkSize) * (picture.width() / chunkSize);
    }

    //returns number of rows
    public static Integer getWidthChunks() {
        return picture.width() / chunkSize;
    }

    //returns number of columns
    public static Integer getHeightChunks() {
        return picture.height() / chunkSize;
    }

    //setsPicture from external
    public static void setPicture(Picture picture) {
        Main.picture = picture;
    }

    //save
    public static void save() {
        picture.show();
    }

    //Creates window
    static void createWindow() {
        window = new JFrame();
        window.setVisible(true);
        window.pack();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    //redraws the frame
    static void rerender() {
        image.setIcon(new ImageIcon(picture.getImage()));
        window.revalidate();
    }

    //initialization of frame
    static void InitPictureInFrame() {
        window.setSize(picture.width(), picture.height() + 25);
        image = new JLabel(new ImageIcon(picture.getImage()));
        image.setSize(picture.width(), picture.height());
        window.add(image);
    }

    //gets average color from block
    public static Color getColor(int fromX, int fromY, int toX, int toY) {
        int r = 0;
        int g = 0;
        int b = 0;
        int count = 0;
        int frY = fromY;
        while (fromX < toX) {
            fromY = frY;
            while (fromY < toY) {
                r += picture.get(Math.min(fromX, picture.width() - 1), Math.min(fromY, picture.height() - 1)).getRed();
                g += picture.get(Math.min(fromX, picture.width() - 1), Math.min(fromY, picture.height() - 1)).getGreen();
                b += picture.get(Math.min(fromX, picture.width() - 1), Math.min(fromY, picture.height() - 1)).getBlue();
                fromY++;
                count++;
            }
            fromX++;
        }
        if (count == 0)
        {
            return new Color(0, 0, 0);
        }
        else
        {
            r /= count;
            g /= count;
            b /= count;
        }

        return new Color(r, g, b);
    }

    //sets an average color to block
    public static void setColor(int fromX, int fromY, int toX, int toY, Color color) {
        int frY = fromY;
        while (fromX <= toX) {
            fromY = frY;
            while (fromY <= toY) {
                picture.set(Math.min(fromX, picture.width() - 1), Math.min(fromY, picture.height() - 1), color);
                fromY++;
            }
            fromX++;
        }
    }

    //runs single mode program
    public static void runSingleMode(String picName) {
        //init
        picture = new Picture(picName);
        createWindow();
        InitPictureInFrame();
        int imageWidth = picture.width();
        int imageHeight = picture.height();

        int y0;
        int x0 = 0;
        int rows = imageHeight / chunkSize;
        int cols = imageWidth / chunkSize;

        //goes for all cols and rows and repaint them
        for (int a = 0; a < cols + 1; a++) {
            int x1 = x0 + chunkSize;
            int y1 = chunkSize;
            y0 = 0;
            for (int b = 0; b < rows + 1; b++) {
                setColor(x0, y0, Math.min(x1, imageWidth - 1), Math.min(y1, imageHeight - 1), getColor(x0, y0, Math.min(x1, imageWidth - 1), Math.min(y1, imageHeight - 1)));
                rerender();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                y0 += chunkSize;
                y1 += chunkSize;
            }
            x0 += chunkSize;
        }
        picture.show();
    }

    //main function decides what to run
    public static void main(String[] args) {
        String imgPath = args[0];
        chunkSize = Integer.parseInt(args[1]);
        String m = args[2];

        if (m.equalsIgnoreCase("S"))
            runSingleMode(imgPath);
        else MultiThread.runInParallel(imgPath);
    }
}
