package org.techtown.costinhand;

public class MainItemData {
    private String main_item_name; // 메뉴명
    private String main_item_price; // 메뉴원가
    private String main_item_seq; // 메뉴 시퀀스
    private String main_pack_qty; // 메뉴 기본 갯수
    
    // 생성자
    public MainItemData(String main_item_seq, String main_item_name, String main_item_price, String main_pack_qty) {
        this.main_item_name = main_item_name;
        this.main_item_price = main_item_price;
        this.main_item_seq = main_item_seq;
        this.main_pack_qty = main_pack_qty;

    }
    
    public String getMain_item_seq() {return main_item_seq;}

    public void setMain_item_seq(String main_item_seq) {this.main_item_seq = main_item_seq;}

    public String getMain_pack_qty() {return main_pack_qty;}

    public void setMain_pack_qty(String main_pack_qty) {this.main_pack_qty = main_pack_qty;}

    public String getMain_item_name() {
        return main_item_name;
    }

    public void setMain_item_name(String main_item_name) {
        this.main_item_name = main_item_name;
    }

    public String getMain_item_price() {
        return main_item_price;
    }

    public void setMain_item_price(String main_item_price) {
        this.main_item_price = main_item_price;
    }
}
