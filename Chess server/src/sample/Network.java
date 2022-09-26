package sample;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class Network {
    String input=null;
    public Network(String title,String headerText,String cotentText,String defaultValue){
        while (true){
            TextInputDialog dialog = new TextInputDialog(defaultValue);
            dialog.setTitle(title);
            dialog.setHeaderText(headerText);
            dialog.setContentText(cotentText);

            // Traditional way to get the response value.
            Optional<String> response = dialog.showAndWait();
            if (response.isPresent()){
                input=response.get();
                System.out.println("Your name: " + response.get());
            }
            if(input==null){
                new ShowNotification("Please enter "+headerText,NotificationType.ERROR);
            }
            else if(title.equalsIgnoreCase("port")&& !portCheck()){
                new ShowNotification("Please enter an integer as server port.",NotificationType.ERROR);
            }
            else {
                break;
            }
        }
    }

    public String getInput(){
        return input;
    }

    public boolean portCheck(){
        try {
            Integer.parseInt(input);
            return true;
        }catch (Exception e){
            return false;
        }
    }


}
