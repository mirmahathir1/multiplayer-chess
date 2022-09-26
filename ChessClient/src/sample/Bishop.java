package sample;

import java.util.HashMap;

public class Bishop extends Piece{

    private HashMap<Integer,Box> validBoxHashMap;

    public Bishop(int color, int isMe) {
        super('B', color, isMe);
    }

    @Override
    public void saveValidMove(Box[][] box,Box bishopBox){
        int isMe=bishopBox.getPiece().getIsMe();

        validBoxHashMap=new HashMap<>();

        for(int i=bishopBox.getRow()+1,j=bishopBox.getCol()+1;(i<=7 && j<=7);i++,j++){
            if(box[i][j].getPiece()==null){
                validBoxHashMap.put(box[i][j].hashCode(),box[i][j]);
            }
            else if(box[i][j].getPiece().getIsMe()!=isMe){
                validBoxHashMap.put(box[i][j].hashCode(),box[i][j]);
                break;
            }
            else {
                break;
            }
        }

        for(int i=bishopBox.getRow()-1,j=bishopBox.getCol()-1;i>=0 && j>=0;i--,j--){
            if(box[i][j].getPiece()==null){
                validBoxHashMap.put(box[i][j].hashCode(),box[i][j]);
            }
            else if(box[i][j].getPiece().getIsMe()!=isMe){
                validBoxHashMap.put(box[i][j].hashCode(),box[i][j]);
                break;
            }
            else {
                break;
            }
        }

        for(int i=bishopBox.getRow()+1,j=bishopBox.getCol()-1;i<=7 && j>=0;i++,j--){
            if(box[i][j].getPiece()==null){
                validBoxHashMap.put(box[i][j].hashCode(),box[i][j]);
            }
            else if(box[i][j].getPiece().getIsMe()!=isMe){
                validBoxHashMap.put(box[i][j].hashCode(),box[i][j]);
                break;
            }
            else {
                break;
            }
        }

        for(int i=bishopBox.getRow()-1,j=bishopBox.getCol()+1;j<=7 && i>=0;j++,i--){
            if(box[i][j].getPiece()==null){
                validBoxHashMap.put(box[i][j].hashCode(),box[i][j]);
            }
            else if(box[i][j].getPiece().getIsMe()!=isMe){
                validBoxHashMap.put(box[i][j].hashCode(),box[i][j]);
                break;
            }
            else {
                break;
            }
        }
    }
    @Override
    public void selectValidMove(){
        for(Box tempBox:validBoxHashMap.values()){
            tempBox.selectBox();
        }
    }
    @Override
    public void deSelectValidMove(){
        System.out.println("validBoxHashMap  "+validBoxHashMap.size());
        for(Box bishopBox:validBoxHashMap.values()){
            bishopBox.deSelectBox();
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
