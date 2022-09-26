package sample;

import java.util.LinkedList;

public class Game  {

    LinkedList<MoveInfo> whiteMove =new LinkedList<>();
    LinkedList<MoveInfo> blackMove =new LinkedList<>();

    LinkedList<Integer> spectator=new LinkedList<>();

    private Player me;
    private Player friend;
    private int gameNumber;

    public Game(Player me, Player friend) {
        this.me = me;
        this.friend = friend;
    }

    public Player getMe() {
        return me;
    }

    public void setMe(Player me) {
        this.me = me;
    }

    public Player getFriend() {
        return friend;
    }

    public void setFriend(Player friend) {
        this.friend = friend;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }

    public GameInfo convertGame(){
        return new GameInfo(me.covertPlayer(),friend.covertPlayer());
    }

    public GameMove convertToGameMove(){
        GameMove gameMove=new GameMove();
        gameMove.blackMove=blackMove;
        gameMove.whiteMove=whiteMove;
        if(me.getColor()==1){
            gameMove.setWhiteName(me.getName());
            gameMove.setBlackName(friend.getName());
        }
        else {
            gameMove.setWhiteName(friend.getName());
            gameMove.setBlackName(me.getName());
        }
        return gameMove;

    }

}
