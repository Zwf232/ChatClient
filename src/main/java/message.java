import java.io.Serializable;

public class message implements Serializable {
    private static final long serialVersionUID = 1L;
    private String user;//发送者
    private String msg;//消息
    private String Touser;//接收者
    private  String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public message(){}
    public message(String user, String msg, String Touser){
        this.Touser = Touser;
        this.user = user;
        this.msg = msg;
    }

    public String getTouser() {
        return Touser;
    }

    public void setTouser(String touser) {
        Touser = touser;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUser()  {
        return user;
    }

    public void setUser(String u) {
        this.user = u;
    }


}
