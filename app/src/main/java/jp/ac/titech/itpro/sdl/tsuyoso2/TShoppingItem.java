package jp.ac.titech.itpro.sdl.tsuyoso2;

import java.util.ArrayList;

/**
 * Created by Yamada on 2016/12/11.
 */
public class TShoppingItem {
    //材料名
    private String fName;

    //日付と量のリスト
    private ArrayList<TDateAndQuantity> fDateAndQuantityList;

    /**
     * コンストラクタ
     * @param fName
     * @param fDateAndQuantityList
     */
    public TShoppingItem(String fName, ArrayList<TDateAndQuantity> fDateAndQuantityList) {
        this.fName = fName;
        this.fDateAndQuantityList = fDateAndQuantityList;
    }

    public TShoppingItem(String name){
        this.fName = name;
        fDateAndQuantityList = new ArrayList<>();
    }

    public ArrayList<TDateAndQuantity> getDateAndQuantityList() {
        return fDateAndQuantityList;
    }

    public void setDateAndQuantityList(ArrayList<TDateAndQuantity> fDateAndQuantityList) {
        this.fDateAndQuantityList = fDateAndQuantityList;
    }

    public String getName() {
        return fName;
    }

    public void setName(String fName) {
        this.fName = fName;
    }
}
