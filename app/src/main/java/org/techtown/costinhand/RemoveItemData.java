package org.techtown.costinhand;

public class RemoveItemData {
    private String remove_item_name;
    private String remove_item_info;


    public RemoveItemData(String remove_item_name, String remove_item_info) {
        this.remove_item_name = remove_item_name;
        this.remove_item_info = remove_item_info;
    }

    public String getRemove_item_name() {
        return remove_item_name;
    }

    public void setRemove_item_name(String remove_item_name) {
        this.remove_item_name = remove_item_name;
    }

    public String getRemove_item_info() {
        return remove_item_info;
    }

    public void setRemove_item_info(String remove_item_info) {
        this.remove_item_info = remove_item_info;
    }
}
