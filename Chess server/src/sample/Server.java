package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

public class Server {
    ServerGUIController serverGui;
    private Thread thread=null;
    private int gameNumber=0;
    private Game[] games=new Game[100];
    private AllImage allImage;
    private int avaiableClient=0;
    private int clientNumber=0;
    private LinkedList<Player> players=new LinkedList<>();
    private Vector<GameMove> historyGame=new Vector<>();
    
    
    public Server(ServerGUIController serverGui,int port){
        allImage=new AllImage();
        this.serverGui=serverGui;
        collectGameFromFile();
        openServerThread(port);

    }
    

    public void openServerThread(int port){

        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                System.out.println("running");
                try {

                    ServerSocket serverSocket= null;
                    serverSocket = new ServerSocket(port);

                    for (; avaiableClient < 100; avaiableClient++, clientNumber++) {
                        Socket playerSocket = serverSocket.accept();
                        players.add(new Player(playerSocket, clientNumber));
                        players.get(clientNumber).setServer(serverGui.server);
                        players.get(clientNumber).setAvaiableInNet(false);

                        System.out.println(players.size());
                        System.out.println(players.get(clientNumber));
                        for(int i=0;i<players.size();i++)
                            System.out.println(players.get(i));

                        servePlayer(players.get(clientNumber));
                    }
                } catch (IOException e) {
                    System.out.println("Already open this socket");
                   // e.printStackTrace();
                }
            }
        };
        thread=new Thread(runnable);
        thread.start();
    }

    public void servePlayer(Player player){
        System.out.println("PlayerNumber "+clientNumber);
        player.write("PlayerNumber+"+clientNumber);
        player.write(allImage);
        player.readThread();
//        WaitingPlayerInfo waitingPlayerInfo=new WaitingPlayerInfo();
//        for(int i=0;i<players.size();i++){
//            if(players.get(i).isAvaiableInNet() && i!=clientNumber){
//                waitingPlayerInfo.addPlayer(players.get(i).covertPlayer());
//            }
//        }
//        System.out.println("Player Number: "+waitingPlayerInfo.getSize());
//        if(waitingPlayerInfo.getSize()!=0){
//            player.write(waitingPlayerInfo);
//            System.out.println("Writing..");
//        }
        //player.write("Finish");

    }

