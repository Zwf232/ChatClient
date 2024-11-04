import javax.swing.*;
import java.awt.*;
import java.sql.*;

public interface Database {
    String DB_URL = "jdbc:mysql://gz-cynosdbmysql-grp-4mzg6teb.sql.tencentcdb.com:29919/test";
    String USER = "root";
    String PASS = "1161822807Zwf";
    //--------------------------------------注册功能-----------------------------------------
    public default boolean Sign_Up(String user, String password)throws Exception {
        boolean flag = false;
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://gz-cynosdbmysql-grp-4mzg6teb.sql.tencentcdb.com:29919/test", "root", "1161822807Zwf");
                PreparedStatement ps = conn.prepareStatement("insert into account(user,password) values (?,?)");
                PreparedStatement ps1=conn.prepareStatement("insert into r_account(user,password) values (?,?)");
                ps.setString(1, user);
                ps.setString(2, password);
                ps1.setString(1, user);
                ps1.setString(2, password);
                int result = ps.executeUpdate();
                ps1.executeUpdate();
                if (result > 0) {
                    flag=true;
                    System.out.println("注册成功！");
                } else {
                    flag=false;
                    System.out.println("注册失败！");
                }
                ps1.close();
                ps.close();
                conn.close();
            } catch (Exception e) {
                System.out.println("数据库连接失败！" + e.getMessage());
                e.printStackTrace();

        }
            return flag;
    }
//---------------------------------------登录功能-------------------------------------
    public default  boolean Login(String user,String password) throws Exception {
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement p0 = conn.prepareStatement("Select user,password,sex,age,telephone,address from account where user=? and password = ?");
        p0.setString(1, user);
        p0.setString(2, password);
        p0.executeQuery();
        if (p0.getResultSet().next()) {
//            User u = new User();
//            u.setUser(p0.getResultSet().getString("user"));
//            u.setPassword(p0.getResultSet().getString("password"));
//            u.setAge(p0.getResultSet().getString("age"));
//            u.setSex(p0.getResultSet().getString("sex"));
//            u.setTelephone(p0.getResultSet().getString("telephone"));
//            u.setAddress(p0.getResultSet().getString("address"));
            p0.close();
            conn.close();
            return true;
        } else {
            p0.close();
            conn.close();
            return false;
        }
    }
        //-----------------------------------------聊天记录功能--------------------------------
    public default void  insertMsg(message msg)throws Exception{
         Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
         PreparedStatement p=conn.prepareStatement("insert into chat_history(user,chat,touser) values (?,?,?)");
         p.setString(1,msg.getUser());
         p.setString(2,msg.getMsg());
         p.setString(3,msg.getTouser());
         p.executeUpdate();
         p.close();
         conn.close();
    }

    public default  message searchMsg(String user) throws Exception{
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        message msg=new message();
        PreparedStatement p=conn.prepareStatement("select user,chat,touser from chat_history where user=? or  touser=?");
        p.setString(1,user);
        p.setString(2,user);
        p.executeQuery();
        while (p.getResultSet().next()){
            if(msg.getMsg() != null){
             msg.setMsg(msg.getMsg()+"\n"+"用户："+p.getResultSet().getString("user")+"对"+p.getResultSet().getString("touser")+"说："+p.getResultSet().getString("chat"));
        }else {
                msg.setMsg("用户："+p.getResultSet().getString("user")+"对"+p.getResultSet().getString("touser")+"说："+p.getResultSet().getString("chat"));

            }
        }
        p.close();
        conn.close();
        return msg;
    }

    public default boolean  AddFriend(String user,String friend_name) throws Exception{
        boolean sign=false;
        try{
            Connection conn=DriverManager.getConnection(DB_URL,USER,PASS);
            PreparedStatement p0=conn.prepareStatement("select * from r_account where user = ?");
            PreparedStatement p= conn.prepareStatement("insert into friends values (?,?,?,?,?,?)");
            p0.setString(1,friend_name);
            p0.executeQuery();
            ResultSet rs=p0.getResultSet();
            if(rs.next()){
                String sex=rs.getString("sex");
                String age=rs.getString("age");
                String tel=rs.getString("telephone");
                String address=rs.getString("address");
                p.setString(1,user);
                p.setString(2,friend_name);
                p.setString(3,sex);
                p.setString(4,age);
                p.setString(5,tel);
                p.setString(6,address);
                p.executeUpdate();
                if(p.getUpdateCount()>0){
                    sign=true;
                    p.close();
                }else{
                    sign =false;
                    p.close();
                }
            }
            p0.close();
            conn.close();
        }catch (Exception e){}
        return sign;
    }
    }

