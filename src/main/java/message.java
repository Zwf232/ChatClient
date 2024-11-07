import java.io.Serializable;

public class message implements Serializable {
    private static final long serialVersionUID = 1L;//序列化
    private String user;//发送者
    private String msg;//消息
    private String Touser;//接收者
    private  String type;//消息类型
    public message(){}

    public void setType(String type) {
        this.type = type;
    }
    public void setTouser(String touser) {
        Touser = touser;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public void setUser(String u) {
        this.user = u;
    }


    public String getType() {
        return type;
    }
    public String getTouser() {
        return Touser;
    }
    public String getMsg() {
        return msg;
    }
    public String getUser()  {
        return user;
    }


}
