package sample;

import java.io.Serializable;
import java.util.Arrays;

public class WaitingPlayerInfo implements Serializable{
    private PlayerInfo[] players;
    int size=0;

    public WaitingPlayerInfo(){
        players=new PlayerInfo[50];
    }

    public void addPlayer(PlayerInfo player){
        players[size]=player;
        size++;
    }

    public PlayerInfo getPlayer(int index) {
        return players[index];
    }
    public int size(){
        return size;
    }

    public PlayerInfo[] getPlayers() {
        return players;
    }
    public int getSize(){
        return size;
    }

    @Override
    public String toString() {
        return "WaitingPlayerInfo{" +
                "players=" + Arrays.toString(players) +
                ", size=" + size +
                '}';
    }
}