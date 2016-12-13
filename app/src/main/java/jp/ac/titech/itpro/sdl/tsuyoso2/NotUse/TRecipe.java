package jp.ac.titech.itpro.sdl.tsuyoso2.NotUse;

/**
 * レシピクラス
 */
public class TRecipe {

    //レシピID
    private int fRecipeID;

    //レシピ名
    private String fRecipeName;

    //何人前
    private int fServings;

    //材料
    private TAllIngredients fIngredients;

    //手順
    private TProcedure fProcedure;

    //メイン食材(ナンバリングの方がいいかも)
    private String fMainIngredient;
    //private int fMainIngredientIndex;

    //ジャンル(ナンバリングの方がいいかも)
    private String fCategory;

    //料理時間(分)
    private double fCookingTime;

    //カロリー(kCal)
    private double fCalory;

    //価格(円)
    private double fPrice;

    /**
     * デフォルトコンストラクタ
     */
    public TRecipe() {
        fIngredients = new TAllIngredients();
        fProcedure = new TProcedure();
    }

    /**
     * コンストラクタ
     * @param fRecipeID
     * @param fRecipeName
     * @param fServings
     * @param fIngredients
     * @param fProcedure
     * @param fMainIngredient
     * @param fCategory
     * @param fCookingTime
     * @param fCalory
     * @param fPrice
     */
    public TRecipe(int fRecipeID, String fRecipeName, int fServings,
                   TAllIngredients fIngredients, TProcedure fProcedure,
                   String fMainIngredient, String fCategory,
                   double fCookingTime, double fCalory, double fPrice) {
        this.fRecipeID = fRecipeID;
        this.fRecipeName = fRecipeName;
        this.fServings = fServings;
        this.fIngredients = fIngredients;
        this.fProcedure = fProcedure;
        this.fMainIngredient = fMainIngredient;
        this.fCategory = fCategory;
        this.fCookingTime = fCookingTime;
        this.fCalory = fCalory;
        this.fPrice = fPrice;
    }

    /**
     * コピーコンストラクタ
     * @param src
     */
    public TRecipe(TRecipe src){
        this.fRecipeID = fRecipeID;
        this.fRecipeName = fRecipeName;
        this.fServings = fServings;
        this.fIngredients = new TAllIngredients(src.fIngredients);
        this.fProcedure = new TProcedure(src.fProcedure);
        this.fMainIngredient = fMainIngredient;
        this.fCategory = fCategory;
        this.fCookingTime = fCookingTime;
        this.fCalory = fCalory;
        this.fPrice = fPrice;
    }

    /**
     * copy
     * @param src
     * @return
     */
    public TRecipe copyFrom(TRecipe src){
        this.fRecipeID = fRecipeID;
        this.fRecipeName = fRecipeName;
        this.fServings = fServings;
        this.fIngredients.copyFrom(src.fIngredients);
        this.fProcedure.copyFrom(src.fProcedure);
        this.fMainIngredient = fMainIngredient;
        this.fCategory = fCategory;
        this.fCookingTime = fCookingTime;
        this.fCalory = fCalory;
        this.fPrice = fPrice;

        return this;
    }

    /**
     * clone
     * @return
     */
    public TRecipe clone(){
        return new TRecipe(this);
    }

    /**
     * レシピIDgetter
     * @return
     */
    public int getRecipeID() {
        return fRecipeID;
    }

    /**
     * レシピIDsetter
     * @param fRecipeID
     */
    public void setRecipeID(int fRecipeID) {
        this.fRecipeID = fRecipeID;
    }

    /**
     * レシピ名getter
     * @return
     */
    public String getRecipeName() {
        return fRecipeName;
    }

    /**
     * レシピ名setter
     * @param fRecipeName
     */
    public void setRecipeName(String fRecipeName) {
        this.fRecipeName = fRecipeName;
    }

    /**
     * 何人前getter
     * @return
     */
    public int getServings() {
        return fServings;
    }

    /**
     * 何人前setter
     * @param fServings
     */
    public void setServings(int fServings) {
        this.fServings = fServings;
    }

    /**
     * 材料getter
     * @return
     */
    public TAllIngredients getIngredients() {
        return fIngredients;
    }

    /**
     * 材料setter
     * @param fIngredients
     */
    public void setfIngredients(TAllIngredients fIngredients) {
        this.fIngredients = fIngredients;
    }

    /**
     * 手順getter
     */
    public TProcedure getProcedure() {
        return fProcedure;
    }

    /**
     * 手順setter
     * @param fProcedure
     */
    public void setProcedure(TProcedure fProcedure) {
        this.fProcedure = fProcedure;
    }

    /**
     * メイン材料getter
     * @return
     */
    public String getMainIngredient() {
        return fMainIngredient;
    }

    /**
     * メイン材料setter
     * @param fMainIngredient
     */
    public void setMainIngredient(String fMainIngredient) {
        this.fMainIngredient = fMainIngredient;
    }

    /**
     * ジャンルgetter
     * @return
     */
    public String getCategory() {
        return fCategory;
    }

    /**
     * ジャンルsetter
     * @param fCategory
     */
    public void setCategory(String fCategory) {
        this.fCategory = fCategory;
    }

    /**
     * 調理時間getter
     * @return
     */
    public double getCookingTime() {
        return fCookingTime;
    }

    /**
     * 調理時間setter
     * @param fCookingTime
     */
    public void setfCookingTime(double fCookingTime) {
        this.fCookingTime = fCookingTime;
    }

    /**
     * カロリーgetter
     * @return
     */
    public double getfCalory() {
        return fCalory;
    }

    /**
     * カロリーsetter
     * @param fCalory
     */
    public void setCalory(double fCalory) {
        this.fCalory = fCalory;
    }

    /**
     * 価格getter
     * @return
     */
    public double getPrice() {
        return fPrice;
    }

    /**
     * 価格setter
     * @param fPrice
     */
    public void setPrice(double fPrice) {
        this.fPrice = fPrice;
    }
}
