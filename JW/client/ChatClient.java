
import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.io.BufferedReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.*;


import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ChatClient extends JFrame implements KeyListener, ActionListener, FocusListener,ListSelectionListener {
    public static final String appName = "Chat Tool";
    public static final String serverText = "127.0.0.1";
    public static final String portText = "3500";
    public static final String nickText = "YourName";
    public static final String friendText="Friend's name";
    JPanel northPanel, southPanel, centerPanel;
    JTextField txtHost, txtPort, msgWindow, txtNick,txtFriend,txtField,txtInfo;
    JButton buttonConnect, buttonSend,buttonAdd,buttonAccept,buttonSave;
    JScrollPane sc;
    ClientKernel ck;
    ClientHistory historyWindow;//ClientHistory类在下面定义，继承自JEditPane类
    DefaultListModel d;
    
    
    //this is s test
    	private JList<String> list;
//    	private JTextField tf=new JTextField("文本框");
    	private  String[] color={"红色","黄色","蓝色","绿色","紫色","橙色","黑色"}; 
   // 
    	
   //listclick test
    	MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                JList theList = (JList) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 2) {
                    int index = theList.getSelectedIndex();
                    if (index >= 0) {
//                        String s = theList.getItem(index);
                    	String s =theList.getSelectedValue().toString();        
                    	
//                       JOptionPane.showMessageDialog(null, s, s, JOptionPane.ERROR_MESSAGE); 
                        request(s);
                    	/*
                    	Frame1 jf1=new Frame1();
                    	jf1.setBounds(100,50,800,600);
                    	jf1.setDefaultCloseOperation(jf1.EXIT_ON_CLOSE);
                    	jf1.setVisible(true);
                    	*/
                    	//第一步，查找选择对象的IP及端口号
                    	//第二步，让选择对象建立聊天服务器
                    	//第三步，建立客户端，连接聊天服务器
                    	
                    	/*
                    	Client  frm=new Client();
                	    frm.setVisible(true);
                	    
                	    Server ser=new Server();
                	    ser.setVisible(true);
                	    */
                    }
                }
            }
        };
    //

public class Client extends JFrame implements ActionListener {

