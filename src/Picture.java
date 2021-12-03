import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

//additional class with image processing functions
public class Picture {

    private String img_name;
    private BufferedImage buf_img;
    private int width;
    private int height;

    //constuctors
    public Picture(String name) {
        this.img_name = name;

        try {
            buf_img = ImageIO.read(new File(img_name));
        } catch (IOException ex) {
            Logger.getLogger(Picture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Picture(int w, int h) {
        this.width = w;
        this.height = h;
        buf_img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    //getters
    public int width() {
        width = buf_img.getWidth();
        return width;
    }

    public int height() {
        height = buf_img.getHeight();
        return height;
    }

    public Color get(int col, int row) {
        Color color = new Color(buf_img.getRGB(col, row));
        return color;
    }

    public BufferedImage getImage() {
        return buf_img;
    }

    //updates color at position
    public void set(int col, int row, Color color) {
        buf_img.setRGB(col, row, color.getRGB());
    }

    //saves image
    public void show() {
        try {
            File saveAs = new File("blured.png");
            ImageIO.write(buf_img, "png", saveAs);
        } catch (IOException ex) {
            Logger.getLogger(Picture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}