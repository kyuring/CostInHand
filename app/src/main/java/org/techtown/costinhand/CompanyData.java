package org.techtown.costinhand;

public class CompanyData {
    private String name; // 회사명
    private String ceo_name; // 대표명
    private String seq; // 시퀀스
    private String tel; // 회사 전화번호
    private String phone; // 핸드폰번호
    private String etc; // 비고

    public CompanyData(String name, String ceo_name, String seq, String tel, String phone, String etc) {
        this.name = name;
        this.ceo_name = ceo_name;
        this.seq = seq;
        this.tel = tel;
        this.phone = phone;
        this.etc = etc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCeo_name() {
        return ceo_name;
    }

    public void setCeo_name(String ceo_name) {
        this.ceo_name = ceo_name;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }
}
