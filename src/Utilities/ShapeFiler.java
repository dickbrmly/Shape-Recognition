package Utilities;

import Objects.Shape;

import java.io.*;

public class ShapeFiler {

    FileOutputStream record = null;
    File file;
    ObjectOutputStream itemObject;

    public void file(Shape.ShapeData item, int index) {

            int whole = (int) item.distribution;
            int fraction = (int) (item.distribution - whole) * 100;

        try {
            file = new File("objects/object " + index + ".data");
            record = new FileOutputStream(file);
            if(!file.exists()) file.createNewFile();
            itemObject = new ObjectOutputStream(record);
            record.write(whole);
            record.write(fraction);
            record.write(item.color.getRed());
            record.write(item.color.getGreen());
            record.write(item.color.getBlue());
            record.write(item.color.getAlpha());
            itemObject.writeObject(item.edge);
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
