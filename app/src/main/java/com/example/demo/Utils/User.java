package com.example.demo.Utils;

/**
 * @author: JJY
 * @date: 2021/6/8 12:10
 */
public class User {

    private Integer u_id;//用户ID
    private String username;//用户名
    private String password;//密码
    private String nickname;//昵称
    private String phone;//手机号

    public User() {
    }

    public User(Integer u_id, String username, String password, String nickname, String phone) {
        this.u_id = u_id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
    }

    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
        this.u_id = u_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
