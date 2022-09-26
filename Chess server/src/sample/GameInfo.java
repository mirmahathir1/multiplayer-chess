package sample;

import java.io.Serializable;

public class GameInfo implements Serializable {
    private PlayerInfo me;
    private PlayerInfo friend;
    private int gameNumber;

    public GameInfo(PlayerInfo me, PlayerInfo friend) {
        this.me = me;
        this.friend = friend;
    }

    public PlayerInfo getMe() {
        return me;
    }

    public void setMe(PlayerInfo me) {
        this.me = me;
    }

    public PlayerInfo getFriend() {
        return friend;
    }

    public void setFriend(PlayerInfo friend) {
        this.friend = friend;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }

    @Override
    public String toString() {
        return "GameInfo{" +
                "me=" + me +
                ", friend=" + friend +
                ", gameNumber=" + gameNumber +
                '}';
    }
}
