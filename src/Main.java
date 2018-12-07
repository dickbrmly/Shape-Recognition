
import javafx.application.Application;
import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import Controls.Picture;
import Controls.Mother;

public class Main extends Application {

  @Override public void init() {

  }

  @Override public void start(Stage primaryStage) {

    Picture picture = Picture.getInstance("src/Images/pic.bmp");

    ImageView imgView = new ImageView(picture.getImage());
    primaryStage.setTitle("Load Image");
    primaryStage.setResizable(false);
    StackPane sp = new StackPane();
    sp.getChildren().add(imgView);

    //Adding HBox to the scene
    Scene scene = new Scene(sp);
    primaryStage.setScene(scene);
    primaryStage.show();
    Mother mother = new Mother(); //mother gives birth to shapes found within an image
  }
    public static void main (String[]args)
    {
      launch(args);
    }
}