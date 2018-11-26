package utilities;

import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class PictureFiler {

  String type;

  public PictureFiler(String type) {
    this.type = type;
  }


  public void file(WritableImage image, int index)  {
      File file = new File("images/image " + index + "." + type);
      BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
      try { ImageIO.write((RenderedImage) bImage, type, file); }
        catch (IOException e) { System.out.println(e); }
  }
}
