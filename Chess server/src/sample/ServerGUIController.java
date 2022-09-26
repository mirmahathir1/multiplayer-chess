package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ServerGUIController extends Application {

    Server server=null;
    Stage stage;
    private Parent root = null;
    public ListView playerList;
    public TextField portTextField;
    public ToggleButton serverButton;
    public Label statusLabel;
    public ListView currentlyPlayingList;


    ArrayList<String>playerArrayList=new ArrayList<>();
    ObservableList playerObservableList;

    ArrayList<String>currentlyPlayingArrayList = new ArrayList<>();
    ObservableList currentlyPlayingObservableList;

    public void portTextField(ActionEvent actionEvent) {

    }

    public void serverShutDown() {

    }

    public void serverStartUp() {
        server=new Server(this,Integer.parseInt(portTextField.getText()));


    }

    public void serverButtonAction(ActionEvent actionEvent) {
        if(serverButton.isSelected()) {
            statusLabel.setText("On");
            serverButton.setVisible(false);
            serverStartUp();
        }
        else if(!serverButton.isSelected()) {
            statusLabel.setText("Off");
            serverShutDown();
        }
    }



    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage){

        stage=primaryStage;
        try {
            root = FXMLLoader.load(getClass().getResource("ServerGUI.fxml"));
        } catch (IOException e) {
            //e.printStackTrace();
        }
        primaryStage.setTitle("Chess Server");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


       // portTextField.setText("55555");
    }

    public void addPlayerInListView(String name){
        playerArrayList.add(name);
        updatePlayerList();
    }

    public void deletePlayerInListView(String name){
        String temp[]=name.split("\\+");

        playerArrayList.remove(temp[0]);
        updatePlayerList();
    }
    public void addPlayerInPlayingListView(String name1,String name2){

        currentlyPlayingArrayList.add(name1+"+"+name2);
        updatePlayerList();
    }
    public void deletePlayerInPlayingListView(String name1,String name2){

        System.out.println(name1+"+"+name2);
        System.out.println(currentlyPlayingArrayList.remove(name1+"+"+name2));
        if(currentlyPlayingArrayList.size()>0)
        System.out.println(currentlyPlayingArrayList.remove(name2+"+"+name1));
        updatePlayerList();
    }

    public void updatePlayerList() {
        Platform.runLater(() -> {
            System.out.println("Playerlist " + playerArrayList.size());
            playerObservableList = FXCollections.observableList(playerArrayList);
            //playerList = new ListView();
            playerList.setItems(playerObservableList);

            System.out.println("Current " + currentlyPlayingArrayList.size());
            currentlyPlayingObservableList = FXCollections.observableList(currentlyPlayingArrayList);
            //currentlyPlayingList = new ListView();
            currentlyPlayingList.setItems(currentlyPlayingObservableList);
            //stage.show();
        });
    }
//    //public void endGame(Player player){
//        players[player.getPlayerNumber()]=null;
//    }
}
