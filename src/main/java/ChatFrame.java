import javax.swing.*;
import java.awt.*;
import java.io.ObjectOutputStream;

public class ChatFrame extends JFrame implements Database {
    private  User u;
    private JPanel MsgPanel;//聊天区
    private ImageIcon Head;//头像
    private JTextField MsgText;//    消息输入框
    private JTextField send_name;//发送名字
    private JPanel MainPanel;//主面板
    private ObjectOutputStream os;
    private ClientThread clientThread;

    public void initimg(){
        Head=new ImageIcon("src\\test\\java\\head2.jpg");
    }//-----------初始化图片------------




//----------------------------------------显示消息----------------------------
private void ShowMsg(String msg,int Sign){
    if(Sign==1) {
        JLabel MsgLabel = new JLabel("我说 ："+msg);
        MsgLabel.setFont(new Font("黑体", Font.BOLD, 14));
        MsgLabel.setSize(50,25);
        JPanel TextPanel = new JPanel();
        TextPanel.setBackground(Color.pink);
        TextPanel.setPreferredSize(new Dimension(370, 60));
        TextPanel.setLayout(new FlowLayout(FlowLayout.LEFT));//左对齐


        if(msg.length()>15){
            MsgLabel.setText("<html><body>"+msg.substring(0,15)+"<br>"+msg.substring(16)+"</body></html>");
            if(msg.length()>30){
                MsgLabel.setText("<html><body>"+msg.substring(0,15)+"<br>"+msg.substring(16,30)+"<br>"+msg.substring(31)+"</body></html>");
            }
        }//判断是否换行（仅限3行）
        TextPanel.add(MsgLabel);
        MsgPanel.add(TextPanel);
    }

    if (Sign ==0) {
        JLabel MsgLabel = new JLabel("<html><body>"+"用户"+u.getUser()+ ":      " +"<br>"+msg+"</body></html>");
        MsgLabel.setFont(new Font("黑体", Font.BOLD, 14));
        JPanel TextPanel = new JPanel();
        TextPanel.setBackground(Color.orange);
        TextPanel.setPreferredSize(new Dimension(370, 60));
        TextPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));//右对齐
        if(msg.length()>15){
            MsgLabel.setText("<html><body>"+u.getUser() + ":" +"<br>"+msg.substring(0,15)+"<br>"+msg.substring(16)+"</body></html>");
            if(msg.length()>30){
                MsgLabel.setText("<html><body>"+u.getUser() + ":" +"<br>"+msg.substring(0,15)+"<br>"+msg.substring(16,30)+"<br>"+msg.substring(31)+"</body></html>");
            }
        }
        TextPanel.add(MsgLabel);
        MsgPanel.add(TextPanel);
    }
    MsgPanel.updateUI();//更新消息记录
}
//-------------------------------------------------------------------------------------------------------------------------------------
public ChatFrame(User u,ClientThread C) throws Exception{
    this.u=u;
    this.clientThread=C;

    os=new ObjectOutputStream(clientThread.getSocket().getOutputStream());

    initimg();
    setTitle("用户: "+u.getUser());
    setSize(680,530);
    setLocationRelativeTo(null);
    JLabel HeadLabel=new JLabel(Head);
    HeadLabel.setBounds(400,220,100,70);
    MainPanel = new JPanel(null);
    add(MainPanel);
    //------------------------消息面板---------------
    MsgPanel=new JPanel();
    MsgPanel.setLayout(new VerticalFlowLayout());
    MainPanel.add(HeadLabel);
    MsgPanel.setBounds(5,5,400,400);
    MsgPanel.setBackground(Color.white);
    MainPanel.add(MsgPanel);
    //--------------------------------------------------

    //------------------------消息输入框----------------
    MsgText=new JTextField();
    MsgText.setBounds(5,420,400,50);
    JScrollPane scrollPane = new JScrollPane(MsgPanel);
    scrollPane.setBounds(5,5,400,400);
    MainPanel.add(scrollPane);
    MainPanel.add(MsgText);
    //---------------------------------------------

    //------------------------发送对方名称----------------
    JLabel SendName =new JLabel("发送对方名称：");
    SendName.setBounds(440,330,150,10);
    MainPanel.add(SendName);
    send_name=new JTextField();
    send_name.setBounds(440,355,150,30);
    MainPanel.add(send_name);

    //---------------------------------------------------

    //---------------------发送按钮--------------
    JButton Send =new JButton("发送");
    Send.setBounds(470,430,120,40);
    Send.addActionListener(e -> {
        try {
            SendMsg();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    });
    MainPanel.add(Send);
    //------------------------------------------
    setVisible(true);
}

public void SendMsg() throws Exception {
    message msg=new message();
    msg.setType("#");
    msg.setUser(u.getUser());
    msg.setTouser(send_name.getText());
    msg.setMsg(MsgText.getText());
    os.writeObject(msg);
    ShowMsg(msg.getMsg(),1);

}
}


