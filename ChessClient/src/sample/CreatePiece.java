package sample;

public class CreatePiece {
    int color;
    char name;
    int isMe;


    public CreatePiece(int color, char name, int isMe) {
        this.color = color;
        this.name = name;
        this.isMe = isMe;
    }

    public Piece getPiece(){
        if(name=='P')
             return  (new Pawn(color,isMe));
        if(name=='K')
            return  (new King(color,isMe));
        if(name=='Q')
            return  (new Queen(color,isMe));
        if(name=='N')
            return  (new Knight(color,isMe));
        if(name=='R')
            return  (new Rook(color,isMe));
        if(name=='B')
            return  (new Bishop(color,isMe));
        return null;
    }
}
