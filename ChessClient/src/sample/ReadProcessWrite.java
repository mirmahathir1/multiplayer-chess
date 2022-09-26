package sample;

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.LinkedList;

public class ReadProcessWrite {
    Player player;
    public ReadProcessWrite(Player player){
        this.player=player;
    }

    synchronized public boolean read(){
        //Runnable runnable= () -> {
        while (player.connected){
            System.out.println("Reading");
            if(Chess.inHomePage){
                System.out.println("reading finishjhbj");
                return false;
            }
            try {
                if(process(player.getInputStream().readObject())==false){
                    System.out.println("reading finish");
                    return false;
                }
                else{
                    if(Chess.inHomePage){
                        System.out.println("reading finishjhbj");
                        return false;
                    }
                }
            } catch (Exception e) {
               e.printStackTrace();
                return false;
            }
        }
        return false;
//        };
//
//        Thread t=new Thread(runnable);
//        t.start();
    }


    public void readThread(){
        Runnable runnable= () -> {
             read();
        };

        player.thread=new Thread(runnable);
        player.thread.start();
    }

    public boolean process(Object o){
        System.out.println("Get object "+o);

        if(o instanceof LinkedList){
            System.out.println("in LinkedList");

            LinkedList linkedList=(LinkedList) o;
            if(Chess.myMove.size()==0){
                Chess.myMove.addAll(linkedList);
                System.out.println("Size my move: "+Chess.myMove.size());
                //Chess.getBoardPage().getWhiteMove=true;
            }
            else {
                Chess.friendMove.addAll(linkedList);
                System.out.println("Size friend move: "+Chess.friendMove.size());

                if(Chess.myMove.size()>Chess.friendMove.size()){
                    Chess.getBoardPage().whiteMove=false;

                }
                else Chess.getBoardPage().whiteMove=true;

                if(Chess.getBoardPage().spectator) {
                    Platform.runLater(()->{
                        Chess.getBoardPage().showAllMove();
                    });

                }

                    return false;
            }
        }

        else if(o instanceof ArrayList){
            System.out.println("in window changing");

            ArrayList game=(ArrayList) o;
            System.out.println(game.size()+"  "+game);
            Platform.runLater(()->{
                Chess.getWindow().setScene(Chess.getBoardPage().getScene());
                Chess.getWindow().show();
                ShowPlayingGame showPlayingGame=new ShowPlayingGame(game);
                if(Chess.getBoardPage().spectator){
                    Chess.getMe().write("Game+"+showPlayingGame.getGameNumber());
                }
                else
                    Chess.getMe().write("HistoryGame+"+showPlayingGame.getGameNumber());


            });
            return false;
        }

        else if(o instanceof MoveInfo){
            MoveInfo moveInfo=(MoveInfo) o;
            System.out.println("moving.");
            if(Chess.getBoardPage().spectator){

                System.out.println("in spectator");
                if(Chess.getBoardPage().whiteMove){
                    System.out.println("in whit");
                    Chess.myMove.add(moveInfo);

                    Chess.getBoardPage().performAllMoveS(moveInfo);
                    Chess.getBoardPage().whiteMove=false;
                }
                else {
                    System.out.println("in black");
                    Chess.friendMove.add(moveInfo);
                    Chess.getBoardPage().whiteMove=true;
                    Chess.getBoardPage().performAllMoveS(moveInfo);

                }
            }
            else {
               // Platform.runLater(()->{
                Chess.getBoardPage().yourTurn.setVisible(true);
                Chess.getBoardPage().friendTurn.setVisible(false);
                Chess.getBoardPage().resignButton.setVisible(true);
                System.out.println("Moving....");

                Chess.getBoardPage().performMoveFriend(moveInfo);

                if(Chess.myMove.size()>=1){
                    Chess.getBoardPage().undoButton.setVisible(true);
                }

                for(Box temp:Chess.getFriendHashMap().values()){
                    temp.getPiece().saveValidElimination(Chess.getBoardPage().getBox(),temp);
                }

                Chess.getBoardPage().setMyTurn(true);
                if(moveInfo.isCheck()){
                    System.out.println("You are checked ");
                    new ShowNotification("You are checked",NotificationType.CHECK);
                }
                if(moveInfo.isCheckmate()){

                    Chess.getBoardPage().backToHomepage.setVisible(true);
                    //Chess.getBoardPage().backToMenupage.setVisible(true);
                    System.out.println("CKECHMET");
                    new ShowNotification("CHECKMATE",NotificationType.CHECKMET);
                    new ShowNotification("You loss",NotificationType.ERROR);

                    Chess.getBoardPage().spectator=true;
                    Chess.getBoardPage().yourTurn.setVisible(false);
                    Chess.getBoardPage().friendTurn.setVisible(false);
                    Chess.getBoardPage().undoButton.setVisible(false);
                    Chess.getBoardPage().resignButton.setVisible(false);

                   // Chess.getMenuPage().changeToMenuPage();
                    //return false;
                }
            }

        }

        else if(o instanceof AllImage){
            Piece.setAllImage((AllImage) o);
            return false;
        }
        else if(o instanceof String ){
            return processString((String) o);
        }


        if(o instanceof WaitingPlayerInfo){
            System.out.println("Yes");
            Chess.setPlayers((WaitingPlayerInfo) o);
            return false;
        }

        else if(o instanceof GameInfo){
            System.out.println("YESYES");
            GameInfo gameInfo=(GameInfo)o;
            Chess.getMe().updateFromPlayerInfo(gameInfo.getFriend());
            if(Chess.getMe().getFriend()==null)
                Chess.getMe().setFriend(new Player());
            Chess.getMe().getFriend().updateFromPlayerInfo(gameInfo.getMe());
            Platform.runLater(()->{
                Chess.setBoardPage(new BoardPage(Chess.getMe().getColor()));
                Chess.getBoardPage().getMyName().setText(Chess.getName());
                Chess.getBoardPage().getFriendName().setText(Chess.getMe().getFriend().getName());
                Chess.getWindow().setScene(Chess.getBoardPage().getScene());
                Chess.getWindow().show();
            });

        }


        return true;
    }
    public boolean processString(String inputData){
        System.out.println("Input data from server is ->"+inputData);

        String identifier[]=inputData.split("\\+");

        switch (identifier[0]){
            case "Undo":
                Chess.getBoardPage().undoMoveFriend(false);
                //new ShowNotification("Your friend move Undo",NotificationType.INFORMATION);

                Chess.getBoardPage().undoMoveFriend(true);
                new ShowNotification("Move Undo",NotificationType.INFORMATION);
                if(Chess.myMove.size()<1){
                    Chess.getBoardPage().undoButton.setVisible(false);
                }
                break;
            case "PlayerNumber": Chess.getMe().setPlayerNumber(Integer.parseInt(identifier[1]));
                System.out.println("clientNumber " +Chess.getMe().getPlayerNumber());
                break;
            case "QuiteGame":
                new ShowNotification("You win.Your friend accept defeat.",NotificationType.SUCCESS);

                Chess.getBoardPage().backToHomepage.setVisible(true);
                //Chess.getBoardPage().backToMenupage.setVisible(true);

                //Chess.getBoardPage().spectator=true;
                Chess.getBoardPage().yourTurn.setVisible(false);
                Chess.getBoardPage().friendTurn.setVisible(false);
                Chess.getBoardPage().undoButton.setVisible(false);

                return false;

            case "EndGame":

                new ShowNotification("You win.Your friend is offline",NotificationType.SUCCESS);
                Chess.getBoardPage().backToHomepage.setVisible(true);
               // Chess.getBoardPage().backToMenupage.setVisible(true);
                Chess.getBoardPage().spectator=true;
                Chess.getBoardPage().yourTurn.setVisible(false);
                Chess.getBoardPage().friendTurn.setVisible(false);
                Chess.getBoardPage().undoButton.setVisible(false);

                Chess.getMenuPage().changeToMenuPage();
                return false;

            case "Finish":Chess.inHomePage=false;
                return false;
        }
        return true;
    }
}
