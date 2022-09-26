package sample;

import java.io.Serializable;


public class AllImage implements Serializable {
    private byte[] WK,WN,WQ,WR,WB,WP,BP,BR,BQ,BK,BN,BB;


    public AllImage(){
        WK= FileByte.fileToByte("WK.png");
        BK= FileByte.fileToByte("BK.png");

        WQ= FileByte.fileToByte("WQ.png");
        BQ= FileByte.fileToByte("BQ.png");

        WN= FileByte.fileToByte("WN.png");
        BN= FileByte.fileToByte("BN.png");

        WB= FileByte.fileToByte("WB.png");
        BB= FileByte.fileToByte("BB.png");

        WR= FileByte.fileToByte("WR.png");
        BR= FileByte.fileToByte("BR.png");

        WP= FileByte.fileToByte("WP.png");
        BP= FileByte.fileToByte("BP.png");
    }

    public byte[] getWK() {
        return WK;
    }

    public byte[] getWN() {
        return WN;
    }

    public byte[] getWQ() {
        return WQ;
    }

    public byte[] getWR() {
        return WR;
    }

    public byte[] getWB() {
        return WB;
    }

    public byte[] getWP() {
        return WP;
    }

    public byte[] getBP() {
        return BP;
    }

    public byte[] getBR() {
        return BR;
    }

    public byte[] getBQ() {
        return BQ;
    }

    public byte[] getBK() {
        return BK;
    }

    public byte[] getBN() {
        return BN;
    }

    public byte[] getBB() {
        return BB;
    }

}
