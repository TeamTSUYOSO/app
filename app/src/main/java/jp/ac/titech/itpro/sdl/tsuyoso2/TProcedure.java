package jp.ac.titech.itpro.sdl.tsuyoso2;

import java.util.ArrayList;

/**
 * 手順クラス
 */
public class TProcedure {

    //手順
    private ArrayList<String> fProcedure;

    /**
     * デフォルトコンストラクタ
     */
    public TProcedure(){
        fProcedure = new ArrayList<>();
    }

    /**
     * コンストラクタ
     * @param fProcedure
     */
    public TProcedure(ArrayList<String> fProcedure) {
        this.fProcedure = fProcedure;
    }

    /**
     * コピーコンストラクタ
     * @param src
     */
    public TProcedure(TProcedure src){
        this.fProcedure = new ArrayList<>(src.fProcedure);
    }

    /**
     * copy
     * @param src
     * @return
     */
    public TProcedure copyFrom(TProcedure src){
        fProcedure = new ArrayList<>(src.fProcedure);
        return this;
    }

    /**
     * clone
     * @return
     */
    public TProcedure clone(){
        return new TProcedure(this);
    }

    /**
     * 手順getter
     * @return
     */
    public ArrayList<String> getProcedure() {
        return fProcedure;
    }

    /**
     * 手順setter
     * @param fProcedure
     */
    public void setfProcedure(ArrayList<String> fProcedure) {
        this.fProcedure = fProcedure;
    }
}
