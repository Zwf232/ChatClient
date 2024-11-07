import javax.swing.*;
import java.awt.*;
import java.sql.*;

public interface Database {
    //输入数据库连接账号密码
    String DB_URL = "";
    String USER = "";
    String PASS = "";

//---------------------------------------登录功能-------------------------------------
    public default  boolean Login(String user,String password) throws Exception {
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement p0 = conn.prepareStatement("Select user,password,sex,age,telephone,address from account where user=? and password = ?");
        p0.setString(1, user);
        p0.setString(2, password);
        p0.executeQuery();
        if (p0.getResultSet().next()) {
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
    }//插入聊天记录

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
    }//查询聊天记录

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
    }//添加好友
    public default message SearchFriend(String user) throws Exception {
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement p = conn.prepareStatement("select * from friends where user=? ");
        p.setString(1, user);
        message msg = new message();
        msg.setMsg("");
        p.executeQuery();
        while (p.getResultSet().next()) {

            msg.setMsg(msg.getMsg() + "用户：" + p.getResultSet().getString("name") + "的性别：" + p.getResultSet().getString("sex") + "，年龄：" + p.getResultSet().getString("age") + "，电话：" + p.getResultSet().getString("telephone") + "，地址：" + p.getResultSet().getString("address") + "\n");

    }
        return msg;
    }//查询好友
    }

