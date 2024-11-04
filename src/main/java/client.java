import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class client implements Database{
      private User user=new User();// 当前用户
      private Socket socket;// 与服务器的连接
      public client (User u,Socket socket){
                this.user=u;
                this.socket=socket;
        }
    public client(){
        try{

            // 建立连接
            socket = new Socket("127.0.0.1", 8888);
            //登录信息
            Scanner sc=new Scanner(System.in);
            System.out.println("请输入用户名");
            String user0=sc.nextLine();
            System.out.println("请输入密码");
            String password=sc.nextLine();

            message msg0=new message();
            msg0.setType("登录请求");
            msg0.setUser(user0);
            msg0.setMsg(password);

            // 发送信息
            ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(msg0);
            user.setUser(user0);
            user.setPassword(password);
            //接受信息

            ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
            message msg=(message)ois.readObject();
            oos.flush();
            if(msg.getMsg().equals("登录成功")){
                //新建立与服务器的连接套接字类型
                ClientThread clientThread=new ClientThread(user.getUser(),socket);
                clientThread.start();
                System.out.println("登录成功");
            ObjectOutputStream ooss=new ObjectOutputStream(clientThread.getSocket().getOutputStream());
                Scanner sc0 = new Scanner(System.in);
                while(true) {
                    System.out.println("---------------功能菜单-------------");
                    System.out.println("1.发送消息");
                    System.out.println("2.查看在线列表");
                    System.out.println("3.查询聊天记录");
                    System.out.println("4.添加好友");
                    System.out.println("5.查询好友");
                    System.out.println("-------------------------------------");
                    System.out.println("请输入你的选择");
                    int i = sc0.nextInt();

                    switch (i) {
                        case 1:
                                message msg1=new message();
                                System.out.println("请输入要发送的消息");
                                Scanner sc2=new Scanner(System.in);
                                msg1.setType("#");
                                msg1.setMsg(sc2.nextLine());
                                System.out.println("请输入要发送的用户名");
                                msg1.setTouser(sc2.nextLine());
                                msg1.setUser(user.getUser());
                                ooss.writeObject(msg1);
                                ooss.flush();
                            System.out.println("发送成功");
                            this.insertMsg(msg1);
                                break;
                        case 2:
                            message msg2 = new message();
                            msg2.setUser(user.getUser());
                            msg2.setType("查看在线列表");
                            ooss.writeObject(msg2);
                            ooss.flush();
                            break;
                        case 3:
                            message msg4 = new message();
                            msg4.setType("查询聊天记录");
                            msg4.setUser(user.getUser());
                            ooss.writeObject(msg4);
                            break;
                        case 4:
                            message msg5=new message();
                            msg5.setType("添加好友");
                            msg5.setUser(user.getUser());
                            System.out.println("请输入好友用户名");
                            Scanner sc3=new Scanner(System.in);
                            String name=sc3.nextLine();
                            msg5.setTouser(name);
                            ooss.writeObject(msg5);
                            break;
                        case 5:

                        default:
                            message msg3 = new message();
                            msg3.setType("输入错误");
                            ooss.writeObject(msg3);
                            ooss.flush();
                            break;
                    }
                }
            }else{
                System.out.println("登录失败");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }}

        public User getUser() {
                return user;
        }

        public void setUser(User user) {
                this.user = user;
        }

        public Socket getSocket() {
                return socket;
        }

        public void setSocket(Socket socket) {
                this.socket = socket;
        }







        class ClientThread extends Thread{
            private Socket socket;
            private String user;
            public ClientThread(){}

            public Socket getSocket() {
                return socket;
            }

            public void setSocket(Socket socket) {
                this.socket = socket;
            }

            public  String getUser() {
                return user;
            }

            public void setUser(String user) {
                this.user = user;
            }

            public ClientThread(String user, Socket socket) {
                this.user = user;
                this.socket = socket;
            }

            @Override
            public void run() {
                try{
//                    ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
//                    Scanner sc1=new Scanner(System.in);
                    while(true){
                            message msg = (message) ois.readObject();
                            System.out.println(msg.getMsg());

                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        public static void main(String[] args) {
                client c=new client();

        }




}
