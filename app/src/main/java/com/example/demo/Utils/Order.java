package com.example.demo.Utils;

/**
 * @author: JJY
 * @date: 2021/6/9 18:39
 */
public class Order {
    private Integer o_id;//订单ID
    private Integer u_id;//用户ID
    private Integer m_id;//奶茶ID
    private String o_time;//下单时间
    private String o_no;//订单编号
    private Integer o_price;//单杯奶茶价格
    private String o_type;//奶茶规格
    private Integer o_count;//这种奶茶的杯数

    public Order() {
    }

    public Order(Integer o_id, Integer u_id, Integer m_id, String o_time, String o_no, Integer o_price, String o_type, Integer o_count) {
        this.o_id = o_id;
        this.u_id = u_id;
        this.m_id = m_id;
        this.o_time = o_time;
        this.o_no = o_no;
        this.o_price = o_price;
        this.o_type = o_type;
        this.o_count = o_count;
    }

    public Integer getO_id() {
        return o_id;
    }

    public void setO_id(Integer o_id) {
        this.o_id = o_id;
    }

    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
        this.u_id = u_id;
    }

    public Integer getM_id() {
        return m_id;
    }

    public void setM_id(Integer m_id) {
        this.m_id = m_id;
    }

    public String getO_time() {
        return o_time;
    }

    public void setO_time(String o_time) {
        this.o_time = o_time;
    }

    public String getO_no() {
        return o_no;
    }

    public void setO_no(String o_no) {
        this.o_no = o_no;
    }

    public Integer getO_price() {
        return o_price;
    }

    public void setO_price(Integer o_price) {
        this.o_price = o_price;
    }

    public String getO_type() {
        return o_type;
    }

    public void setO_type(String o_type) {
        this.o_type = o_type;
    }

    public Integer getO_count() {
        return o_count;
    }

    public void setO_count(Integer o_count) {
        this.o_count = o_count;
    }
}
