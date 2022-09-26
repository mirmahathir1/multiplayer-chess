package sample;

import java.util.HashMap;

public class Rook extends Piece{

    private HashMap<Integer,Box> validBoxHashMap;

    public Rook(int color, int isMe) {
        super('R', color, isMe);
    }
    
    @Override
    public void saveValidMove(Box[][] box,Box rookBox){
        int isMe=rookBox.getPiece().getIsMe();
        validBoxHashMap=new HashMap<>();
        int row=rookBox.getRow();
        int col=rookBox.getCol();

        for(int i=row+1;i<=7;i++){
            if(box[i][col].getPiece()==null){
                validBoxHashMap.put(box[i][col].hashCode(),box[i][col]);
            }
            else if(box[i][col].getPiece().getIsMe()!=isMe){
                validBoxHashMap.put(box[i][col].hashCode(),box[i][col]);
                break;
            }
            else {
                break;
            }
        }

        for(int i=row-1;i>=0;i--){
            if(box[i][col].getPiece()==null){
                validBoxHashMap.put(box[i][col].hashCode(),box[i][col]);
            }
            else if(box[i][col].getPiece().getIsMe()!=isMe){
                validBoxHashMap.put(box[i][col].hashCode(),box[i][col]);
                break;
            }
            else {
                break;
            }
        }

        for(int i=col+1;i<=7;i++){
            if(box[row][i].getPiece()==null){
               // box[row][i].selectBox();
                validBoxHashMap.put(box[row][i].hashCode(),box[row][i]);

            }
            else if(box[row][i].getPiece().getIsMe()!=isMe){
                validBoxHashMap.put(box[row][i].hashCode(),box[row][i]);
                break;
            }
            else {
                break;
            }
        }

        for(int i=col-1;i>=0;i--){
            if(box[row][i].getPiece()==null){
                validBoxHashMap.put(box[row][i].hashCode(),box[row][i]);

            }
            else if(box[row][i].getPiece().getIsMe()!=isMe){
                //box[row][i].selectBox();
                validBoxHashMap.put(box[row][i].hashCode(),box[row][i]);

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
        for(Box rookBox:validBoxHashMap.values()){
            rookBox.deSelectBox();
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

    public HashMap<Integer,Box> getValidBoxHashMap() {
        return validBoxHashMap;
    }
}
