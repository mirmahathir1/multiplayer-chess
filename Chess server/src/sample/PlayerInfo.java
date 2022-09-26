package sample;

import java.io.Serializable;

public class PlayerInfo  implements Serializable{
    private PlayerInfo friend;
    private int gameNumber;
    private String name;
    private int color=-1;
    private boolean playing=false;
    private int playerNumber;
    private boolean avaiableInNet=true;

    public PlayerInfo(){}


    public PlayerInfo(int gameNumber, String name, int color, boolean playing, int playerNumber, boolean avaiableInNet) {
        this.gameNumber = gameNumber;
        this.name = name;
        this.color = color;
        this.playing = playing;
        this.playerNumber = playerNumber;
        this.avaiableInNet = avaiableInNet;
    }

    public PlayerInfo(String name, boolean playing, int playerNumber) {
        this.playerNumber=playerNumber;
        this.name=name;
        this.playerNumber=playerNumber;
    }

    public PlayerInfo(int playerNumber) {
        this.playerNumber=playerNumber;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public boolean isAvaiableInNet() {
        return avaiableInNet;
    }

    public void setAvaiableInNet(boolean avaiableInNet) {
        this.avaiableInNet = avaiableInNet;
    }

    public PlayerInfo getFriend() {
        return friend;
    }

    public void setFriend(PlayerInfo friend) {
        this.friend = friend;
    }

    @Override
    public String toString() {
        return "PlayerInfo{" +
                "gameNumber=" + gameNumber +
                ", friend=" + friend +
                ", name='" + name + '\'' +
                ", color=" + color +
                ", playing=" + playing +
                ", playerNumber=" + playerNumber +
                ", avaiableInNet=" + avaiableInNet +
                '}';
    }
}
