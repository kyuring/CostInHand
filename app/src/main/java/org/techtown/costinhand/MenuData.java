package org.techtown.costinhand;

public class MenuData {
    private String name; // 메뉴명
    private String cost_prc; // 메뉴원가
    private String seq; // 메뉴 시퀀스
    private String count; // 메뉴 기본 갯수

    public MenuData(String name, String cost_prc, String seq, String count) {
        this.name = name;
        this.cost_prc = cost_prc;
        this.seq = seq;
        this.count = count;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost_prc() {
        return cost_prc;
    }

    public void setCost_prc(String cost_prc) {
        this.cost_prc = cost_prc;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
