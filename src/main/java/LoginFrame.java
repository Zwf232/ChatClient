

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class LoginFrame extends JFrame  implements Database {
    private User user = new User();// 当前用户
    private Socket socket;// 与服务器的连接

    public LoginFrame() {
        //--------------------------------标题---------------------------------------
        try {

            socket = new Socket("127.0.0.1", 8888);

            this.setTitle("聊天软件");
            this.setSize(600, 400);
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel jp = new JPanel(null);
            this.add(jp);
            JLabel j = new JLabel("聊天软件");
            j.setFont(new Font("微软雅黑", Font.BOLD, 30));
            j.setBounds(230, 25, 300, 50);
            jp.add(j);
//------------------------------------ //创建用户文本框-----------------------------------------
            JLabel j1 = new JLabel("用户名：");
            j1.setFont(new Font("微软雅黑", Font.BOLD, 17));
            j1.setBounds(150, 100, 100, 50);
            jp.add(j1);
            JTextField t1 = new JTextField();
            t1.setBounds(225, 110, 200, 30);
            jp.add(t1);
            //监听t1的输入内容长度并提示
            t1.addFocusListener(new FocusListener() {
                @Override
                public void focusLost(FocusEvent e) {
                    if (t1.getText().length() > 8) {
                        t1.setText("");
                        JOptionPane.showMessageDialog(null, "用户名过长！请重新输入");
                        JLabel tip1 = new JLabel("请输入8位及以下的用户名");
                        tip1.setFont(new Font("微软雅黑", Font.BOLD, 10));
                        tip1.setForeground(Color.RED);
                        tip1.setBounds(225, 130, 300, 30);
                        jp.add(tip1);
                        setVisible(false);
                        setVisible(true);
                    }
                }

                @Override
                public void focusGained(FocusEvent e) {

                }
            });

//------------------------------------ //创建用户密码文本框-----------------------------------------
            JLabel j2 = new JLabel("密码：");
            j2.setFont(new Font("微软雅黑", Font.BOLD, 17));
            j2.setBounds(150, 160, 100, 50);
            jp.add(j2);
            JTextField t2 = new JTextField();
            t2.setBounds(225, 170, 200, 30);
            jp.add(t2);
            //监听t2的输入内容长度并提示
            t2.addFocusListener(new FocusListener() {
                @Override
                public void focusLost(FocusEvent e) {
                    JLabel tip2 = new JLabel("请输入16位及以下的密码");
                    if (t2.getText().length() > 16) {
                        t2.setText("");
                        JOptionPane.showMessageDialog(null, "密码过长！请重新输入");
                        tip2.setFont(new Font("微软雅黑", Font.BOLD, 10));
                        tip2.setForeground(Color.RED);
                        tip2.setBounds(225, 190, 300, 30);
                        jp.add(tip2);
                        setVisible(false);
                        setVisible(true);
                    }
                }

                @Override
                public void focusGained(FocusEvent e) {

                }
            });


            //----------------------------------------------注册，登录按钮-----------------------------------------
            JButton btn1 = new JButton("注册");
            btn1.setBounds(175, 230, 100, 30);
            JButton btn2 = new JButton("登录");
            btn2.setBounds(325, 230, 100, 30);
            JButton btn3 = new JButton("修改密码");
            btn3.setBounds(250, 270, 100, 30);
            jp.add(btn1);
            jp.add(btn2);
            jp.add(btn3);
            this.setVisible(true);//显示窗口

            //注册按钮功能
            btn1.addActionListener(e -> {

            });
            //登录按钮功能
            btn2.addActionListener(e -> {
                message msg = new message();
                msg.setUser(t1.getText());//用户名
                msg.setMsg(t2.getText());//密码
                msg.setType("登录请求");
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(msg);
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    message msg1 = (message) ois.readObject();
                    if (msg1.getMsg().equals("登录成功")) {
                        user.setUser(t1.getText());
                        user.setPassword(t2.getText());
                        JOptionPane.showMessageDialog(null, "登录成功！");
                        this.setVisible(false);
                        ClientThread clientThread = new ClientThread(user.getUser(), socket);

                        new MainFrame(user, clientThread);
                    } else {
                        JOptionPane.showMessageDialog(null, "登录失败！");
                    }
                    oos.flush();

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
            //修改密码按钮功能
            btn3.addActionListener(e -> {


            });
        } catch (Exception e) {
        }
    }


    public static void main(String[] args) {
        client c = new client();

    }
}

