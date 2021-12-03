import javax.swing.*;
import java.awt.*;

public class Main {
    private static JFrame window;
    private static JLabel image;
    private static Integer chunkSize;
    private static Picture picture;

    public static Integer getChunkSize() {
        return chunkSize;
    }

    public static Picture getPicture() {
        return picture;
    }

    public static void setPicture(Picture picture) {
        Main.picture = picture;
    }

    public static void save() {
        picture.show();
    }

    static void createWindow() {
        window = new JFrame();
        window.setVisible(true);
        window.pack();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    static void rerender() {
        image.setIcon(new ImageIcon(picture.getImage()));
        window.revalidate();
    }

    static void InitPictureInFrame() {
        window.setSize(picture.width(), picture.height() + 25);
        image = new JLabel(new ImageIcon(picture.getImage()));
        image.setSize(picture.width(), picture.height());
        window.add(image);
    }

    public static Color getColor(int fromX, int fromY, int toX, int toY) {
        int r = 0;
        int g = 0;
        int b = 0;
        int count = 0;
        int frY = fromY;
        while (fromX < toX) {
            fromY = frY;
            while (fromY < toY) {
                r += picture.get(fromX, fromY).getRed();
                g += picture.get(fromX, fromY).getGreen();
                b += picture.get(fromX, fromY).getBlue();
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

    public static void setColor(int fromX, int fromY, int toX, int toY, Color color) {
        int frY = fromY;
        while (fromX <= toX) {
            fromY = frY;
            while (fromY <= toY) {
                picture.set(fromX, fromY, color);
                fromY++;
            }
            fromX++;
        }
    }

    public static void runSingleMode(String picName) {
        picture = new Picture(picName);
        createWindow();
        InitPictureInFrame();
        int imageWidth = picture.width();
        int imageHeight = picture.height();

        int y0 = 0;
        int x0 = 0;
        int rows = imageHeight / chunkSize;
        int cols = imageWidth / chunkSize;

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

    public static void main(String[] args) {
        String imgPath = args[0];
        chunkSize = Integer.parseInt(args[1]);
        String m = args[2];

        System.out.println(imgPath + " " + chunkSize + " " + m);

        if (m.equalsIgnoreCase("S"))
            runSingleMode(imgPath);
        else MultiThread.runInParallel(imgPath);
    }
}
