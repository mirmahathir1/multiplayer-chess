package sample;

import java.io.Serializable;

public class WaitingPlayer implements Serializable{
    private Player[] players;
    int size=0;

    public WaitingPlayer(){
        players=new Player[50];
    }

    public void addPlayer(Player player){
        players[size]=player;
        size++;
    }

    public Player getPlayer(int index) {
        return players[index];
    }
    public int size(){
        return size;
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getSize(){
        return size;
    }
}
