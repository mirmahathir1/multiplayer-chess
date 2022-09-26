package sample;

import javafx.application.Application;
import javafx.stage.Stage;

import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;

public class Chess extends Application {


    public static volatile String IP,port;

    public static volatile LinkedList<MoveInfo> myMove;
    public static volatile LinkedList<MoveInfo> friendMove;
    private static volatile  String name;
    private static volatile  WaitingPlayerInfo players;
    private static volatile  Player me=new Player();
    private static volatile  boolean joinGame;
    private static volatile  int friendIndex;
    private static volatile  HomePage  homePage;
    private static volatile  MenuPage menuPage;
    private static volatile  BoardPage boardPage;
    private static volatile  Stage window;
    private static volatile  HashMap<Integer,Box> myHashMap;
    private static volatile  HashMap<Integer,Box> friendHashMap;
    static volatile boolean inHomePage;


    public static void initialize(){
        myMove=new LinkedList<>();
        friendMove=new LinkedList<>();
        players=new WaitingPlayerInfo();
        joinGame=false;
        friendIndex=-1;
        myHashMap=new HashMap<>();
        friendHashMap=new HashMap<>();
        menuPage=new MenuPage();
        boardPage = new BoardPage();
        inHomePage=false;
        me.initializePlayer();

    }


    public static void main(String[]args) {
        launch(args);
    }

    public void init() {
        homePage = new HomePage();
        initialize();
    }

    public void start(Stage stage){
//        this.window=window;
//        System.out.println("Chess ON");
//        window.setTitle("CHESS");
//        Parent root=null;
//        try {
//            root= FXMLLoader.load(getClass().getResource("NetworkScreen.fxml"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Scene myScene= new Scene(root,800,600);
//        window.setScene(myScene);
//        window.show();
//
//        window.setScene(networkPage.getScene());
        window=stage;
        window.setResizable(false);
        homePage.homePageActions();
        menuPage.menuPageActions();

        window.setTitle("CHESS");
        window.setScene(homePage.getScene());
        window.show();

        window.setOnCloseRequest(e->{
            try {
                me.write("EndGame");
                me.connected=false;
                me.thread.stop();
            }catch (Exception f){
                System.out.println("");
            }
        });

    }

    public static boolean connectServer(){

        IP=new Network("IP","Server IP Address","Please input Server IP Address","localhost").getInput();
        port=new Network("Port","Server Port","Please input Server Port","55555").getInput();


         Socket socket = null;

        try {
            socket = new Socket(IP,Integer.parseInt(port));
            Chess.setMe(new Player(socket));

            System.out.println("Server connected");
            return true;
//                if(loading!=null){
//                    loading.close();
//                }

        } catch (Exception e) {
            new ShowNotification("Can not connect to Server.",NotificationType.INFORMATION);
            return false;
//                if(loading==null)
//                    loading=new Loading(new ActionEvent());
            //e.printStackTrace();
            //window.close();
        }

    }



    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Chess.name = name;
    }

    public static WaitingPlayerInfo getPlayers() {
        return players;
    }

    public static void setPlayers(WaitingPlayerInfo players) {

        Chess.players = players;
//        Chess.setMenuPage(new MenuPage());

//        for(int i=0;i<players.size();i++){
//            System.out.println("working");
//            if(players.getPlayer(i).getColor()==1)
//                menuPage.nameOfClientAvailable.add(players.getPlayer(i).getName()+" /WHITE/");
//            else
//                menuPage.nameOfClientAvailable.add(players.getPlayer(i).getName()+" /BLACK/");
//
//        }
//
//        //if(menuPage.nameOfClientAvailable.size()>0){
//        Platform.runLater(()->{
//            menuPage.activePlayers= FXCollections.observableArrayList(menuPage.nameOfClientAvailable);
//            menuPage.playerList.setItems(menuPage.activePlayers);
//            menuPage.menuPageActions();
//        });
//
//        //}
    }


    public static Player getMe() {
        return me;
    }

    public static void setMe(Player me) {
        Chess.me = me;
    }

    public static boolean isJoinGame() {
        return joinGame;
    }

    public static void setJoinGame(boolean joinGame) {
        Chess.joinGame = joinGame;
    }

    public static int getFriendIndex() {
        return friendIndex;
    }

    public static void setFriendIndex(int friendIndex) {
        Chess.friendIndex = friendIndex;
    }

    public static HomePage getHomePage() {
        return homePage;
    }

    public static void setHomePage(HomePage homePage) {
        Chess.homePage = homePage;
    }

    public static MenuPage getMenuPage() {
        return menuPage;
    }

    public static void setMenuPage(MenuPage menuPage) {
        Chess.menuPage = menuPage;
    }

    public static BoardPage getBoardPage() {
        return boardPage;
    }

    public static void setBoardPage(BoardPage boardPage) {
        Chess.boardPage = boardPage;
    }

    public static Stage getWindow() {
        return window;
    }

    public static void setWindow(Stage window) {
        Chess.window = window;
    }

    public static HashMap<Integer, Box> getMyHashMap() {
        return myHashMap;
    }

    public static void setMyHashMap(HashMap<Integer, Box> myHashMap) {
        Chess.myHashMap = myHashMap;
    }

    public static HashMap<Integer, Box> getFriendHashMap() {
        return friendHashMap;
    }

    public static void setFriendHashMap(HashMap<Integer, Box> friendHashMap) {
        Chess.friendHashMap = friendHashMap;
    }
}
