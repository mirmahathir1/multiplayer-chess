package sample;

import java.io.Serializable;

public class MoveInfo implements Serializable {
    int preCol;
    int preRow;
    int newRow;
    int newCol;
    boolean check=false;
    boolean checkmate=false;
    boolean elimination=false;
    char eliminatePieceName;
    boolean castling=false;
    boolean pawnToQueen=false;

    public MoveInfo(int preRow,int preCol , int newRow, int newCol) {
        this.preCol = preCol;
        this.preRow = preRow;
        this.newRow = newRow;
        this.newCol = newCol;
    }

    public int getPreCol() {
        return preCol;
    }

    public void setPreCol(int preCol) {
        this.preCol = preCol;
    }

    public int getPreRow() {
        return preRow;
    }

    public void setPreRow(int preRow) {
        this.preRow = preRow;
    }

    public int getNewRow() {
        return newRow;
    }

    public void setNewRow(int newRow) {
        this.newRow = newRow;
    }

    public int getNewCol() {
        return newCol;
    }

    public void setNewCol(int newCol) {
        this.newCol = newCol;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isCheckmate() {
        return checkmate;
    }

    public void setCheckmate(boolean checkmate) {
        this.checkmate = checkmate;
    }

    public boolean isElimination() {
        return elimination;
    }

    public void setElimination(boolean elimination) {
        this.elimination = elimination;
    }

    public char getEliminatePieceName() {
        return eliminatePieceName;
    }

    public void setEliminatePieceName(char eliminatePieceName) {
        this.eliminatePieceName = eliminatePieceName;
    }

    public MoveInfo getFriendMove(){
        MoveInfo moveInfo=new MoveInfo(7-preRow,7-preCol,7-newRow,7-newCol);
        moveInfo.setCheck(check);
        moveInfo.setCheckmate(checkmate);
        moveInfo.setElimination(elimination);
        moveInfo.setEliminatePieceName(eliminatePieceName);
        moveInfo.setCastling(castling);
        moveInfo.setPawnToQueen(pawnToQueen);
        return moveInfo;
    }

    public boolean isCastling() {
        return castling;
    }

    public void setCastling(boolean castling) {
        this.castling = castling;
    }

    public boolean isPawnToQueen() {
        return pawnToQueen;
    }

    public void setPawnToQueen(boolean pawnToQueen) {
        this.pawnToQueen = pawnToQueen;
    }



    @Override
    public String toString() {
        return "MoveInfo{" +
                "preCol=" + preCol +
                ", preRow=" + preRow +
                ", newRow=" + newRow +
                ", newCol=" + newCol +
                ", check=" + check +
                ", checkmate=" + checkmate +
                ", elimination=" + elimination +
                ", eliminatePieceName=" + eliminatePieceName +
                '}';
    }
}