	JPanel contentPane;
	  JLabel jLabel1 = new JLabel();
	  JTextField jTextField1 = new JTextField("127.0.0.1");
	  JLabel jLabel2 = new JLabel();
	  JTextField jTextField2 = new JTextField("4700");
	  JButton jButton1 = new JButton();
	  JLabel jLabel3 = new JLabel();
	  JTextField jTextField3 = new JTextField();
	  JButton jButton2 = new JButton();
	  JScrollPane jScrollPane1 = new JScrollPane();
	  JTextArea jTextArea1 = new JTextArea();
	  BufferedReader instr =null;
	  Socket socket = null;
	  PrintWriter os=null;
	  public Client() {
	      jbInit();
	  }
	  class MyThread extends Thread{
	  public void run(){
	   try{
	    os=new PrintWriter(socket.getOutputStream());
	       instr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    while(true){
	    this.sleep(100);
	    if(instr.ready()){     
	      jTextArea1.append("服务器: "
	        +instr.readLine()+"\n");
	    }
	   }  
	   }catch(Exception ex){
	   }
	   
	  }
	 }
	  public void actionPerformed(ActionEvent e){
	    if(e.getSource()==jButton1){
	     String ip=jTextField3.getText().trim();
	     jTextField3.setText("");
	      int port=Integer.parseInt(jTextField2.getText().trim());
	       connectServer(ip,port);
	    }
	    if(e.getSource()==jButton2){
	     String s=this.jTextField3.getText().trim();
	       sendData(s); 
	    }
	  }
	  private void connectServer(String ip,int port){//连接
	    
	    try{  
	         if(jButton1.getText().trim().equals("连接")){
	            jButton1.setText("连接服务器...");
	            socket=new Socket(ip,port);
	           jButton1.setText("正在聊天");
	         
	     MyThread t=new MyThread();
	     t.start();
	           }      
	     }catch(Exception ex){
	    }
	  }
	  private void sendData(String s){//发送数据
	     try{
	      os = new PrintWriter(socket.getOutputStream());
	      os.println(s);
	   os.flush();
	   this.jTextArea1.append("Server:"+s+"\n");
	   
	    }catch(Exception ex){
	    }
	  }
	  private void jbInit() {
	    contentPane = (JPanel) this.getContentPane();
	    jLabel1.setFont(new java.awt.Font("宋体", 0, 14));
	    jLabel1.setText("服务器名称");
	    jLabel1.setBounds(new Rectangle(20, 22, 87, 28));
	    contentPane.setLayout(null);
	    this.setSize(new Dimension(540, 340));
	    this.setTitle("客户端");
	 
	    jTextField1.setBounds(new Rectangle(114, 26, 108, 24));
	    jLabel2.setBounds(new Rectangle(250, 25, 72, 28));
	    jLabel2.setText("端口号");
	    jLabel2.setFont(new java.awt.Font("宋体", 0, 14));
	    jTextField2.setBounds(new Rectangle(320, 27, 108, 24));
	    
	    jButton1.setBounds(new Rectangle(440, 28, 73, 25));
	    jButton1.setFont(new java.awt.Font("Dialog", 0, 14));
	    jButton1.setBorder(BorderFactory.createEtchedBorder());
	    jButton1.setActionCommand("jButton1");
	    jButton1.setText("连接");
	    jLabel3.setBounds(new Rectangle(23, 57, 87, 28));
	    jLabel3.setText("请输入信息");
	    jLabel3.setFont(new java.awt.Font("宋体", 0, 14));
	    jTextField3.setBounds(new Rectangle(114, 60, 314, 24));
	 
	    jButton2.setText("发送");
	    jButton2.setActionCommand("jButton1");
	    jButton2.setBorder(BorderFactory.createEtchedBorder());
	    jButton2.setFont(new java.awt.Font("Dialog", 0, 14));
	    jButton2.setBounds(new Rectangle(440, 58, 73, 25));
	    jScrollPane1.setBounds(new Rectangle(23, 92, 493, 189));

	    contentPane.add(jLabel1, null);
	    contentPane.add(jTextField1, null);
	    contentPane.add(jLabel2, null);
	    contentPane.add(jTextField2, null);
	    contentPane.add(jButton1, null);
	    contentPane.add(jLabel3, null);
	    contentPane.add(jTextField3, null);
	    contentPane.add(jButton2, null);
	    contentPane.add(jScrollPane1, null);
	    jScrollPane1.getViewport().add(jTextArea1, null);
	    jButton1.addActionListener(this);
	    jButton2.addActionListener(this);
	    this.addWindowListener(new WindowAdapter(){
	      public void windowClosing(WindowEvent e){
	       try{
	        socket.close();instr.close();os.close();System.exit(0);
	           }catch(Exception ex){
	       }
	       
	      }
	     });
	  }
	  /*
	  public static void main(String arg[]){
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    Client  frm=new Client();
	    frm.setVisible(true);
	    }
	  */
	 }
public class Server extends JFrame implements ActionListener {
	  JPanel contentPane;
	  JLabel jLabel2 = new JLabel();
	  JTextField jTextField2 = new JTextField("4700");
	  JButton jButton1 = new JButton();
	  JLabel jLabel3 = new JLabel();
	  JTextField jTextField3 = new JTextField();
	  JButton jButton2 = new JButton();
	  JScrollPane jScrollPane1 = new JScrollPane();
	  JTextArea jTextArea1 = new JTextArea();
	  ServerSocket server = null;
	  Socket socket = null;BufferedReader instr =null;PrintWriter os=null ;
	  //Construct the frame
	  public Server() {
	     jbInit();
	     
	  }
	  class MyThread extends Thread{//该线程负责接受数据
	  public void run(){
	   try{
	     while(true){
	   //  this.sleep(100);
	     instr= new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        if(instr.ready()){ //检查是否有数据    
	           jTextArea1.append("客户端:  "+instr.readLine()+"\n");
	        }
	        }  
	      }catch(Exception ex){}
	  }
	 }
	   public void actionPerformed(ActionEvent e){
	    if(e.getSource()==jButton1){
	      int port=Integer.parseInt(jTextField2.getText().trim());
	       listenClient(port);
	    }
	    if(e.getSource()==jButton2){
	     String s=this.jTextField3.getText().trim();
	       sendData(s); 
	    }
	  }
	  
