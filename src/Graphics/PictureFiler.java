package Graphics;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class PictureFiler {

        String type;

    PictureFiler(String type){
        this.type = type;
    }
        FileOutputStream record = null;
        File file;

        public void file(BufferedImage item, int index) {

            try {
                file = new File("Image " + index + "."+ type);
                if(!file.exists()) file.createNewFile();
                ImageIO.write(item, "jpg", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try{
                    if(record != null) record.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
        }
    }
