package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NotificationImage {
    private ImageView imageView;
    public NotificationImage(NotificationType notificationType){
        Image image;
        switch (notificationType){
            case ERROR:image=new Image("error.png");
            break;
            case SUCCESS:image= new Image("success.png");
            break;
            case WARNING:image=new Image("warning.png");
            break;
            case CHECK:image=new Image("info.png");
            break;
            case CHECKMET:image=new Image("info.png");
            break;
            case INFORMATION:image=new Image("info.png");
            break;
            default:image=null;
        }
        imageView=new ImageView( image);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
    }
    public ImageView get(){
        return imageView;
    }
}
