package jp.ac.titech.itpro.sdl.tsuyoso2.NotUse;

import java.util.ArrayList;

/**
 * 材料クラス
 */

public class TAllIngredients {

    //材料総数
    private int fTotalNum;

    //材料リスト
    private ArrayList<TIngredient> fIngredients;

    /**
     * デフォルトコンストラクタ
     */
    public TAllIngredients(){
        fTotalNum = 0;
        fIngredients = new ArrayList<>();
    }

    /**
     * コンストラクタ
     * @param fIngredients
     * @param fIngredientsTotal
     */
    public TAllIngredients(ArrayList<TIngredient> fIngredients, int fIngredientsTotal) {
        this.fIngredients = fIngredients;
        this.fTotalNum = fIngredientsTotal;
    }

    public TAllIngredients(TAllIngredients src){
        this.fTotalNum = src.fTotalNum;
        this.fIngredients = new ArrayList<>(src.fIngredients);
    }

    /**
     * copy
     * @param src
     * @return
     */
    public void copyFrom(TAllIngredients src){

        assert this.fTotalNum == src.fTotalNum;
        this.fTotalNum = src.fTotalNum;
        this.fIngredients = new ArrayList<>(src.fIngredients);
    }

    /**
     * clone
     * @return
     */
    public TAllIngredients clone(){
        return new TAllIngredients(this);
    }

    /**
     * 材料総量getter
     * @return
     */
    public int getTotalNum() {
        return fTotalNum;
    }

    /**
     * 材料総量setter
     * @param fTotalNum
     */
    public void setTotalNum(int fTotalNum) {
        this.fTotalNum = fTotalNum;
    }

    /**
     * 材料リストgetter
     * @return
     */
    public ArrayList<TIngredient> getfIngredients() {
        return fIngredients;
    }

    /**
     * 材料リストsetter
     * @param fIngredients
     */
    public void setfIngredients(ArrayList<TIngredient> fIngredients) {
        this.fIngredients = fIngredients;
    }
}
