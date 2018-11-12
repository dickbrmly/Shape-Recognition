package Graphics;

import Graphics.Pictures.Picture;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application
{

  @Override
  public void init() throws IOException
  {

  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    Picture picture = Picture.getInstance("pic.bmp");

    ImageView imgView = new ImageView(picture.getImage());
    primaryStage.setTitle("Load Image");
    primaryStage.setResizable(false);
    StackPane sp = new StackPane();
    sp.getChildren().add(imgView);

    //Adding HBox to the scene
    Scene scene = new Scene(sp);
    primaryStage.setScene(scene);
    primaryStage.show();
    Mother mother = new Mother();
  }

  public static void main(String[] args)
  {
    launch(args);
  }
}