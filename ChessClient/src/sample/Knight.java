package sample;

import java.util.HashMap;

public class Knight extends Piece{

    private HashMap<Integer,Box> validBoxHashMap;

    public Knight(int color, int isMe) {
        super('N', color, isMe);
    }

    @Override
    public void saveValidMove(Box[][] box,Box knightBox){
        int isMe=knightBox.getPiece().getIsMe();

        validBoxHashMap=new HashMap<>();
        int row=knightBox.getRow()+2;
        int col=knightBox.getCol()+1;

        try{
            if((row<8 && col<8) && (box[row][col].getPiece()==null || box[row][col].getPiece().getIsMe()!=isMe)){
                validBoxHashMap.put(box[row][col].hashCode(),box[row][col]);
            }
        }catch (NullPointerException e){
            System.out.println(e);
        }


        col=knightBox.getCol()-1;
        if((row<8 && col>=0) && (box[row][col].getPiece()==null || box[row][col].getPiece().getIsMe()!=isMe)){
            validBoxHashMap.put(box[row][col].hashCode(),box[row][col]);
        }

        row=knightBox.getRow()-2;
        if((row>=0 && col>=0) && (box[row][col].getPiece()==null || box[row][col].getPiece().getIsMe()!=isMe)){
            validBoxHashMap.put(box[row][col].hashCode(),box[row][col]);
        }

        col=knightBox.getCol()+1;
        if((row>=0 && col<8) && (box[row][col].getPiece()==null || box[row][col].getPiece().getIsMe()!=isMe)){
            validBoxHashMap.put(box[row][col].hashCode(),box[row][col]);
        }

        row=knightBox.getRow()+1;
        col=knightBox.getCol()+2;
        if((row<8 && col<8) && (box[row][col].getPiece()==null || box[row][col].getPiece().getIsMe()!=isMe)){
            validBoxHashMap.put(box[row][col].hashCode(),box[row][col]);
        }

        col=knightBox.getCol()-2;
        if((row<8 && col>=0) && (box[row][col].getPiece()==null || box[row][col].getPiece().getIsMe()!=isMe)){
            validBoxHashMap.put(box[row][col].hashCode(),box[row][col]);
        }

        row=knightBox.getRow()-1;
        if((row>=0 && col>=0) && (box[row][col].getPiece()==null || box[row][col].getPiece().getIsMe()!=isMe)){
            validBoxHashMap.put(box[row][col].hashCode(),box[row][col]);
        }

        col=knightBox.getCol()+2;
        if((row>=0 && col<8) && (box[row][col].getPiece()==null || box[row][col].getPiece().getIsMe()!=isMe)){
            validBoxHashMap.put(box[row][col].hashCode(),box[row][col]);
        }
    }
    @Override
    public void selectValidMove(){
        System.out.println("validBoxHashMap  "+validBoxHashMap.size());
        for(Box tempBox:validBoxHashMap.values()){
            tempBox.selectBox();
        }
    }
    @Override
    public void deSelectValidMove(){
        for(Box knightBox:validBoxHashMap.values()){
            knightBox.deSelectBox();
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
    public HashMap<Integer, Box> getValidBoxHashMap() {
        return validBoxHashMap;
    }


}