//    public void serveGame(Player sender, Player receiver) {
//        sender.setFriend(receiver);
//        receiver.setFriend(sender);
//
//        while (true){
//            try {
//                if(sender.readMove(receiver)){
//                    return;
//                }
//                if(receiver.readMove(sender)){
//                    return;
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//
//

    public  void addGame(Game game){
        serverGui.addPlayerInPlayingListView(game.getMe().getName(),game.getFriend().getName());
        game.setGameNumber(gameNumber);
        games[gameNumber]=game;
        gameNumber++;
    }

    public void deleteGame(int gameNumber){
        if(gameNumber!=-1 && games[gameNumber]!=null){
            serverGui.deletePlayerInPlayingListView(games[gameNumber].getMe().getName(),games[gameNumber].getFriend().getName());
            games[gameNumber]=null;
            System.out.println("deleting +"+gameNumber+"total: "+this.gameNumber);
            this.gameNumber--;
        }

    }

    public void saveGameInFile(){

        ObjectOutputStream oos=null;
        try {

            System.out.println("Writing in file");
            oos=new ObjectOutputStream(new FileOutputStream("dir/game.txt"));
            oos.writeObject(historyGame);
            oos.flush();

        } catch (IOException e) {
           // e.printStackTrace();
        }
        finally {
            if(oos!=null){
                try {
                    oos.close();
                } catch (IOException e1) {
                    //e1.printStackTrace();
                }
            }
        }
    }

    public void collectGameFromFile(){
        ObjectInputStream ois=null;
        try {
            ois=new ObjectInputStream(new FileInputStream("dir/game.txt"));
            historyGame= (Vector<GameMove> ) ois.readObject();
            System.out.println("size: "+historyGame.size());
            ois.close();
        } catch (Exception e) {
            System.out.println("Does not open file");
            //e.printStackTrace();
        }
        finally {
            if(ois!=null){
                try {
                    ois.close();
                } catch (IOException e1) {
                    //e1.printStackTrace();
                }
            }
        }
    }

    
    public void closeAll(){
        try {
            for(int i=0;i<players.size();i++){
                if(players.get(i).getThread()!=null){
                    players.get(i).connected=false;
                    players.get(i).getThread().stop();
                }
            }
            thread.stop();

        }catch (Exception f){
            //f.printStackTrace();
            System.out.println("Stopping");
        }
    }

    class ReadProcessAndWrite {

        private Player sender;
        private Player receiver;


        public ReadProcessAndWrite(Player sender){
            this.sender=sender;
        }

        public boolean processData(Object data) {
            //move
            if (data instanceof MoveInfo) {
                MoveInfo moveInfo = (MoveInfo) data;
                System.out.println("sending " + moveInfo + " to " + sender.getFriend().getPlayerNumber());

                if (!sender.getFriend().connected) {
                    return false;
                }

                sender.getFriend().write(moveInfo);

                if (sender.getColor() == 1) {
                    games[sender.getGameNumber()].whiteMove.add(moveInfo);
                    //saveInFile(inputData[1]);
                } else {
                    games[sender.getGameNumber()].blackMove.add(moveInfo.getFriendMove());
                    //reverseMoveAndSaveInFile(inputData[1]);
                }
//                System.out.println("/////White move");
//                for(int i=0;i<games[sender.getGameNumber()].whiteMove.size();i++){
//                    System.out.println(games[sender.getGameNumber()].whiteMove.get(i));
//                }
//                System.out.println("/////BlaCk move");
//                for(int i=0;i<games[sender.getGameNumber()].blackMove.size();i++){
//                    System.out.println(games[sender.getGameNumber()].blackMove.get(i));
//                }

                for (int i = 0; i < games[sender.getGameNumber()].spectator.size(); i++) {
                    if (sender.getColor() == 1) {
                        players.get(games[sender.getGameNumber()].spectator.get(i)).write(moveInfo);

                    } else
                        players.get(games[sender.getGameNumber()].spectator.get(i)).write(moveInfo.getFriendMove());
                }

                if (moveInfo.isCheckmate()) {
                    historyGame.add(games[sender.getGameNumber()].convertToGameMove());
                    saveGameInFile();
                    if (sender.getGameNumber() != -1)
                        deleteGame(sender.getGameNumber());

                    int i = sender.getPlayerNumber();
                    int j = sender.getFriend().getPlayerNumber();
                    players.get(i).setFriend(null);
                    players.get(i).setGameNumber(-1);
                    players.get(i).setPlaying(false);
                    players.get(i).setAvaiableInNet(false);

                    i = j;

                    players.get(i).setFriend(null);
                    players.get(i).setGameNumber(-1);
                    players.get(i).setPlaying(false);
                    players.get(i).setAvaiableInNet(false);
                    return false;
                }

            }
            //for board page
            else if (data instanceof String) {
                return processAndWriteString(((String) data));
            }
            //for create game
            else if (data instanceof PlayerInfo) {
//            System.out.println("getting player info");
//            PlayerInfo player=(PlayerInfo) data;
//            sender.setName(player.getName());
//            sender.setColor(player.getColor());
//            sender.setPlaying(false);
//            sender.setAvaiableInNet(true);
//            System.out.println(sender.getName());
//
//            serverGui.addPlayerInListView(player.getName());
            }
            //for join game
            else if (data instanceof GameInfo) {
                System.out.println("YESYES");
                GameInfo game = (GameInfo) data;
                sender.setColor(game.getMe().getColor());

                findAndSaveReceiver(game);

                sender.setGameNumber(gameNumber);
                receiver.setGameNumber(gameNumber);
                sender.setAvaiableInNet(false);
                receiver.setAvaiableInNet(false);
                sender.setPlaying(true);
                receiver.setPlaying(true);
                sender.waiting = false;
                receiver.waiting = false;

                System.out.println(game);
                receiver.write(new GameInfo(sender.covertPlayer(), receiver.covertPlayer()));
                //receiver.write("Finish");

                serverGui.deletePlayerInListView(receiver.getName());
                sender.setFriend(receiver);
                receiver.setFriend(sender);
                addGame(new Game(sender, receiver));
//                if(sender.getColor()==1){
//                    serveGame(sender,receiver );
//                }
//                else
//                    serveGame(receiver,sender);
//                try{
//                    if(sender.getThread()!=null){
//                        sender.getThread().stop();
//                        sender.setThread(null);
//                    }
//                    if(receiver.getThread()!=null){
//                        receiver.getThread().stop();
//                        sender.setThread(null);
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                    return false;
//                }
            }
            return true;
        }

        public boolean processAndWriteString(String data){
            System.out.println("Getting data from client "+data+" "+sender.getPlayerNumber());
            String inputData[]=data.split("\\+");
            switch (inputData[0]){
                case "Undo":
                    games[sender.getGameNumber()].whiteMove.removeLast();
                    games[sender.getGameNumber()].blackMove.removeLast();
                    sender.getFriend().write(data);
                    for(int i=0;i<games[sender.getGameNumber()].spectator.size();i++){
                        //if(sender.getColor()==1){
                            players.get(games[sender.getGameNumber()].spectator.get(i)).write("Undo");
//                        }
//                        else
//                            players.get(games[sender.getGameNumber()].spectator.get(i)).write(moveInfo.getFriendMove());
                    }
                    break;
                case "Game":
                    int gameNumber=Integer.parseInt(inputData[1]);
                    System.out.println("Sending all move ");
                    games[gameNumber].spectator.add(sender.getPlayerNumber());
                    System.out.println(games[gameNumber].whiteMove);
                    System.out.println(games[gameNumber].blackMove);
                    sender.write(games[gameNumber].whiteMove);
                    sender.write(games[gameNumber].blackMove);
                    break;
                case "HistoryGame":
                    int historyGameNumber=Integer.parseInt(inputData[1]);
                    System.out.println("Sending all move ");

                    if(historyGameNumber!=-1){

                        sender.write(historyGame.get(historyGameNumber).whiteMove);
                        sender.write(historyGame.get(historyGameNumber).blackMove);
                    }
                   // sender.write("Finish");
                    break;
                case "JustName":
                    System.out.println("getting name");
                    // PlayerInfo player=(PlayerInfo) data;
                    sender.setName(inputData[1]);
                    System.out.println(sender.getName());
                    sender.setName(inputData[1]);
                    System.out.println(sender.getName());
                    if(inputData.length>2){
                        if(inputData[2].equals("Spectator")){
                            System.out.println("Sending currentlyPlayingArrayList +size "+serverGui.currentlyPlayingArrayList.size());
//                            games[games.length-1].spectator.add(sender.getPlayerNumber());

                            sender.write(serverGui.currentlyPlayingArrayList);

                        }
                        if(inputData[2].equals("History")){
                            System.out.println("Sending history +size "+historyGame.size());

                            ArrayList<String> list=new ArrayList<>();
                            for(int i=0;i<historyGame.size();i++){
                                list.add(historyGame.get(i).getWhiteName()+"+"+historyGame.get(i).getBlackName());
                            }
                            sender.write(list);
                        }
                    }
                    System.out.println("getting name");
                    // PlayerInfo player=(PlayerInfo) data;

                    return false;
                case "Name":
                    System.out.println("getting player info");
                    // PlayerInfo player=(PlayerInfo) data;
                    sender.setName(inputData[1]);
                    sender.setColor(Integer.parseInt(inputData[2]));
                    sender.setPlaying(false);
                    sender.setAvaiableInNet(true);
                    sender.waiting=true;
                    System.out.println(sender.getName());
                    for(int i=0;i<players.size();i++){
                        System.out.println(players.get(i));
                    }
                    serverGui.addPlayerInListView(sender.getName());

                    return false;

                case "MenuPageData":
                    System.out.println("Wring data to "+ sender.getPlayerNumber());
                    WaitingPlayerInfo player1=new WaitingPlayerInfo();
                    for(int i=0;i<players.size();i++){
                        if(players.get(i).waiting && i!=clientNumber)
                            player1.addPlayer(players.get(i).covertPlayer());
                    }
                    sender.write(player1);
                    System.out.println(player1);
                    //sender.write("Finish");
                    break;

                case "QuiteGame":
                    historyGame.add(games[sender.getGameNumber()].convertToGameMove());
                    saveGameInFile();

                    if(sender.getFriend()!=null){
                        System.out.println("Yes");
                        sender.getFriend().write(data);
                        sender.write("Finish");
                        //sender.write("Finish");
                    }
                    if(sender.getGameNumber()!=-1)
                        deleteGame(sender.getGameNumber());
                    sender.getFriend().initializePlayer();
                    sender.initializePlayer();
                    return false;
                case "EndGame":


                    if(sender.getFriend()!=null){
                        System.out.println("Yes");
                        sender.getFriend().write(data);

                    }
                    if(sender.getGameNumber()!=-1)
                        deleteGame(sender.getGameNumber());
                    if(sender.getFriend()!=null)
                    sender.getFriend().initializePlayer();
                    sender.initializePlayer();
                    return false;
                case "WatchFinish":
                    System.out.println("Write finish to spectator");

                    try {
                        for (int i=0;i<games.length;i++){
                            if(games[i].spectator.contains(sender.getPlayerNumber())){
                                games[i].spectator.remove((Integer) sender.getPlayerNumber());
                                break;
                            }
                        }
                    }catch (Exception e){ }

                    sender.write("Finish");
                    //sender.write("Finish");
                    sender.initializePlayer();
                    return false;
            }
//            if(inputData.length>2){
//                if(inputData[2].equalsIgnoreCase("checkmet")){
//                    sender=new Player(sender.getSocket(),sender.getName(), sender.getOutputStream(),sender.getInputStream(),false,sender.getPlayerNumber());
//                    receiver=sender.getFriend();
//                    receiver=new Player(sender.getSocket(),receiver.getName(), receiver.getOutputStream(),receiver.getInputStream(),false,receiver.getPlayerNumber());
//                }
//            }
            return true;
        }

        public void findAndSaveReceiver(GameInfo gameInfo){
            System.out.println(gameInfo);

            int i=gameInfo.getFriend().getPlayerNumber();

            if(players.get(i).isAvaiableInNet()){
                receiver=new Player();
                receiver.setPlayerNumber(i);
                receiver.setInputStream(players.get(i).getInputStream());
                receiver.setOutputStream(players.get(i).getOutputStream());
                receiver=players.get(i);
                receiver.setFriend(sender);
                receiver.setColor(sender.getColor()^1);
                return;
            }
            System.out.println("Not finding ");
        }
    }
}
