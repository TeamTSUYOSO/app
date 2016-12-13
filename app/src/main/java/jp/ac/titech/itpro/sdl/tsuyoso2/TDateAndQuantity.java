package jp.ac.titech.itpro.sdl.tsuyoso2;

/**
 * Created by Yamada on 2016/12/11.
 */
public class TDateAndQuantity {

    //材料が必要な日
    private String fDate;

    //必要な量
    private String fQuantity;

    /**
     * コンストラクタ
     * @param fDate
     * @param fQuantity
     */
    public TDateAndQuantity(String fDate, String fQuantity) {
        this.fDate = fDate;
        this.fQuantity = fQuantity;
    }

    public String getDate() {
        return fDate;
    }

    public void setDate(String fDate) {
        this.fDate = fDate;
    }

    public String getQuantity() {
        return fQuantity;
    }

    public void setQuantity(String fQuantity) {
        this.fQuantity = fQuantity;
    }
}
