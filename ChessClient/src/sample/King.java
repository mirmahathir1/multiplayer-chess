package sample;

import java.util.HashMap;

public class King extends Piece{

    private int isMe;
    private HashMap<Integer,Box> validBoxHashMap;

    public King(int color, int isMe) {
        super('K', color, isMe);
    }

    @Override
    public void saveValidMove(Box[][] box,Box kingBox){
        isMe=kingBox.getPiece().getIsMe();

        validBoxHashMap=new HashMap<>();
        int row=kingBox.getRow();
        int col=kingBox.getCol();

        if( (col+1)<8 && checkBoxIsValidForKing(box[row][col+1])){
            validBoxHashMap.put(box[row][col+1].hashCode(),box[row][col+1]);
        }
        if((row+1)<8 && checkBoxIsValidForKing(box[row+1][col])){
            validBoxHashMap.put(box[row+1][col].hashCode(),box[row+1][col]);
        }

        if( (col-1)>=0 && checkBoxIsValidForKing(box[row][col-1])){
            validBoxHashMap.put(box[row][col-1].hashCode(),box[row][col-1]);
        }
        if((row-1)>=0 && checkBoxIsValidForKing(box[row-1][col])){
            validBoxHashMap.put(box[row-1][col].hashCode(),box[row-1][col]);
        }

        if( (col+1)<8 && (row+1)<8 && checkBoxIsValidForKing(box[row+1][col+1])){
            validBoxHashMap.put(box[row+1][col+1].hashCode(),box[row+1][col+1]);
        }
        if( (col+1)<8 && (row-1)>=0 && checkBoxIsValidForKing(box[row-1][col+1])){
            validBoxHashMap.put(box[row-1][col+1].hashCode(),box[row-1][col+1]);
        }

        if( (col-1)>=0 && (row+1)<8 && checkBoxIsValidForKing(box[row+1][col-1])){
            validBoxHashMap.put(box[row+1][col-1].hashCode(),box[row+1][col-1]);
        }
        if( (col-1)>=0 && (row-1)>=0 && checkBoxIsValidForKing(box[row-1][col-1])){
            validBoxHashMap.put(box[row-1][col-1].hashCode(),box[row-1][col-1]);
        }

        if(row==7 && (col+2)<=7 && box[7][col+1].getPiece()==null &&
                box[7][7].getPiece()!=null && box[7][7].getPiece().getName()=='R'
                && box[7][7].getPiece().getIsMe()==isMe && checkBoxIsValidForKing(box[7][col+2])){
            validBoxHashMap.put(box[7][col+2].hashCode(),box[7][col+2]);
        }
        if(row==7 && (col-2)>=0 && box[7][col-1].getPiece()==null &&
                box[7][0].getPiece()!=null && box[7][0].getPiece().getName()=='R'
                && box[7][0].getPiece().getIsMe()==isMe && checkBoxIsValidForKing(box[7][col-2])){
            validBoxHashMap.put(box[7][col-2].hashCode(),box[7][col-2]);
        }
    }
    public boolean checkBoxIsValidForKing(Box newBox){
        if(newBox.getPiece()!=null && newBox.getPiece().getIsMe()==isMe)
            return false;

        for(Box temp:Chess.getFriendHashMap().values()){
            if(temp.getPiece()!=null && !temp.getPiece().issafeForKing(newBox)){
//                if(temp.piece.getName()=='P'){
//                    System.out.println("nj");
//                }
                return false;
            }
        }
        return true;
    }

    public static boolean isMeRemainChecked(Box[][] boxes,Box previousBox,Box newBox){
        Box duplicateBoxes[][] = new Box[8][8];
        Box.copyBoxes(duplicateBoxes, boxes);
        HashMap<Integer,Box> myHashMap=new HashMap<>();
        HashMap<Integer,Box> friendHashMap=new HashMap<>();

        for(Box temp: Chess.getMyHashMap().values()){
            myHashMap.put(temp.hashCode(),temp);
        }

        for(Box temp: Chess.getFriendHashMap().values()){
            friendHashMap.put(temp.hashCode(),temp);
        }
        Box.pseodoMove(duplicateBoxes[previousBox.getRow()][previousBox.getCol()], duplicateBoxes[newBox.getRow()][newBox.getCol()], duplicateBoxes,myHashMap,friendHashMap);
        Box kingBox=myHashMap.get(500);
        return isCheck(duplicateBoxes,kingBox,friendHashMap);
    }