	  private void listenClient(int port){//侦听
	    try{  
	         if(jButton1.getText().trim().equals("侦听")){
	            server = new ServerSocket(port);
	            jButton1.setText("正在侦听...");
	           socket=server.accept();//等待，一直到客户端连接才望下执行
	           sendData("已经成功连接。。。");
	           jButton1.setText("正在聊天...");
	           jTextArea1.append("客户端已经连接到服务器\n");
	           MyThread t=new MyThread();
	           t.start();
	         }
	       
	     }catch(Exception ex){
	    }
	  }
	  private void sendData(String s){//发送数据
	     try{
	      os= new PrintWriter(socket.getOutputStream());
	      os.println(s);
	   os.flush();
	   if(!s.equals("已经成功连接。。。"))
	     this.jTextArea1.append("Server:"+s+"\n");
	    }catch(Exception ex){
	    }
	  }
	  //Component initialization
	  private void jbInit() {
	    contentPane = (JPanel) this.getContentPane();
	    contentPane.setLayout(null);
	    this.setSize(new Dimension(540, 340));
	    this.setTitle("服务器");
	    jLabel2.setBounds(new Rectangle(22, 27, 72, 28));
	    jLabel2.setText("端口号");
	    jLabel2.setFont(new java.awt.Font("宋体", 0, 14));
	    jTextField2.setBounds(new Rectangle(113, 27, 315, 24));
	    
	    jButton1.setBounds(new Rectangle(440, 28, 73, 25));
	    jButton1.setFont(new java.awt.Font("Dialog", 0, 14));
	    jButton1.setBorder(BorderFactory.createEtchedBorder());
	    jButton1.setActionCommand("jButton1");
	    jButton1.setText("侦听");
	    jLabel3.setBounds(new Rectangle(23, 57, 87, 28));
	    jLabel3.setText("请输入信息");
	    jLabel3.setFont(new java.awt.Font("宋体", 0, 14));
	    jTextField3.setBounds(new Rectangle(114, 60, 314, 24));
	    jTextField3.setText("");
	    jButton2.setText("发送");
	    jButton2.setActionCommand("jButton1");
	    jButton2.setBorder(BorderFactory.createEtchedBorder());
	    jButton2.setFont(new java.awt.Font("Dialog", 0, 14));
	    jButton2.setBounds(new Rectangle(440, 58, 73, 25));
	    jScrollPane1.setBounds(new Rectangle(23, 92, 493, 189));

	    contentPane.add(jTextField2, null);
	    contentPane.add(jButton1, null);
	    contentPane.add(jLabel3, null);
	    contentPane.add(jTextField3, null);
	    contentPane.add(jButton2, null);
	    contentPane.add(jScrollPane1, null);
	    contentPane.add(jLabel2, null);
	    jScrollPane1.getViewport().add(jTextArea1, null);
	       jButton1.addActionListener(this);
	     jButton2.addActionListener(this);
	     this.addWindowListener(new WindowAdapter(){
	      public void windowClosing(WindowEvent e){
	       try{
	        socket.close();
	        instr.close();
	        System.exit(0);
	       }catch(Exception ex){
	       }
	       
	      }
	     });
	  }
	  /*
	  public static void main(String arg[]){
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    Server frm=new Server();
	    frm.setVisible(true);
	   
	  }
     */
}


