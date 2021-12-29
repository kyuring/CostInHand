package org.techtown.costinhand;

import android.util.Log;

import androidx.annotation.Nullable;

public class RecipeDeleteData {
    private String Recipe_seq; // 레시피 번호
    private String Food_seq; //재료 번호
    private String MENU_SEQ; // 메뉴 시퀀스
    private String buttonName;

    public RecipeDeleteData(@Nullable String Recipe_seq, String Food_seq, @Nullable String MENU_SEQ, String buttonName) {
        this.Recipe_seq = Recipe_seq;
        this.Food_seq = Food_seq;
        this.MENU_SEQ = MENU_SEQ;
        this.buttonName = buttonName;
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

    public String getMENU_SEQ() {
        return MENU_SEQ;
    }

    public void setMENU_SEQ(String MENU_SEQ) {
        this.MENU_SEQ = MENU_SEQ;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }
}

