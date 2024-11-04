import javax.swing.*;
import java.awt.*;
import java.net.Socket;


public class MainFrame extends JFrame implements Database{
    private User u;//登录用户
    private JSplitPane jsp;//分割左右面板
    private JPanel LeftPanel;//左画布
    private JPanel RightPanel;//右画布
    private ImageIcon Head;//头像
    private ClientThread clientThread;

    public void initimg(){
        Head=new ImageIcon("src\\test\\java\\head2.jpg");
    }//初始化图片

    public MainFrame(User u,ClientThread C)throws Exception{
        clientThread=C;
        clientThread.start();
        initimg();//初始化图片
        this.u=u;
        this.setTitle("主界面");
        this.setSize(400,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        jsp=new JSplitPane();
        jsp.setDividerLocation(150);
        jsp.setEnabled(false);
        jsp.setDividerSize(10);
        this.add(jsp);



        //-----------------------------------------左面板------------------------------------------------------------------------
        jsp.setLeftComponent(LeftPanel =new JPanel(null));


        //----------------------------------------退出按钮--------------------
        JButton quit =new JButton("退出");
        quit.setBounds(5,5,70,30);
        quit.addActionListener(e -> {
            this.setVisible(false);
            new LoginFrame();
        });
       LeftPanel.add(quit);
       //--------------------------------------------------------------------------------



        //----------------------------------展示用户-----------------------------------------
        JLabel UserName =new JLabel("当前用户："+u.getUser());
        UserName.setBounds(5,60,200,30);
        UserName.setFont(new Font("黑体",Font.BOLD,16));
        LeftPanel.add(UserName);
        JLabel HeadLabel=new JLabel(Head);
        HeadLabel.setBounds(65,0,100,70);
        LeftPanel.add(HeadLabel);
        //----------------------------------------------------------------------------------------------------



        //-----------------------------------------修改用户信息-----------------------------------------
        JButton ModifyUser=new JButton("修改用户信息");
        ModifyUser.setBounds(10,100,130,30);
        ModifyUser.addActionListener(e -> {
            this.setVisible(false);
//            new ModifyUser(u);
        });
        LeftPanel.add(ModifyUser);
        //----------------------------------------------------------------------------------------------------



        //-----------------------------------------新建聊天-----------------------------------------
        JButton Chat=new JButton("新建聊天");
        Chat.setBounds(10,140,130,30);
        Chat.addActionListener(e -> {
            try {
                new ChatFrame(u,clientThread);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        LeftPanel.add(Chat);
        //----------------------------------------------------------------------------------------------------



        //----------------------------------------------------------------左面板尾----------------------------------------------------------------




        //---------------------------------------------------------------------------右面板------------------------------------------------------------------------
        RightPanel=new JPanel(null);
        jsp.setRightComponent(RightPanel);

//-----------------------------------------添加好友------------------------------------------
        JTextField AddFriendName=new JTextField("");
        AddFriendName.setBounds(30,20,170,30);
        RightPanel.add(AddFriendName);
        JButton AddFriend=new JButton("添加好友");
        AddFriend.setBounds(40,60,130,30);
        AddFriend.addActionListener(e -> {
            if(!AddFriendName.getText().equals("")) {
                String name = AddFriendName.getText();
                try {
//                    AddFriend(u, name);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }else JOptionPane.showMessageDialog(null,"请输入好友用户名！");
        });

        RightPanel.add(AddFriend);
        //----------------------------------------------------------------------------------------------------



        //------------------------------------------------删除好友--------------------------------
        JButton DeleteFriend=new JButton("删除好友");
        DeleteFriend.setBounds(40,100,130,30);
        DeleteFriend.addActionListener(e -> {
            if(!AddFriendName.getText().equals("")) {
                String name = AddFriendName.getText();
                try {
//                        DelFriend(u, name);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }else JOptionPane.showMessageDialog(null,"请输入好友用户名！");
        });
        RightPanel.add(DeleteFriend);
        //----------------------------------------------------------------------------------------------------




        //-----------------------------------------查询好友------------------------------------------
        JButton QueryFriend=new JButton("查询好友");
        QueryFriend.setBounds(40,140,130,30);
        QueryFriend.addActionListener(e -> {
                try {
//                        new FriendsList(u);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

        });
        RightPanel.add(QueryFriend);
        //----------------------------------------- -------------------------------------------------------------



        //-------------------------------------------------------右面板尾------------------------------------------------------------------------
        this.setVisible(true);//显示
    }


}
