package Graphics;

import Graphics.Pictures.Picture;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application
{
  ImageView imgView;
  Picture picture = Picture.getInstance();

  @Override
  public void init()
  {
    picture.img = new Image("file:src/Graphics/Pictures/pic.gif");
    picture.width = (int) picture.img.getWidth() - 1;
    picture.height = (int) picture.img.getHeight() - 1;
    picture.reader = picture.img.getPixelReader();
    picture.colorCount = 0;
  }

  @Override
  public void start(Stage primaryStage)
  {
    imgView = new ImageView(picture.img);
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