/*
class Frame1 extends JFrame
    {
    	private JButton jButton1=new JButton();
    	public Frame1 ()
    	{
    		try {
    			jbInit();
    			}
    		catch(Exception exception) {
    			exception.printStackTrace();
    			}    
    		this.setVisible(true);  
    	} 
    	private void jbInit () throws Exception  {
    		this.setBounds(300,180,400,300);
    		getContentPane().setLayout(null);
    		jButton1.setBounds(new Rectangle(127, 120, 139, 36));
    		jButton1.setMnemonic('C');
    		jButton1.setVisible(true);
    		jButton1.setText("点我bai(C)");
    		jButton1.addActionListener(new ActionListener(){
    			public void actionPerformed (ActionEvent e){
    				jButton1_actionPerformed(e);      
    				}   
    			});
    		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		this.getContentPane().add(jButton1);
    	}
    	public void jButton1_actionPerformed (ActionEvent e) {
    		this.setVisible(false);
    		JFrame jf1=new JFrame("子窗口");
    		jf1.setBounds(100,50,800,600);
    		jf1.setDefaultCloseOperation(jf1.EXIT_ON_CLOSE);
    		jf1.setVisible(true);  }
    }
   */
    private String lastMsg = "";
    /** Creates a new instance of Class */
    public ChatClient() {
        uiInit();
        txtHost.setText("127.0.0.1");
        txtPort.setText("3500");
    }
    public void uiInit() {
        setLayout(new BorderLayout());
        //创建North
        northPanel = new JPanel(new GridLayout(0,2));
        northPanel.add(new JLabel("Host address:"));
        northPanel.add(txtHost = new JTextField(ChatClient.serverText));
        northPanel.add(new JLabel("Port:"));
        northPanel.add(txtPort = new JTextField(ChatClient.portText));
        northPanel.add(new JLabel("Nick:"));
        northPanel.add(txtNick = new JTextField(ChatClient.nickText));
        northPanel.add(new JLabel(""));
        northPanel.add(new JLabel(""));
        northPanel.add(new JLabel(""));
        northPanel.add(buttonConnect = new JButton("Connect"));
        
        northPanel.add(new JLabel("Add Friend:"));
        northPanel.add(txtFriend=new JTextField(ChatClient.friendText));
        northPanel.add(buttonAdd = new JButton("Add Friend"));
        //northPanel.add(buttonAccept = new JButton("Accept"));
        northPanel.add(txtField=new JTextField("Field"));
        northPanel.add(txtInfo=new JTextField("Info"));
        northPanel.add(buttonSave = new JButton("Save Info"));
        
        
        
        buttonConnect.addActionListener(this);
        
        txtHost.addKeyListener(this);
        txtHost.addFocusListener(this);
        txtNick.addFocusListener(this);
        txtNick.addKeyListener(this);
        txtPort.addKeyListener(this);
        txtPort.addFocusListener(this);
        buttonConnect.addKeyListener(this);
        
        txtFriend.addKeyListener(this);
        txtFriend.addFocusListener(this);
        buttonAdd.addActionListener(this);
        //buttonAccept.addActionListener(this);
        
        txtField.addKeyListener(this);
        txtField.addFocusListener(this);
        txtInfo.addKeyListener(this);
        txtInfo.addFocusListener(this);
        buttonSave.addActionListener(this);
        
        this.add(northPanel, BorderLayout.NORTH);
        
        //创建Sourth
        southPanel = new JPanel();
        southPanel.add(msgWindow = new JTextField(20));
        southPanel.add(buttonSend = new JButton("Send"));
        buttonSend.addActionListener(this);
        msgWindow.addKeyListener(this);
        add(southPanel, BorderLayout.SOUTH);
        
        //创建Center
        historyWindow = new ClientHistory();  //ClientHistory类在下面定义，继承自JEditPane类
        sc = new JScrollPane(historyWindow);
        sc.setAutoscrolls(true);
        //sc.setPreferredSize(new Dimension(200,300));
        //this.add(sc, BorderLayout.EAST);
        
        //创建列表框
       // list=new JList<String>(color);
        d = new DefaultListModel();//只有默认的模型有添加/删除方法
        d.addElement("a");
        d.addElement("b");
        list = new JList(d);
        JScrollPane ps=new JScrollPane(list);
        
        list.addListSelectionListener(this);
        list.addMouseListener(mouseListener);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //  ps.setPreferredSize(new Dimension(200,100));
  
        JSplitPane jSplitPane =new JSplitPane();//设定为左右拆分布局
        jSplitPane.setOneTouchExpandable(true);//让分割线显示出箭头
        jSplitPane.setContinuousLayout(true);//操作箭头，重绘图形
        jSplitPane.setLeftComponent(ps);//布局中添加组件 ，面板1
        jSplitPane.setRightComponent(sc);//添加面板2
        jSplitPane.setDividerLocation(100);//设定分割线的距离左边的位置
        
        
        this.add(jSplitPane,BorderLayout.CENTER);
        
    }
    @Override
    public void valueChanged(ListSelectionEvent e) {

        JList<?> jlist=(JList<?>) e.getSource();
        String name=(String) jlist.getSelectedValue();
//        JOptionPane.showMessageDialog(this, "提示消息", "标题",JOptionPane.WARNING_MESSAGE);  
        if(name=="红色") list.setBackground(Color.RED);
        if(name=="黄色") list.setBackground(Color.YELLOW);
        if(name=="蓝色") list.setBackground(Color.BLUE);
        if(name=="绿色") list.setBackground(Color.GREEN);

    }
   public static void main(String args[]) {
        ChatClient client = new ChatClient();
        client.setTitle(client.appName);
        client.setSize(450, 500);
        client.setLocation(100,100);
        client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.setVisible(true);
        client.msgWindow.requestFocus();
    }
    public void addMsg(String str) {
    	
        historyWindow.addText(str);
    }
    
    //this is a test
    public void updateList(String str) {
    //	d.clear();
    	d.addElement(str);
    }
    public void update()
    {
    	list.validate();
    }
    
    //双击list的请求。
    void request(String nick) {
    	String message ="/" + " " + "req" + " " + nick;
    	JOptionPane.showMessageDialog(null, message, "in request function", JOptionPane.ERROR_MESSAGE); 
    	ck.sendMessage(message);
    }
    
    //this is a test
    private void connect() {
        try {
            if(ck!=null) ck.dropMe();
            ck = new ClientKernel(txtHost.getText(), Integer.parseInt(txtPort.getText()));
            ck.setNick(txtNick.getText());
  //          JOptionPane.showMessageDialog(null, txtNick.getText(), txtNick.getText(), JOptionPane.ERROR_MESSAGE);
            if(ck.isConnected()) {
                ck.addClient(this);
                addMsg("<font color=\"#00ff00\">connected! Local Port:" + ck.getLocalPort() + "</font>");
                //上线，发送消息
                ck.sendMessage("/online");
            } else {
                addMsg("<font color=\"#ff0000\">connect failed！</font>");
            }
        } catch(Exception e) { e.printStackTrace(); }
    }
    
    private void addFriend() {//添加好友
    	String friend=txtFriend.getText();
    	ck.setFriend(friend);
    	ck.sendMessage("@ "+ friend +" "+txtNick.getText()+"wanttobeyourfriend,sendbacktoagree");
    	txtFriend.setText("");
    }
    private void saveInfo() {
    	String Field=txtField.getText();
    	String Info=txtInfo.getText();
    	ck.savInfo(Field,Info);
    	
    }
    
    private void send() {
        String toSend = msgWindow.getText();
        ck.sendMessage(toSend);
        lastMsg = "" + toSend;
        msgWindow.setText("");
       
        //测试列表框
       // d.addElement("c");

    }
    public void keyPressed(KeyEvent e) {
    }
    public void keyReleased(KeyEvent e) {
        if(e.getSource() == msgWindow && e.getKeyCode() == KeyEvent.VK_UP) msgWindow.setText(lastMsg);
    }
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() ==KeyEvent.VK_ENTER) {
            if(e.getSource() == msgWindow) send();
            if(e.getSource() == txtNick) { connect(); msgWindow.requestFocus(); }
            if(e.getSource() == txtHost) txtPort.requestFocus();
            if(e.getSource() == txtPort) txtNick.requestFocus();
        }
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==buttonConnect) {connect();}
        if(e.getSource()==buttonSend) send();
        if(e.getSource()==buttonAdd) addFriend();
        if(e.getSource()==buttonSave) saveInfo();
        
    }
    public void focusGained(FocusEvent e) {
        if(e.getSource()==txtHost && txtHost.getText().equals(ChatClient.serverText)) txtHost.setText("");
        if(e.getSource()==txtPort && txtPort.getText().equals(ChatClient.portText)) txtPort.setText("");
        if(e.getSource()==txtNick && txtNick.getText().equals(ChatClient.nickText)) txtNick.setText("");
    }
    public void focusLost(FocusEvent e) {
       if(e.getSource()==txtPort && txtPort.getText().equals("")) txtPort.setText(ChatClient.portText);
       if(e.getSource()==txtHost && txtHost.getText().equals("")) txtHost.setText(ChatClient.serverText);
       if(e.getSource()==txtNick && txtNick.getText().equals(ChatClient.nickText)) 
                                                            txtNick.setText(ChatClient.nickText);
    }
    class ClientHistory extends JEditorPane {
        public ClientHistory() {
            super("text/html", "" + ChatClient.appName);
            setEditable(false);
            setAutoscrolls(true);
        }
        public void addText(String str) {
            String html = getText();
            int end = html.lastIndexOf("</body>");
            String startStr = html.substring(0, end);
            String endStr = html.substring(end, html.length());
            String newHtml = startStr + "<br>" + str + endStr;
            setText(newHtml);
            setSelectionStart(newHtml.length()-1);
            setSelectionEnd(newHtml.length());
         }
        public void clear() {
            setText("");
        }
    }
}