    public static boolean isCheck(Box[][] box,Box kingBox,HashMap<Integer,Box> hashMap){
        try {
            for(Box temp:hashMap.values()){
                temp.getPiece().saveValidElimination(box,temp);
                if(temp.getPiece().isValidElimination(kingBox)){
                    return true;
                }
            }
        }catch (Exception e) {
            System.out.println("Exception in isCheck");
            //e.printStackTrace();
            return false;

        }
        return false;
    }

    public static boolean isCheckMet(Box[][] box){
        System.out.println("in isCheckMet");

        Box duplicateBoxes[][] = new Box[8][8];
        Box.copyBoxes(duplicateBoxes, box);

        HashMap<Integer,Box> myHashMap=new HashMap<>();
        HashMap<Integer,Box> friendHashMap=new HashMap<>();

        for(Box temp: Chess.getMyHashMap().values()){
            myHashMap.put(temp.hashCode(),duplicateBoxes[temp.getRow()][temp.getCol()]);
        }

        for(Box temp: Chess.getFriendHashMap().values()){
            friendHashMap.put(temp.hashCode(),duplicateBoxes[temp.getRow()][temp.getCol()]);
        }

        Box kingBox=friendHashMap.get(500);

        kingBox.getPiece().saveValidMove(box,kingBox);
        if(kingBox.getPiece().getValidBoxHashMap()!=null && kingBox.getPiece().getValidBoxHashMap().size()!=0){
            return false;
        }

        for(Box previous:Chess.getFriendHashMap().values()){
            if(previous.hashCode()==500)
                continue;
            previous.getPiece().saveValidMove(box,previous);
            //System.out.print("Row : "+previous.getRow()+"\tCol : "+previous.getCol());
            //System.out.println("\tSize "+previous.getPiece().getValidBoxHashMap().size());
            if(previous.getPiece().getValidBoxHashMap()!=null && previous.getPiece().getValidBoxHashMap().size()!=0){
                for(Box now:previous.getPiece().getValidBoxHashMap().values()){
//                    System.out.println("previous");
//
//                    for(int i=0;i<8;i++){
//                        for(int j=0;j<8;j++){
//                            if(duplicateBoxes[i][j].piece!=null)
//                            System.out.println(duplicateBoxes[i][j]);
//                        }
//                    }
                    Box previousBoxCopy=new Box();
                    Box newBoxCopy=new Box();
                    previousBoxCopy.copyBox(previous);
                    newBoxCopy.copyBox(now);

                    Box.pseodoMove(previous,now, duplicateBoxes,friendHashMap,myHashMap);
//                    System.out.println("next");
//                    for(int i=0;i<8;i++){
//                        for(int j=0;j<8;j++){
//                            if(duplicateBoxes[i][j].piece!=null)
//                                System.out.println(duplicateBoxes[i][j]);
//                        }
//                    }



                    //System.out.println(previous+""+now);
                    if(!isCheck(duplicateBoxes,kingBox,myHashMap)){
                        System.out.println("just check");
                        return false;
                    }

                    Box.returnBackPseodoMove(previousBoxCopy,newBoxCopy,duplicateBoxes,friendHashMap,myHashMap);

//                    System.out.println("return back");
//                    for(int i=0;i<8;i++){
//                        for(int j=0;j<8;j++){
//                            if(duplicateBoxes[i][j].piece!=null)
//                                System.out.println(duplicateBoxes[i][j]);
//                        }
//                    }
                }
            }
        }
        return true;
    }

    @Override
    public void selectValidMove(){
        for(Box tempBox:validBoxHashMap.values()){
            tempBox.selectBox();
        }
    }
    @Override
    public void deSelectValidMove(){
        for(Box kingBox:validBoxHashMap.values()){
            kingBox.deSelectBox();
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
