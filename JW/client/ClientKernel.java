
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.JOptionPane;
public class ClientKernel {
    public static final char MSGENDCHAR = 0xff;
    public static final char EXIT = 0xFE;
    public static final char NICK = 0xFD;
    public static final char COMMAND = 0xFD;
    
    private String serverAd;
    private int port;
    private Socket sock;
    private boolean isConnected = false;
    private boolean dropMe = false;
    private LinkedList clients;
    public String nick;
    public boolean printMsg = true;
    private ClientMsgSender cms;
    private ClientMsgListener cml;
    /** Creates a new instance of ClientKernel */
    public ClientKernel(String server, int port) {
        this.port = port;
        nick = "" + port;
        serverAd = server;
        clients = new LinkedList();
        connect();
        if(isConnected) {
            cms = new ClientMsgSender(this, sock);
            cml = new ClientMsgListener(this, sock);
        }
    }
    public void connect() {
        try {
            sock = new Socket(serverAd, port);
            isConnected = true;
        } catch(IOException ioe ) {
            ioe.printStackTrace();
        }
    }
    public int getPort() {
        return port;
    }
    public boolean setNick(String nick) {
        sendMessage("/" + "nick " + nick);
        return true;
    }
    
    public boolean setFriend(String friend) {
    	sendMessage("" + ClientKernel.COMMAND + "friend " + friend);
        return true;
    }
    public boolean savInfo(String Field,String Info) {
    	sendMessage(""+ClientKernel.COMMAND+"info "+Field+"#"+Info);
    	return true;
    }
    /*public boolean getInfo(String user) {
    	sendMessage(""+ClientKernel.COMMAND+"getinfo "+user);
    	return true;
    }*/
    
    
    public int getLocalPort() {
        return sock.getLocalPort();
    }
    public void dropMe() {
        System.out.println("Drop ME!!!");
        cms.drop();
        cml.drop();
        dropMe = true;
        while(cml.hasStoped() && cms.hasStoped()) pause(5);
    }
    public void sendMessage(String str) {
        if(!dropMe) {
            if(str.charAt(0) == '/')
                cms.addMessage("" + ClientKernel.COMMAND + str.substring(1) );
            else if(str.charAt(0) == '@') {
            	 cms.addMessage("" + ClientKernel.COMMAND + "find"+" "+str.substring(1) );
            }
            else cms.addMessage(str);
        }
    }
    public void addClient(ChatClient c) {
        clients.add(c);
    }
    public void removeClient(ChatClient c) {
        clients.remove(c);
    }
    public void pause(int time) {
        try {
            Thread.sleep(time);
        } catch(Exception e) {}
    }
  
    //这个函数进行了修改
    public synchronized void storeMsg(String str) {
//    	JOptionPane.showMessageDialog(null, str, str, JOptionPane.ERROR_MESSAGE); 
    	
//    	JOptionPane.showMessageDialog(null, "alert1cccccccccc", "alert1", JOptionPane.ERROR_MESSAGE);
    	Object[] client = clients.toArray();
        for(int i=0;i<client.length;i++)
            ((ChatClient)(client[i])).addMsg(str);
 	
//    	JOptionPane.showMessageDialog(null, str, "alert1", JOptionPane.ERROR_MESSAGE);
        if(str.indexOf('/')==0)
    	{   	    
    		StringTokenizer strTok = new StringTokenizer(str.substring(1));
//    		JOptionPane.showMessageDialog(null, str.substring(1), "int indexof", JOptionPane.ERROR_MESSAGE); 
 //   		String command=str.substring(1);
 //   		JOptionPane.showMessageDialog(null, command, "int indexofnewss", JOptionPane.ERROR_MESSAGE);
 //   		String command = strTok.nextToken();
    		Object[] client1 = clients.toArray();				    		
			for(int i=0;i<client1.length;i++)
			{
				((ChatClient)(client1[i])).d.clear();				
			}
    		do
    		{    		
        		String command = strTok.nextToken();
    			for(int i=0;i<client1.length;i++)
    			{
    				((ChatClient)(client1[i])).updateList(command);		
 //   				JOptionPane.showMessageDialog(null, str.substring(1), "updatelistf", JOptionPane.ERROR_MESSAGE);
    			}

    		}while(strTok.hasMoreTokens());		
    	}
    }
    
    
    public boolean isConnected() {
        return isConnected;
    }
    public static void main(String args[]) {
        new ClientKernel("localhost", 1984);
    }
}
class ClientMsgSender extends Thread {
    private Socket s;
    private ClientKernel ck;
    private LinkedList msgList;
    private boolean running = true;
    private boolean hasStoped = false;
    public ClientMsgSender(ClientKernel ck, Socket s) {
        this.ck = ck;
        this.s  = s;
        msgList = new LinkedList();
        start();
    }
    public synchronized void addMessage(String msg) {
        msgList.addLast(msg);
    }
    public void drop() {
        running = false;
    }
    public boolean hasStoped() {
        return hasStoped;
    }
    public void run() {
        try {
            DataOutputStream dataOut = new DataOutputStream(s.getOutputStream());
            while(running) {
                while(msgList.size()>0) {
                    String msg = ((String)(msgList.removeFirst()));
                    char[] data = msg.toCharArray();
                    for(int i=0;i<data.length;i++) dataOut.write((int)data[i]);//发送请求
                    dataOut.write(ClientKernel.MSGENDCHAR);
                }
                sleep(10);
            }
            dataOut.write(ClientKernel.EXIT);
            dataOut.close();
            stop();
        } catch(Exception ioe) {
            ioe.printStackTrace();
        } finally {
            hasStoped = true;
        }
    }
}
class ClientMsgListener extends Thread{
    private ClientKernel ck;
    private Socket s;
    private boolean running = true;
    private boolean hasStoped = false;
    public ClientMsgListener(ClientKernel ck, Socket s) {
        this.ck = ck;
        this.s  = s;
        start();
    }
    public void drop() {
        running = false;
    }
    public boolean hasStoped() {
        return hasStoped;
    }
    public void run() {
        try {
                BufferedInputStream buffIn = new BufferedInputStream(s.getInputStream());
                DataInputStream dataIn = new DataInputStream(buffIn);
                while(running) {
                    StringBuffer strBuff = new StringBuffer();
                    int c;
                    while( (c=dataIn.read()) != ClientKernel.MSGENDCHAR) {
                        strBuff.append((char)c);
                    }
                    ck.storeMsg("" + strBuff.toString());
                }
                dataIn.close();
                buffIn.close();
                stop();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } finally {
            hasStoped = true;
        }
    }
}
