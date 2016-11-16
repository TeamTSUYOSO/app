package jp.ac.titech.itpro.sdl.tsuyoso2;

import android.graphics.Bitmap;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 自動提案の結果
 */
public class TRecommend {
    private int fRecipeId; //レシピID
    private String fRecipeName; //レシピ名
    private String fDate; //提案対象日付 yyyy-MM-dd
    public final String dateFormat = "yyyy-MM-dd";
    private Bitmap fImageBitMap; //画像
    //private URL fImageURL; //レシピ画像URL

    /**
     * デフォルトコンストラクタ
     */
    public TRecommend(){
    }

    /**
     * コンストラクタ
     */
    public TRecommend(int recipeId, String recipeName , String date, Bitmap image) {
        this.fRecipeId = recipeId;
        this.fRecipeName = recipeName;
        this.fDate = date;
        this.fImageBitMap = image;
    }


    /**
     * コピーコンストラクタ
     * @param src
     */
    public TRecommend(TRecommend src){
        this.fRecipeId = src.fRecipeId;
        this.fRecipeName = src.fRecipeName;
        this.fDate = src.fDate;
        this.fImageBitMap = src.fImageBitMap;
    }

    public boolean hasImage(){
        return this.fImageBitMap != null;
    }

    /**
     *  getter
     */
    public int getRecpeId() {
        return fRecipeId;
    }
    public String getRecpeName() { return fRecipeName; }
    public String getDate(){ return fDate; }
    //public URL getImageURL(){ return fImageURL };
    public int getYear(){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(fDate));
            return calendar.YEAR;
        }catch (java.text.ParseException e){
            return 0;
        }
    }

    public int getMonth(){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(fDate));
            return calendar.get(Calendar.MONTH) + 1;
        }catch (java.text.ParseException e){
            return 0;
        }
    }

    public int getDay(){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(fDate));
            return calendar.get(Calendar.DATE);
        }catch (java.text.ParseException e){
            return 0;
        }
    }

    public Bitmap getImage(){
        return this.fImageBitMap;
    }

    /**
     * setter
     */
    public void setRecipeId(int recipeId){ this.fRecipeId = recipeId; }
    public void setRecipeName(String recipeName) {
        this.fRecipeName = recipeName;
    }
    public void setDate(String date) {
        this.fDate = date;
    }
    public void setImageBitMap(Bitmap image){ this.fImageBitMap = image; }
}
