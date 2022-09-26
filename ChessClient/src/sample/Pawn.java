package sample;

import java.util.HashMap;

public class Pawn extends Piece{

    private HashMap<Integer,Box> unSafeForKingHashMap;
    private HashMap<Integer,Box> validBoxHashMap;
    private HashMap<Integer,Box> validEliminationBoxHashMap;

    public Pawn(int color, int isMe) {
        super('P', color, isMe);
    }

    @Override
    public void unSafeForKing(Box[][] box,Box pawnBox){
        int isMe=pawnBox.getPiece().getIsMe();

        unSafeForKingHashMap=new HashMap<>();
        int row;
        int col=pawnBox.getCol()+1;
        if(getIsMe()!=isMe){
            row=pawnBox.getRow()+1;
        }
        else {
            row=pawnBox.getRow()-1;
        }
        if((col)<=7){
            if(box[row][col].getPiece()==null){
                unSafeForKingHashMap.put(box[row][col].hashCode(),box[row][col]);
            }
        }

        col=pawnBox.getCol()-1;
        if((col)>=0){
            if(box[row][col].getPiece()==null){
                unSafeForKingHashMap.put(box[row][col].hashCode(),box[row][col]);
            }
        }

    }

    @Override
    public void saveValidElimination(Box[][] box,Box pawnBox){
        int isMe=pawnBox.getPiece().getIsMe();

        validEliminationBoxHashMap=new HashMap<>();
        int row;
        int col=pawnBox.getCol()+1;
        if(getIsMe()!=isMe){
            row=pawnBox.getRow()+1;
        }
        else {
            row=pawnBox.getRow()-1;
        }
        if((col)<=7){
            if(box[row][col].getPiece()!=null && box[row][col].getPiece().getIsMe()!=isMe){
                validEliminationBoxHashMap.put(box[row][col].hashCode(),box[row][col]);
            }
        }

        col=pawnBox.getCol()-1;
        if((col)>=0){
            if(box[row][col].getPiece()!=null && box[row][col].getPiece().getIsMe()!=isMe){
                validEliminationBoxHashMap.put(box[row][col].hashCode(),box[row][col]);
            }
        }

    }

    @Override
    public void saveValidMove(Box[][] box,Box pawnBox){
        int isMe=pawnBox.getPiece().getIsMe();

        validBoxHashMap=new HashMap<>();
        int row=pawnBox.getRow();
        int col=pawnBox.getCol();

        if(isMe==1){
            if(row==6 && box[row-1][col].getPiece()==null && box[row-2][col].getPiece()==null){
                validBoxHashMap.put(box[row-2][col].hashCode(),box[row-2][col]);
            }

            if(box[row-1][col].getPiece()==null){
                validBoxHashMap.put(box[row-1][col].hashCode(),box[row-1][col]);
            }

            if((col+1)<=7)
                if(box[row-1][col+1].getPiece()!=null && box[row-1][col+1].getPiece().getIsMe()!=isMe){
                    validBoxHashMap.put(box[row-1][col+1].hashCode(),box[row-1][col+1]);
                }

            if((col-1)>=0)
                if(box[row-1][col-1].getPiece()!=null && box[row-1][col-1].getPiece().getIsMe()!=isMe){
                    validBoxHashMap.put(box[row-1][col-1].hashCode(),box[row-1][col-1]);
                }
        }
        else {
            if(row==1 && box[row+1][col].getPiece()==null && box[row+2][col].getPiece()==null){
                validBoxHashMap.put(box[row+2][col].hashCode(),box[row+2][col]);
            }

            if(box[row+1][col].getPiece()==null){
                validBoxHashMap.put(box[row+1][col].hashCode(),box[row+1][col]);
            }

            if((col+1)<=7)
                if(box[row+1][col+1].getPiece()!=null && box[row+1][col+1].getPiece().getIsMe()!=isMe){
                    validBoxHashMap.put(box[row+1][col+1].hashCode(),box[row+1][col+1]);
                }

            if((col-1)>=0)
                if(box[row+1][col-1].getPiece()!=null && box[row+1][col-1].getPiece().getIsMe()!=isMe){
                    validBoxHashMap.put(box[row+1][col-1].hashCode(),box[row+1][col-1]);
                }
        }

    }

    @Override
    public void selectValidMove(){
        for(Box validBox:validBoxHashMap.values()){
            validBox.selectBox();
        }
    }
    @Override
    public void deSelectValidMove(){
        for(Box pawnBox:validBoxHashMap.values()){
            pawnBox.deSelectBox();
        }
    }
    @Override
    public boolean isValidMove(Box newBox){
        if(validBoxHashMap==null || validBoxHashMap.size()==0)
            return false;
        if(validBoxHashMap.containsValue(newBox))
            return true;
        return false;
    }

    @Override
    public boolean isValidElimination(Box newBox) {
        if(validEliminationBoxHashMap!=null && validEliminationBoxHashMap.containsValue(newBox))
            return true;

        return false;
    }

    @Override
    public boolean issafeForKing(Box newBox){
        if(unSafeForKingHashMap!=null && unSafeForKingHashMap.containsValue(newBox))
            return false;

        return true;
    }

    public HashMap<Integer, Box> getValidBoxHashMap() {
        return validBoxHashMap;
    }

}
