import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class server implements Database{
    private ServerSocket serverSocket;//储存一个服务器套接字
    private List<ServerThread> serverList =new ArrayList<>();//储存所有连接的客户端

    public void startServer() throws Exception{
        try{
            serverSocket = new ServerSocket(8888);//客户端需要连接的端口号
            while(true){
                Socket socket = serverSocket.accept();//等待客户端连接....
                 System.out.println("有客户端尝试连接.....");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());//读取客户端发送的信息
                message msg0 = (message) ois.readObject();//接收客户端发送的信息
                message msg=new message();//返回客户端的响应信息
                ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());


                 if(msg0.getType().equals("登录请求") ) {
                    if (Login(msg0.getUser(), msg0.getMsg())) {//数据库操作验证登录信息是否正确
                        msg.setMsg("登录成功");
                        oos.writeObject(msg);
                        oos.flush();
                        System.out.println("账号：" + msg0.getUser() + " 密码：" + msg0.getMsg() + "   登录成功！");
                        ServerThread st = new ServerThread(msg0.getUser(), socket);//验证成功后，创建一个线程，将客户端信息储存在服务器端，并开启线程进行持续通信
                        st.start();
                        serverList.add(st);
                    } else if (!Login(msg0.getUser(), msg0.getMsg())) {
                        msg.setMsg("登录失败");
                        oos.writeObject(msg);
                        System.out.println("服务器验证账号密码失败！");
                    }
                }
            }
        }catch (Exception e){
            System.out.println("服务器启动失败！");
        }
    }


    class ServerThread extends Thread implements Database {
        private Socket socket;//储存客户端套接字
        private String user;//储存客户端用户名
        private  ObjectInputStream ois ;//储存客户端输入流，方便读取客户端发送的信息
        private ObjectOutputStream oos;//储存客户端输出流，方便向客户端发送信息

        public ObjectInputStream getOis() {
            return ois;
        }

        public void setOis(ObjectInputStream ois) {
            this.ois = ois;
        }

        public ObjectOutputStream getOos() {
            return oos;
        }

        public void setOos(ObjectOutputStream oos) {
            this.oos = oos;
        }

        public ServerThread(String user, Socket socket)
        {
            this.socket = socket;
            this.user = user;
        }
        public ServerThread(){}

        public Socket getSocket() {
            return socket;
        }

        public void setSocket(Socket socket) {
            this.socket = socket;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }
        @Override
        public void run(){//线程运行
            try{
                ois = new ObjectInputStream(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
                while(true) {
                    //持续读取客户端发送的信息并进行信息处理返回给客户端
                    message msg=(message) ois.readObject();
                    System.out.println("收到请求："+msg.getType());
//-------------------------------------客户端请求异常------------------------------------
                    if(msg.getType().equals("输入错误")){
                        System.out.println("客户端输入错误");
                        oos.writeObject(msg);
                    }
//-------------------------------------------私聊功能----------------------------------------
                    if(msg.getType().equals("#")){
                        for(ServerThread sts:serverList){
                            if(sts.getUser().equals(msg.getTouser())){
                                ObjectOutputStream oos1=sts.getOos();
                                msg.setMsg("#"+msg.getMsg());
                                msg.setUser(msg.getUser());
                                oos1.writeObject(msg);
                            }
                        }
                        System.out.println("用户"+msg.getUser()+"发送消息给"+msg.getTouser());
                    }
//---------------------------------功能查询在线列表---------------------------------------
                    if(msg.getType().equals("查看在线列表")){
                        msg.setMsg("当前在线用户："+serverList.size());
                        for(ServerThread sts:serverList){//遍历serverList集合里的所有线程来判断在线人数
                            msg.setMsg(msg.getMsg()+"\n"+"用户："+sts.getUser());
                        }
                        oos.writeObject(msg);
                        oos.flush();
                        System.out.println("发送客户端"+ user+"在线数据");
                    }
//---------------------------------------------查询聊天记录功能--------------------------------
                    if(msg.getType().equals("查询聊天记录")){
                            message msg1=this.searchMsg(msg.getUser());
                            oos.writeObject(msg1);
                    }
//-----------------------------------------添加好友----------------------------------
                    if(msg.getType().equals("添加好友")) {
                        message msg2 = new message();
                        if (AddFriend(msg.getUser(), msg.getTouser())) {
                            msg2.setMsg("添加好友" + msg.getTouser() + "成功");
                            oos.writeObject(msg2);
                            System.out.println("用户：" + msg.getUser() + "添加好友" + msg.getTouser() + "成功");
                        } else {
                            msg2.setMsg("添加好友失败");
                            oos.writeObject(msg2);
                        }
                    }
                    //------------------------------查询好友--------------------------------------
                    if(msg.getType().equals("查询好友")){
                        message msg2=SearchFriend(msg.getUser());
                        oos.writeObject(msg2);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                serverList.remove(this);//客户端退出，将线程移除集合并遍历剩下的线程数统计在线人数
                System.out.println("有人登出，当前在线人数："+serverList.size());
                for(ServerThread sts:serverList) {
                    System.out.println("当前剩下用户：" + sts.getUser());
                }
                System.out.println(user+"的客户端已经退出");//输出退出用户名称

            }
        }
    }
    //启动服务器
    public static void main(String[] args)throws Exception {
         server s=new server();
         s.startServer();
    }
}
