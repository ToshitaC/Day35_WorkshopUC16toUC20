package com.blz;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBC {
    static String url = "jdbc:mysql://localhost:3306/AddressBook_System1&useSSL=false";
    static String userName = "root";
    static String password = "Toshita@17";
    private static Connection con = null;

    public static Connection getConnection()
    {
        try {
            //Driver Loading
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Making the connection to Database
            con = DriverManager.getConnection(url,userName,password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
