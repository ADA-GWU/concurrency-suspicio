import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Picture {

String img_name;
BufferedImage buf_img;
int width;
int height;

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

public void set(int col, int row, Color color) {
    buf_img.setRGB(col, row, color.getRGB());
}

public void show() {
    try {

        File saveAs = new File( "blured.png");
        ImageIO.write(buf_img, "png", saveAs);

//        Desktop.getDesktop().open(saveAs);
    } catch (IOException ex) {
        Logger.getLogger(Picture.class.getName()).log(Level.SEVERE, null, ex);
    }
   }

 }