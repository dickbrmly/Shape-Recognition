package Graphics;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static Graphics.Pictures.Picture.*;
import static javafx.embed.swing.SwingFXUtils.toFXImage;

public class Main extends Application
{
  ImageView imgView;
  Image viewedImage;

  @Override
  public void init() throws IOException
  {
    img = ImageIO.read( new File("src/Graphics/Pictures/pic.gif"));
    width = img.getWidth() - 1;
    height = img.getHeight() - 1;
    WritableImage interim = new WritableImage(width,height);
    PixelWriter write = interim.getPixelWriter();
    viewedImage = toFXImage(img,interim);
    colorCount = 0;
  }

  @Override
  public void start(Stage primaryStage)
  {
    imgView = new ImageView(viewedImage);
    primaryStage.setTitle("Load Image");
    primaryStage.setResizable(true);
    StackPane sp = new StackPane();
    sp.getChildren().add(imgView);

    //Adding HBox to the scene
    Scene scene = new Scene(sp);
    primaryStage.setScene(scene);
    primaryStage.show();
    Mother mother = Mother.getInstance();
  }

  public static void main(String[] args)
  {
    launch(args);
  }
}
