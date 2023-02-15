package com.mycompany.pos.basic.object;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public  class ConnectDb {
	private  final static String username = "root";
	private  final static String password = "";
	public  static Connection con;
	private static String dbURL;
	
	public ConnectDb()  {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			dbURL = "jdbc:mysql://localhost/pos_crud?characterEncoding=utf-8";
			con = DriverManager.getConnection(dbURL,getUsername(),getPassword());
		} catch (ClassNotFoundException e) {
			System.out.println("ค้นหา Class ไม่เจอ" + e.getMessage());
		} catch (SQLException e) {
			System.out.println("คำสั่ง SQL ไม่ถูกต้อง" + e.getMessage());
		}
		
	}

	public static  String getUsername() {
		return username;
	}

	public static String getPassword() {
		return password;
	}

	public static Connection getCon() {
		return con;
	}

	public  static void setCon(Connection con) {
		con = con;
	}

	public String getDbURL() {
		return dbURL;
	}

	public static void setDbURL(String dbURL) {
		dbURL = dbURL;
	}
	
	public static void closeCon() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
    public int Register(UserOb usr){
        int status = 0;
          
        String username = usr.getUsername();
        String password = usr.getPassword();
        String fname = usr.getFname();
        String lname = usr.getLname();
        String tel = usr.getTel();
               
 
           try {
               String sql = "INSERT INTO users (username_user,password_user,fname_user,lname_user,tel_user)";
               sql += "SELECT ?,?,?,?,? ";
               sql += "WHERE NOT EXISTS (SELECT username_user FROM users WHERE username_user = ?)";
               
               
               
                System.out.println(sql + "....");
		PreparedStatement stmt = this.getCon().prepareStatement(sql);
                stmt.setString(1,username);
                stmt.setString(2,password);
                stmt.setString(3,fname);                
                stmt.setString(4,lname);
                stmt.setString(5,tel);
                stmt.setString(6,username);
                System.out.println(stmt);
		int row = stmt.executeUpdate();
		System.out.println(row + " row(s) inserted");
		this.closeCon();
                status = row;
           } catch (SQLException ex) {
               Logger.getLogger(ConnectDb.class.getName()).log(Level.SEVERE, null, ex);
           }
           return status;
    }
    
    public int Login(UserOb usr){
        int id = 0;
        
        
        String username = usr.getUsername();
        String password = usr.getPassword();
        
        
        String sql = "SELECT id_user FROM users WHERE username_user = ? AND password_user = ?";
        
            try {
                PreparedStatement prest = this.getCon().prepareStatement(sql);
                prest.setString(1,username);
                prest.setString(2,password);
                System.out.println("SQL : " + prest);
                ResultSet rt = prest.executeQuery();
                if(rt.next()){
                    id = rt.getInt("id_user");
                    UserOb usrs = new UserOb(id) ;
                }
                this.closeCon();
            } catch (SQLException ex) {
                Logger.getLogger(ConnectDb.class.getName()).log(Level.SEVERE, null, ex);
            }

        return id;
    }
    
    
    public boolean Edit_Profile(UserOb usr){
        boolean status = false;
        String fname = usr.getFname();
        String lname = usr.getLname();
        String tel = usr.getTel();
        int id = usr.getId();
        
        String sql = "UPDATE users SET fname_user = ?,lname_user = ? , tel_user = ? WHERE id_user = ?";
        
        PreparedStatement pre;
            try {
                pre = this.getCon().prepareStatement(sql);
                pre.setString(1, fname);
                pre.setString(2,lname);
                pre.setString(3,tel);
                pre.setInt(4,id);
                System.out.println("SQL : " + pre);
                int row = pre.executeUpdate();
                if(row != 0){
                    status = true;
                }else{
                    status = false;
                }
 
            } catch (SQLException ex) {
                Logger.getLogger(ConnectDb.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return status;
    }
    
    public UserOb show_Myself(){
            UserOb usr = null;
            int id = usr.getId();
            
            String sql = "SELECT * FROM users WHERE id_user = ?";
            
            PreparedStatement pre;
            try {
                pre = this.getCon().prepareStatement(sql);
                pre.setInt(1,id);
                
                ResultSet rs = pre.executeQuery();
                
                if(rs.next()){
                    String fname = rs.getString("fname_user");
                    String lname = rs.getString("lname_user");
                    String tel = rs.getString("tel_user");
                    usr = new UserOb(fname,lname,tel);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ConnectDb.class.getName()).log(Level.SEVERE, null, ex);
            }
        return usr;
    }
    
    
    public boolean create_Type(TypeOb type){
        boolean status = false;
        String name = type.getName();
        String sql = "INSERT INTO types (name_type) ";
        sql += "SELECT ?";
        sql += "WHERE NOT EXISTS (SELECT name_type FROM types WHERE name_type = ?)";
        
        
        PreparedStatement pre;
        
            try {
                pre = this.getCon().prepareStatement(sql);
                pre.setString(1,name);                
                pre.setString(2,name);
                System.out.println("SQL : " + pre);
                
                int row = pre.executeUpdate();
                
                if(row != 0 ){
                    System.out.println("Insert : " + row );
                    status = true;
                }else{
                    status = false;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ConnectDb.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        return status;
    }
    
    
    public DefaultTableModel getAllType(){
        Vector<String>  columnsName = new Vector<String>();
        String header[] = {"ID","Name"};
        
        DefaultTableModel model = new DefaultTableModel(columnsName,0);
        
        model.setColumnIdentifiers(header);
        
        String sql = "SELECT * FROM types";
        
            try {
                Statement stmt = this.getCon().createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                ResultSetMetaData rsmd = rs.getMetaData();
                
                int numofcols = rsmd.getColumnCount();
                
                
                
                while(rs.next()){
                    Vector<String> row = new Vector<String>();
                    for(int i = 1; i <= numofcols; i++){
                        row.add(rs.getString(i));
                    }
                    model.addRow(row);
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(ConnectDb.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        return model;
    }
    
    
    public boolean create_Product(ProductOb pro){
        boolean status = false;
        String name = pro.getName();
        double price = pro.getPrice();
        int id_type = pro.getType().getId();
        
        
        String sql = "INSERT INTO products (name_pro,price_pro,type_pro) ";
        sql += "SELECT ?,?,? ";
        sql += "WHERE NOT EXISTS (SELECT * FROM products WHERE name_pro = ? )";
        
        PreparedStatement pre;
            try {
                pre = this.getCon().prepareStatement(sql);
                pre.setString(1,name);
                pre.setDouble(2,price);
                pre.setInt(3, id_type);
                pre.setString(4, name);
                
                
                System.out.println("SQL : " + pre);
                
                int row = pre.executeUpdate();
                
                if(row != 0){
                    status = true;
                }else{
                    status = false;
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(ConnectDb.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        return status;
    }
    
    public TypeOb[] getType(){
        ArrayList<TypeOb> list = new ArrayList<TypeOb>();
        
        TypeOb typ[] = null;
        
        
//        String sql = "SELECT id_pro,name_pro,price_pro,id_type,name_type FROM products,types WHERE type_pro = id_type";
          String sql = "SELECT * FROM types";
          
        
         PreparedStatement pre;
         
            try {
                pre = this.getCon().prepareStatement(sql);
                ResultSet rs = pre.executeQuery();
                
                while(rs.next()){
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    
                    list.add(new TypeOb(id,name));
                }
                typ = new TypeOb[list.size()];
                list.toArray(typ);
            } catch (SQLException ex) {
                Logger.getLogger(ConnectDb.class.getName()).log(Level.SEVERE, null, ex);
            }
  
        
        return typ;
    }
    
    
    public DefaultTableModel getAllProduct(){
        Vector<String> colName = new Vector<String>();
        String header[] = {"ID","NAME","PRICE","TYPE"};
        
        
        DefaultTableModel model = new DefaultTableModel(colName,0);
        model.setColumnIdentifiers(header);
        String sql = "SELECT id_pro,name_pro,price_pro,name_type FROM products,types WHERE type_pro = id_type";
        
        Statement stmt; 
            try {
                stmt = this.getCon().createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                ResultSetMetaData rsmd = rs.getMetaData();
                
                int col = rsmd.getColumnCount();
                
                while(rs.next()){
                    Vector<String> row = new Vector<String>(); 
                    
                    for(int i = 1; i <= col; i++){
                        row.add(rs.getString(i));
                    }
                    model.addRow(row);      
                }
            } catch (SQLException ex) {
                Logger.getLogger(ConnectDb.class.getName()).log(Level.SEVERE, null, ex);
            }
       
        return model;
    }
	
}