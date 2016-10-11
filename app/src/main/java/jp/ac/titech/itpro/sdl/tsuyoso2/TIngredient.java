package jp.ac.titech.itpro.sdl.tsuyoso2;

/**
 * 材料1つのクラス
 */
public class TIngredient {

    //材料名
    private String fName;

    //量
    private double fAmount;

    /**
     * デフォルトコンストラクタ
     */
    public TIngredient(){
    }

    /**
     * コンストラクタ
     * @param fName
     * @param fAmount
     */
    public TIngredient(String fName, double fAmount) {
        this.fName = fName;
        this.fAmount = fAmount;
    }


    /**
     * コピーコンストラクタ
     * @param src
     */
    public TIngredient(TIngredient src){
        this.fName = src.fName;
        this.fAmount = src.fAmount;
    }

    /**
     * 材料名getter
     * @return
     */
    public String getName() {
        return fName;
    }

    /**
     * 材料名setter
     * @param fName
     */
    public void setName(String fName) {
        this.fName = fName;
    }

    /**
     * 量getter
     * @return
     */
    public double getAmount() {
        return fAmount;
    }

    /**
     * 量setter
     * @param fAmount
     */
    public void setAmount(double fAmount) {
        this.fAmount = fAmount;
    }
}
