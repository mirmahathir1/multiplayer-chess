package sample;

import javafx.scene.image.Image;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileByte {

    public static byte[] fileToByte(String filePath) {
        Path path = Paths.get("dir/"+filePath);
        byte[] data=null;
        try {
            data = Files.readAllBytes(path);

        } catch (IOException e) {
            System.out.println("Error in converting file to byteArray");
        }finally {
            return data;
        }

    }
    public static Image byteToFile(byte[] data,String name ){

        byteToFile(name,data);

        Image image = new Image("dir/"+name);
        return image;
    }

    public static void byteToFile(String destinationFilePath, byte[] data ){
        FileOutputStream fileOuputStream=null;
        try {
            fileOuputStream = new FileOutputStream("dir/"+destinationFilePath);
            fileOuputStream.write(data);
            System.out.println("done");

        } catch (Exception e) {
            System.out.println("Error in converting byteArray to image");
        }finally
        {
            try {
                fileOuputStream.close();
            } catch (IOException e) {
                System.out.println("Error in closing file output stream");
            }
        }
    }
}