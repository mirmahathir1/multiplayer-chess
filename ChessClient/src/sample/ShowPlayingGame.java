package sample;

import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShowPlayingGame {
    List<String> choices=new ArrayList<>();
    String gameName;


    public ShowPlayingGame(ArrayList list){
        if(list.size()==0){
            new ShowNotification("No game are playing",NotificationType.INFORMATION);

            Chess.getMe().write("WatchFinish");
            Chess.getHomePage().changeToHomePage();
            return;

        }
        else {
            choices.addAll(list);
            ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
            dialog.setTitle("All Games");
            dialog.setHeaderText("Games");
            dialog.setContentText("Choose a game");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                String string[]=result.get().split("\\+");
                Chess.getBoardPage().getMyName().setText(string[0]);
                Chess.getBoardPage().getFriendName().setText(string[1]);
                gameName=result.get();
            }
        }

    }

    public int getGameNumber(){
        return choices.indexOf(gameName);
    }

}
