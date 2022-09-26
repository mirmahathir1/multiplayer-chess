package sample;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

class MenuPage extends Page
{
    Button refreshButton;
    ImageView createImage;
    ImageView joinImage;
    HBox hbox;

    Button startGame;
    RadioButton createWhite, createBlack;
    ToggleGroup colorSelect;

    ArrayList<String> nameOfClientAvailable;


    ObservableList<String>activePlayers;
    ListView<String> playerList;
    MultipleSelectionModel<String>playerSelectionModel;
    Label gameJoin;

    public MenuPage()
    {
        super("125.jpg");
        refreshButton=new Button("Refresh");

        hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);

        createImage= new ImageView(new Image("createGame.png"));
        joinImage = new ImageView(new Image("joinGame.png"));

        nameOfClientAvailable = new ArrayList<>();
        int size=Chess.getPlayers().size();

        for (int i=0;i<size;i++){
            System.out.println(Chess.getPlayers().getPlayer(i).getName());
            if(Chess.getPlayers().getPlayer(i).getColor()==1)
                nameOfClientAvailable.add(Chess.getPlayers().getPlayer(i).getName()+"+WHITE");
            else
                nameOfClientAvailable.add(Chess.getPlayers().getPlayer(i).getName()+"+BLACK");
        }

        activePlayers = FXCollections.observableArrayList(nameOfClientAvailable);
        playerList = new ListView<String>(activePlayers);
        playerList.setPrefSize(100,100);
        gridpane.add(playerList,1,4,1,1);
        playerSelectionModel = playerList.getSelectionModel();

        CreateRadioButtons:{
            createWhite = new RadioButton("White");
            createBlack = new RadioButton("Black");
            startGame = new Button("Start Game");
            colorSelect= new ToggleGroup();
            createWhite.setToggleGroup(colorSelect);
            createBlack.setToggleGroup(colorSelect);
        }

        startGame.setPrefSize(300,50);
        Label gameCreate= new Label("Create Game");
        gameJoin = new Label("Available players");

        gridpane.add(createImage,0,1,1,1);
        gridpane.add(createWhite,0,2,1,1);
        gridpane.add(createBlack,0,3,1,1);
        gridpane.add(joinImage,1,1,1,1);
        gridpane.add(gameJoin,1,3,1,1);
        gridpane.add(refreshButton,1,5,1,1);

        gridpane.setHgap(100);
        gridpane.setVgap(10);

        hbox.getChildren().add(startGame);


        startGame.setTranslateY(-15);
        borderpane.setBottom(hbox);

        menuPageActions();
    }

    public void menuPageActions() {
        final GameInfo[] game = new GameInfo[1];

        refreshButton.setOnAction( e->{
            refreshButton();
        });

        colorSelect.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> changed, Toggle oldVal, Toggle newVal)-> {
            Chess.setJoinGame(false);
            if(createWhite.isSelected()){
                Chess.getMe().setColor(1);
            }
            else if(createBlack.isSelected()){
                Chess.getMe().setColor(0);
            }
        });

        playerSelectionModel.selectedItemProperty().addListener((ObservableValue<? extends String> changed, String oldVal, String newVal)-> {
            Chess.setJoinGame(true);
            PlayerInfo playerInfo = findAndStoreFriendInfo(newVal);
            System.out.println(playerInfo);
            if(playerInfo!=null){
                Chess.getMe().setName(Chess.getName());
                Chess.getMe().setColor(playerInfo.getColor()^1);
                Chess.getMe().setFriend(playerInfo);
                game[0] =new GameInfo(Chess.getMe().covertPlayer(),playerInfo);
            }

        });

        startGame.setOnAction(e-> {
            System.out.println("In start game");

            if(game[0]!=null || Chess.getMe().getColor()!=-1){
                //Chess.getMe().connected=false;
                Chess.getMe().readThread();

                if(Chess.isJoinGame()){
                    System.out.println("In join game");
                    Chess.setBoardPage(new BoardPage(Chess.getMe().getColor()));
                    Chess.getBoardPage().getFriendName().setText(Chess.getMe().getFriend().getName());
                    Chess.getBoardPage().getMyName().setText(Chess.getMe().getName());
                    if(Chess.getMe().getColor()==1){
                        for(Box temp:Chess.getFriendHashMap().values()){
                            temp.getPiece().saveValidElimination(Chess.getBoardPage().getBox(),temp);
                        }
                    }
                    System.out.println("My color: "+Chess.getMe().getColor());
                    Platform.runLater(()->{
                        Chess.getWindow().setScene(Chess.getBoardPage().getScene());
                        Chess.getWindow().show();
                    });
                    Chess.getMe().write(game[0]);
                    System.out.println(game[0]);


                }
                else {
                    System.out.println("changing to board page");
                    Platform.runLater(()->{
                        Chess.setBoardPage(new BoardPage());
                        Chess.getWindow().setScene(Chess.getBoardPage().getScene());
                        Chess.getWindow().show();
                    });

                    Chess.getMe().write("Name+"+Chess.getName()+"+"+Chess.getMe().getColor());
                    System.out.println("Writing server: "+"Name+"+Chess.getName()+"+"+Chess.getMe().getColor());
                }
            }
        });
    }

    public PlayerInfo findAndStoreFriendInfo(String name){
        System.out.println("in find game");
        for(int i=0;i<nameOfClientAvailable.size();i++){

            if( name.equals(nameOfClientAvailable.get(i))){
                System.out.println("Find player.");

                System.out.println(Chess.getPlayers().getPlayer(i));
                Chess.setFriendIndex(Chess.getPlayers().getPlayer(i).getPlayerNumber());

                return Chess.getPlayers().getPlayer(i);

            }
        }
        return null;
    }

    public void refreshButton(){
        System.out.println("Refresh button pressed");
        Chess.getMe().write("MenuPageData");

            Chess.getMe().read();

        Platform.runLater(()->{
            Chess.setMenuPage(new MenuPage());
            System.out.println("going back");
            Chess.getWindow().setScene(Chess.getMenuPage().getScene());
            Chess.getWindow().show();
        });
    }

    public void changeToMenuPage(){
        Chess.inHomePage=false;
        new ShowNotification("Go to the MenuPage",NotificationType.INFORMATION);
        refreshButton();
    }

}