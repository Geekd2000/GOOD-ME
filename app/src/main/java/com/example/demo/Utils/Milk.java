package com.example.demo.Utils;

/**
 * @author: JJY
 * @date: 2021/6/5 22:50
 */
public class Milk {

    private int m_id;
    private String m_name;//奶茶名
    private int m_price;//价格
    private String m_picture;//图片url
    private String m_info;//简介
    private int m_type;//奶茶分类 1:当季新品 2:人气推荐 3:限定果茶 4:果茶系列 5:冷萃咖啡
    // 6:芝士茗茶 7:轻乳茶 8:奶茶家族 9:料多多 10:用餐提醒
    private int m_xin;//新品
    private int m_cha;//含茶
    private int m_ru;//含乳制品
    private int m_ka;//含咖啡
    private int m_mang;//含芒果
    private int m_bing;//仅限冰饮

    public Milk() {
    }

    public Milk(int m_id, String m_name, int m_price, String m_picture, String m_info,
                int m_type, int m_xin, int m_cha, int m_ru, int m_ka, int m_mang, int m_bing) {
        this.m_id = m_id;
        this.m_name = m_name;
        this.m_price = m_price;
        this.m_picture = m_picture;
        this.m_info = m_info;
        this.m_type = m_type;
        this.m_xin = m_xin;
        this.m_cha = m_cha;
        this.m_ru = m_ru;
        this.m_ka = m_ka;
        this.m_mang = m_mang;
        this.m_bing = m_bing;
    }

    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public int getM_price() {
        return m_price;
    }

    public void setM_price(int m_price) {
        this.m_price = m_price;
    }

    public String getM_picture() {
        return m_picture;
    }

    public void setM_picture(String m_picture) {
        this.m_picture = m_picture;
    }

    public String getM_info() {
        return m_info;
    }

    public void setM_info(String m_info) {
        this.m_info = m_info;
    }

    public int getM_type() {
        return m_type;
    }

    public void setM_type(int m_type) {
        this.m_type = m_type;
    }

    public int getM_xin() {
        return m_xin;
    }

    public void setM_xin(int m_xin) {
        this.m_xin = m_xin;
    }

    public int getM_cha() {
        return m_cha;
    }

    public void setM_cha(int m_cha) {
        this.m_cha = m_cha;
    }

    public int getM_ru() {
        return m_ru;
    }

    public void setM_ru(int m_ru) {
        this.m_ru = m_ru;
    }

    public int getM_ka() {
        return m_ka;
    }

    public void setM_ka(int m_ka) {
        this.m_ka = m_ka;
    }

    public int getM_mang() {
        return m_mang;
    }

    public void setM_mang(int m_mang) {
        this.m_mang = m_mang;
    }

    public int getM_bing() {
        return m_bing;
    }

    public void setM_bing(int m_bing) {
        this.m_bing = m_bing;
    }

    @Override
    public String toString() {
        return "Milk{" +
                "m_id=" + m_id +
                ", m_name='" + m_name + '\'' +
                ", m_price=" + m_price +
                ", m_picture='" + m_picture + '\'' +
                ", m_info='" + m_info + '\'' +
                ", m_type=" + m_type +
                ", m_xin=" + m_xin +
                ", m_cha=" + m_cha +
                ", m_ru=" + m_ru +
                ", m_ka=" + m_ka +
                ", m_mang=" + m_mang +
                ", m_bing=" + m_bing +
                '}';
    }

}
