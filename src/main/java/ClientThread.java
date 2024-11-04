import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread extends Thread {

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
                ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
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


