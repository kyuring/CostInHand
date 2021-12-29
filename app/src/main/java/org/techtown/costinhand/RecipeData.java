package org.techtown.costinhand;

public class RecipeData {
    private String Recipe_name; // 레시피 이름
    private String Recipe_seq; // 레시피 번호
    private String Food_seq; //재료 번호
    private String Food_Name; // 레시피 안에 들어가는 재료명
    private String Qty; // 수량
    private String Cost_Prc; // 수량
    private String SET_NO; // 순번
    private String MENU_SEQ; // 메뉴 시퀀스
    private String RECIPE_MODE;
    public RecipeData(String recipe_name, String recipe_seq, String food_seq, String food_Name, String qty, String cost_Prc, String Set_no, String Menu_seq) {
        Recipe_name = recipe_name;
        Recipe_seq = recipe_seq;
        Food_seq = food_seq;
        Qty = qty;
        Cost_Prc = cost_Prc;
        Food_Name = food_Name;
        SET_NO = Set_no;
        MENU_SEQ = Menu_seq;
    }

    public RecipeData(String recipe_name, String recipe_seq, String food_seq, String food_Name, String qty, String cost_Prc, String Set_no, String Menu_seq, String RECIPE_MODE) {
        Recipe_name = recipe_name;
        Recipe_seq = recipe_seq;
        Food_seq = food_seq;
        Qty = qty;
        Cost_Prc = cost_Prc;
        Food_Name = food_Name;
        SET_NO = Set_no;
        MENU_SEQ = Menu_seq;
        this.RECIPE_MODE = RECIPE_MODE;
    }

    public RecipeData(String Recipe_seq, String Recipe_name, String Cost_prc, String Qty) {
        this.Recipe_seq = Recipe_seq;
        this.Recipe_name = Recipe_name;
        this.Cost_Prc = Cost_prc;
        this.Qty = Qty;

    }
    public RecipeData(String Food_seq, String Food_Name, String Cost_prc, String Qty, String RECIPE_MODE) {
        this.Food_seq = Food_seq;
        this.Food_Name = Food_Name;
        this.Cost_Prc = Cost_prc;
        this.Qty = Qty;
        this.RECIPE_MODE = RECIPE_MODE;
    }


    public String getRecipe_name() {
        return Recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        Recipe_name = recipe_name;
    }

    public String getRecipe_seq() {
        return Recipe_seq;
    }

    public void setRecipe_seq(String recipe_seq) {
        Recipe_seq = recipe_seq;
    }

    public String getFood_seq() {
        return Food_seq;
    }

    public void setFood_seq(String food_seq) {
        Food_seq = food_seq;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getCost_Prc() {
        return Cost_Prc;
    }

    public void setCost_Prc(String cost_Prc) {
        Cost_Prc = cost_Prc;
    }

    public String getFood_Name() {
        return Food_Name;
    }

    public void setFood_Name(String food_Name) {
        Food_Name = food_Name;
    }

    public String getSET_NO() {
        return SET_NO;
    }

    public void setSET_NO(String SET_NO) {
        this.SET_NO = SET_NO;
    }

    public String getMENU_SEQ() {
        return MENU_SEQ;
    }

    public void setMENU_SEQ(String MENU_SEQ) {
        this.MENU_SEQ = MENU_SEQ;
    }

    public String getRECIPE_MODE() {
        return RECIPE_MODE;
    }

    public void setRECIPE_MODE(String RECIPE_MODE) {
        this.RECIPE_MODE = RECIPE_MODE;
    }
}

