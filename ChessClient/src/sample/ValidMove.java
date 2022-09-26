package sample;

public interface ValidMove {

    default void unSafeForKing(Box[][] box,Box pieceBox){
        saveValidMove(box,pieceBox);
    }
    default void saveValidElimination(Box[][] box,Box pieceBox){
        saveValidMove(box,pieceBox);
    }

    void saveValidMove(Box[][] box,Box pieceBox);
    void selectValidMove();
    void deSelectValidMove();
    boolean isValidMove(Box newBox);
    default boolean isValidElimination(Box newBox){
        return isValidMove(newBox);
    }
    default boolean issafeForKing(Box newBox){
        return !isValidMove(newBox);
    }
}
