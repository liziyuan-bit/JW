
import java.net.*;
import java.util.LinkedList;
import java.io.*;
public class MainServer extends Thread {
    public static final String DISCONNECTED = "Software caused connection abort";
    public static final String DISCONNECTED_CLIENT = "Socket closed";
    public static final String PORT_USED_ERROR = "Address already in use: JVM_Bind";
    public static long uptime = 0; 
    public static long connects = 0;
    public static final char MSGENDCHAR = 0xff;
    int port = 1984;
    int clients = 8;
    private boolean newPort = true;
    private ServerSocket sSock;
    private Socket sock;
    public ConnectionKeeper ck;
    public static DataSource ds;
    public static CommandParser cp;
    public MainServer(int port) {
        this.port = port;
        ck = new ConnectionKeeper(MainServer.cp);//用cp初始化MainServer的连接维护器。
        MainServer.uptime = System.currentTimeMillis();
        start();//调用run函数
    }
    public void run() {
        while(true) {
            try {
                sSock = new ServerSocket(port);
                if(newPort) {
                    System.out.println("Server Listening at port: " + sSock.getLocalPort());
                    newPort = false;
                }
                sock = sSock.accept();//每收到连接请求后，建立一个socket，并赋值给sock对象
                ck.add(sock);//将sock加入ck列表中，并调用add函数创建clientedClient对象，用于收发该socket对象的消息。（见add函数）

/*
 //测试
              System.out.println("this is test 1");              
              LinkedList<?> users= (LinkedList<?>)(ck.users()).clone();
              System.out.println("this is test2 "+Integer.toString(users.size()));
//            String msg = "Current Connected Users: <br>";
              String msg ="/";
              while(users.size()>0)
//           	  msg += "" + ((ConnectedClient)(users.removeFirst())).getNick()+" ";
                  msg += " " + ((ConnectedClient)(users.removeFirst())).getNick();
//                sendMessage(msg);
              
              LinkedList<?> u1 = (LinkedList<?>)(ck.users()).clone();
              System.out.println("this is test3 "+Integer.toString(u1.size()));
              while(u1.size()>0)
                  ((ConnectedClient)(u1.removeFirst())).sendMessage(msg);
                  System.out.println("this is test 4"+msg);
      //this is a test
               */   
                  
                sSock.close();
            } catch(Exception e) {
                String message = e.getMessage();
                if(message.startsWith(MainServer.PORT_USED_ERROR)) {
                    System.out.println("Port " + port + " is already used, Attempting to use " + (port+=1));
                    newPort = true;
                } else {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }
    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch(Exception e) {}
    }
    public static void main(String arg[]) {
        int port = 0;
        MainServer ms;
        MainServer.ds = new FileDataSource();
        MainServer.cp = new BroadcastCommandParser();
        MainServer.cp.setDataSource(MainServer.ds);//服务器包含数据源及命令解析器
        if(arg.length!=1) {
            ms = new MainServer(3500);//调用构造函数开始执行服务器工作
            System.out.println("Usage: java jchat.server.MainServer <port>\nAttempting to use default port 3500");
        } else {
            try {
                port = Integer.parseInt(arg[0]);
            } catch(NumberFormatException nfe) {
                System.out.println("Attempting to use default port 3500");
                port = 3500;
            } finally {
                ms = new MainServer(port);
            }
        }
    }
}
