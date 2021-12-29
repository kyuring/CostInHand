package org.techtown.costinhand;

public class FoodData {
    String seq; //재료 시퀀스
    String name; // 재료명
    String food_kind; // 재료종류
    String unit; // 단위
    String price; // 구매가
    String cost_price; // 원가
    String qty;
    String RECIPE_SEQ; // 소스일때 사용

    public FoodData(String seq, String name, String food_kind, String unit, String price, String cost_price, String qty) {
        this.seq = seq;
        this.name = name;
        this.food_kind = food_kind;
        this.unit = unit;
        this.price = price;
        this.cost_price = cost_price;
        this.qty = qty;
    }

    public FoodData(String seq, String name, String food_kind, String unit, String price, String cost_price, String qty, String receipe_seq) {
        this.seq = seq;
        this.name = name;
        this.food_kind = food_kind;
        this.unit = unit;
        this.price = price;
        this.cost_price = cost_price;
        this.qty = qty;
        this.RECIPE_SEQ = receipe_seq;
    }

    public FoodData(String Recipe_seq, String seq, String name, String cost_price) {
        this.RECIPE_SEQ = Recipe_seq;
        this.seq = seq;
        this.name = name;
        this.cost_price = cost_price;
    }


    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFood_kind() {
        return food_kind;
    }

    public void setFood_kind(String food_kind) {
        this.food_kind = food_kind;
    }

    public int getUnit() {
        int units = 0;
        if(this.unit.equals("4")) { // kg
            units = 0;
        } else if(this.unit.equals("3")) { //g
            units = 1;
        } else if(this.unit.equals("10")) { //l
            units = 2;
        } else if(this.unit.equals("9")) { // ml
            units = 3;
        } else if(this.unit.equals("8")){ // 개
            units = 4;
        }
        return units;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCost_price() {
        return cost_price;
    }

    public void setCost_price(String cost_price) {
        this.cost_price = cost_price;
    }
    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getRECIPE_SEQ() {
        return RECIPE_SEQ;
    }

    public void setRECIPE_SEQ(String RECIPE_SEQ) {
        this.RECIPE_SEQ = RECIPE_SEQ;
    }
}
