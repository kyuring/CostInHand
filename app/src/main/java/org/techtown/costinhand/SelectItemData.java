package org.techtown.costinhand;

public class SelectItemData {
    private String select_item_name;
    private String select_item_price;
    private String select_item_gram;

    public SelectItemData(String select_item_name, String select_item_price) {
        this.select_item_name = select_item_name;
        this.select_item_price = select_item_price;
    }

    public SelectItemData(String select_item_name, String select_item_price, String select_item_gram) {
        this.select_item_name = select_item_name;
        this.select_item_price = select_item_price;
        this.select_item_gram = select_item_gram;
    }

    public String getSelect_item_name() {
        return select_item_name;
    }

    public void setSelect_item_name(String select_item_name) {
        this.select_item_name = select_item_name;
    }

    public String getSelect_item_price() {
        return select_item_price;
    }

    public void setSelect_item_price(String select_item_price) {
        this.select_item_price = select_item_price;
    }
    public String getSelect_item_gram() {
        return select_item_gram;
    }

    public void setSelect_item_gram(String select_item_gram) {
        this.select_item_gram = select_item_gram;
    }
}
