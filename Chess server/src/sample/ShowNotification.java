package sample;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class ShowNotification {
    private Notifications notificationBuilder;

    public ShowNotification(String text,NotificationType type){

        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                Platform.runLater(()->{
                    try {
                        Node graphic = new NotificationImage(type).get();
                        notification(Pos.BOTTOM_RIGHT, graphic, text);
                        notificationBuilder.show();
                    }
                    catch (Exception e){
                        System.out.println("Exception in Showing notification");
                    }
                });
            }
        };
        Thread t=new Thread(runnable);
        t.start();
    }

    private void notification(Pos pos, Node graphic, String Text)
    {
        notificationBuilder = Notifications.create()
                .title("Notification")
                .text(Text)
                .graphic(graphic)
                .hideAfter(Duration.seconds(5))
                .position(pos)
                .onAction(arg0 -> System.out.println("Notifiation is Clicked"));
    }

}
