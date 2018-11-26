package utilities;

import Objects.Shape;

import java.io.*;

public class ShapeFiler {

    FileOutputStream record = null;
    File file;
    ObjectOutputStream itemObject;

    public void file(Shape item, int index) {

        try {
            file = new File("objects/object " + index + ".data");
            record = new FileOutputStream(file);
            if(!file.exists()) file.createNewFile();
            itemObject = new ObjectOutputStream(record);
            itemObject.writeObject(item);
            itemObject.flush();
            itemObject.close();
            record.flush();
            record.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        record.flush();
        record.close();
        itemObject.flush();
        itemObject.close();
    }
}