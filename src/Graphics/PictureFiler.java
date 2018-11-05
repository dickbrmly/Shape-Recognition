package Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PictureFiler {

        String type;


    PictureFiler(String type){
        this.type = type;
    }
        FileOutputStream record = null;
        File file;

        public void file(BufferedImage item, int index) {

            try {
                file = new File("images/Image " + index + "."+ type);
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
