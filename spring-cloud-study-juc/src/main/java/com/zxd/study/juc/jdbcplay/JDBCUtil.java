package com.zxd.study.juc.jdbcplay;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

public class JDBCUtil {
    static String driver = "com.mysql.cj.jdbc.Driver";
//  static String url="jdbc:mysql://rm-bp1r0boa36pfyv6j3so.mysql.rds.aliyuncs.com:53306/chiatest?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8";
//  static String url="jdbc:mysql://192.168.31.38:3308/chiatest?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8";
//    static String url="jdbc:mysql://localhost:3306/chiatest?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8";
    static String url="jdbc:mysql://rm-bp1y689p63k61xjdfyo.mysql.rds.aliyuncs.com:3306/chiatest?useUnicode=true&characterEncoding=UTF-8";


//    static String user="xxx";
//    static String pwd = "cxxx";
//    static String pwd = "xx";
    static String user="xx";
    static String pwd = "xxx";


    static int poolSize = 1000;

    static Vector<Connection> pools = new Vector<Connection>();

    public static Connection getDBConnection(){
        try {
            //1.加载驱动
            Class.forName(driver);
            //2.取得数据库连接
            Connection conn =  DriverManager.getConnection(url, user, pwd);
            System.out.println("====jdbc连接建立成功===>: " + conn);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    static {
        int i = 1;
        while(i<=poolSize){
            pools.add(getDBConnection());
            System.out.println("====jdbc连接池大小===>: " + i);
            i++;
        }
    }

    public static synchronized Connection getPool(){
        if(pools != null && pools.size() > 0){
            int last_ind = pools.size() -1;
            return pools.remove(last_ind);
        }else{
            return getDBConnection();
        }
    }

    public static int insert(String sql,Object[] params){
        Connection conn = getPool();
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            for(int i=0;i<params.length;i++){
                pstmt.setObject(i+1, params[i]);
            }
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(pstmt != null){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(conn != null){
//					conn.close();
                pools.add(conn);
            }
        }
        return 0;
    }


}
