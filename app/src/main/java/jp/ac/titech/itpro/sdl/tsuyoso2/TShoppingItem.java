package jp.ac.titech.itpro.sdl.tsuyoso2;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Yamada on 2016/12/11.
 */
public class TShoppingItem {
    //材料名
    private String fName;

    //日付と量のマップ
    //key = date
    //value = quantity
    private Map<String, String> fQuantityMap;

    /**
     * コンストラクタ
     * @param fName
     * @param quantityMap
     */
    public TShoppingItem(String fName, Map<String, String> quantityMap) {
        this.fName = fName;
        this.fQuantityMap = quantityMap;
    }

    public TShoppingItem(String name){
        this.fName = name;
        fQuantityMap = new TreeMap<String, String>();
    }

    public Map<String, String> getQuantityMap(){
        return this.fQuantityMap;
    }

    public void addDateQuantity(String date, String quantity){
        this.fQuantityMap.put(date, quantity);
    }

    //日付セットを渡して空の文字列で初期化
    public void initQuantityMap(Set<String> dateSet){
        for(String date : dateSet){
            this.fQuantityMap.put(date, "");
        }
    }

    public String getName() {
        return fName;
    }

    public void setName(String fName) {
        this.fName = fName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TShoppingItem that = (TShoppingItem) o;

        return fName != null ? fName.equals(that.fName) : that.fName == null;

    }

    @Override
    public int hashCode() {
        return fName != null ? fName.hashCode() : 0;
    }
}
