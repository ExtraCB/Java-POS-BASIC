/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pos.basic.object;

/**
 *
 * @author tdev
 */
public class UserOb {
    private String username;
    private String password;
    private String fname;
    private String lname;
    private String tel;
    private static int id;

    public UserOb(String username, String password, String fname, String lname, String tel) {
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.tel = tel;
    }
    
    public UserOb(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public UserOb(String fname, String lname, String tel) {
        this.fname = fname;
        this.lname = lname;
        this.tel = tel;
    }
    
    
    public UserOb(int id){
        setId(id);
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

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        UserOb.id = id;
    }
    
    
    
    
}
