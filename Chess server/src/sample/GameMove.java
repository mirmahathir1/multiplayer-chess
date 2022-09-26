package sample;

import java.io.Serializable;
import java.util.LinkedList;

public class GameMove implements Serializable {
    String whiteName;
    String blackName;
    LinkedList<MoveInfo> whiteMove =new LinkedList<>();
    LinkedList<MoveInfo> blackMove =new LinkedList<>();

    public String getWhiteName() {
        return whiteName;
    }

    public void setWhiteName(String whiteName) {
        this.whiteName = whiteName;
    }

    public String getBlackName() {
        return blackName;
    }

    public void setBlackName(String blackName) {
        this.blackName = blackName;
    }

    public LinkedList<MoveInfo> getWhiteMove() {
        return whiteMove;
    }

    public void setWhiteMove(LinkedList<MoveInfo> whiteMove) {
        this.whiteMove = whiteMove;
    }

    public LinkedList<MoveInfo> getBlackMove() {
        return blackMove;
    }

    public void setBlackMove(LinkedList<MoveInfo> blackMove) {
        this.blackMove = blackMove;
    }
}
