package sample;

import java.util.HashMap;

public class Queen extends Piece{
    //because queen has both rook and bishop properties.
    private HashMap<Integer,Box> validBoxHashMap;

    private Rook rook;
    private Bishop bishop;

    public Queen(int color, int isMe) {
        super('Q', color, isMe);
        this.rook = new Rook(color, isMe);
        this.bishop =new Bishop( color, isMe);
    }
    @Override
    public void saveValidMove(Box[][] box,Box queenBox){
        rook.saveValidMove(box,queenBox);
        bishop.saveValidMove(box,queenBox);

    }
    @Override
    public void selectValidMove(){
        rook.selectValidMove();
        bishop.selectValidMove();
    }
    @Override
    public void deSelectValidMove(){
        rook.deSelectValidMove();
        bishop.deSelectValidMove();
    }
    @Override
    public boolean isValidMove(Box newBox){
        return rook.isValidMove(newBox) || bishop.isValidMove(newBox);
    }

    public HashMap<Integer, Box> getValidBoxHashMap() {
        validBoxHashMap=new HashMap<>();
        for(Box box:rook.getValidBoxHashMap().values()){
            validBoxHashMap.put(box.hashCode(),box);
        }
        for(Box box:bishop.getValidBoxHashMap().values()){
            validBoxHashMap.put(box.hashCode(),box);
        }
        return validBoxHashMap;
    }
}
