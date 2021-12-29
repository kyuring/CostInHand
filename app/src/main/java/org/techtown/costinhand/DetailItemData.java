package org.techtown.costinhand;

public class DetailItemData {
    private String detail_item_name;
    private String detail_item_price;
    private String detail_item_gram;

    public DetailItemData(String detail_item_name, String detail_item_price, String detail_item_gram) {
        this.detail_item_name = detail_item_name;
        this.detail_item_price = detail_item_price;
        this.detail_item_gram = detail_item_gram;
    }

    public String getDetail_item_name() {
        return detail_item_name;
    }

    public void setDetail_item_name(String detail_item_name) {
        this.detail_item_name = detail_item_name;
    }

    public String getDetail_item_price() {
        return detail_item_price;
    }

    public void setDetail_item_price(String detail_item_price) {
        this.detail_item_price = detail_item_price;
    }

    public String getDetail_item_gram() {
        return detail_item_gram;
    }

    public void setDetail_item_gram(String detail_item_gram) {
        this.detail_item_gram = detail_item_gram;
    }
}
