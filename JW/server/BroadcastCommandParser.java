
import java.util.*;

import javax.swing.JOptionPane;
public class BroadcastCommandParser implements CommandParser {
	private final String ONLINE="online";
	private final String GETINFO="getinfo";
	private final String FRIENDLIST="friendlist";
	private final String INFO="info";
    private final String FRIEND = "friend";//添加好友命令
	
    private final String NICK = "nick";
    private final String USERS = "users";
    private final String EXIT = "exit";
    private final String VERSION = "version";
    private final String VERIFY = "verify";
    private final String REGISTER = "register";
    private final String WHO_AM_I = "whoami";
    private final String MSG = "msg";
    private final String STATS = "stats";
    private final String REQ = "req";
    private final String FIND = "find";
    
        private final String tab = "&nbsp;&nbsp;&nbsp;";
    private DataSource ds;
    private final int sek = 1000;
    private final int min = 60*sek;
    private final int hours = 60*min;
    private final int days = 24*hours;
    public BroadcastCommandParser() {
        System.out.println("BroadcastCommandParser");
    }
    public  void runCommand(ConnectedClient cc, String str) {
        try {
            if(ds == null) {
                System.out.println("CommandParser: DataSoruce Missing");
                cc.sendMessage("Server: Your command didn't get parsed, The Server Admin knows why ;)");
            } else {
                StringTokenizer strTok = new StringTokenizer(str);
                System.out.println("from broadcastcommanderparser:"+str);
                String command = strTok.nextToken();
                if(command.equalsIgnoreCase(NICK))
                    if(strTok.hasMoreTokens()) {
                    	setNick(cc, strTok.nextToken());
                    	
                    	
                    	
                    	//this is a test
                    	System.out.println("this is test 1");                    	
                    	ConnectionKeeper ck=cc.getConnectionKeeper();
                        LinkedList<?> users= (LinkedList<?>)(ck.users()).clone();
                        System.out.println("this is test2 "+Integer.toString(users.size()));
//                      String msg = "Current Connected Users: <br>";
                        String msg ="/";
                        while(users.size()>0)
//                     	  msg += "" + ((ConnectedClient)(users.removeFirst())).getNick()+" ";
                            msg += " " + ((ConnectedClient)(users.removeFirst())).getNick();
//                          sendMessage(msg);
                        
                        LinkedList<?> u1 = (LinkedList<?>)(ck.users()).clone();
                        System.out.println("this is test3 "+Integer.toString(u1.size()));
                        while(u1.size()>0)
                            ((ConnectedClient)(u1.removeFirst())).sendMessage(msg);
                            System.out.println("this is test 4"+msg);
                    	
                    	//this is a test
                    	
                    }else 
                    	cc.sendMessage("usage: /nick <newNick>");
                else if (command.equalsIgnoreCase(USERS))
                    users(cc);
                else if (command.equalsIgnoreCase(EXIT))
                    exit(cc);
                else if (command.equalsIgnoreCase(VERIFY))
                    verifyNick(cc, strTok.nextToken());
                else if(command.equalsIgnoreCase(REGISTER))
                    registerNick(cc, strTok.nextToken(), strTok.nextToken());
              
                
                
                //this is a test  
                else if(command.equalsIgnoreCase(REQ))
                    request(cc, strTok.nextToken());
                //this is a test
                
              //this is a test  
                else if(command.equalsIgnoreCase(FIND))
                	findAndSend(cc, strTok.nextToken(),strTok.nextToken());
                //this is a test
                
                else if(command.equalsIgnoreCase(WHO_AM_I))//加入代码
                    whoAmI(cc);
                else if(command.equalsIgnoreCase(MSG))
                    msg(cc, strTok.nextToken(), strTok);
                else if(command.equalsIgnoreCase(STATS))
                    stats(cc);
                else if(command.equalsIgnoreCase(FRIEND))
                	addFriend(cc,strTok.nextToken());
                else if(command.equalsIgnoreCase(INFO))
                	addInfo(cc,strTok.nextToken());
                else if(command.equalsIgnoreCase(GETINFO))
                	getInfo(cc,strTok.nextToken());
                else if(command.equalsIgnoreCase(FRIENDLIST))
                	getFriendList(cc);
                else if(command.equalsIgnoreCase(ONLINE))
                	sendOnlineMessage(cc);
            }
        } catch(Exception e) {
            System.out.println("CommandParser: " + e.getMessage());
            cc.sendMessage("Invalid Command: " + str);
        }
    }
    
    
    private void sendOnlineMessage(ConnectedClient cc) {
    	String user=cc.getNick();
    	LinkedList friendlist=ds.getFriendRelation(cc.getNick());
    	Iterator it =friendlist.iterator();
    	String f=null;
    	while(it.hasNext()) {
    		f=(String)it.next();
    		//cc.sendMessage(f);
    		findAndSend(cc,f,"your friend "+user+" online");
    	}
    	
    	//
    }
    private void getFriendList(ConnectedClient cc) {
    	LinkedList friendlist=ds.getFriendRelation(cc.getNick());
    	Iterator it =friendlist.iterator();
    	String f=null;
    	while(it.hasNext()) {
    		f=(String)it.next();
    		cc.sendMessage(f);
    	}
    	cc.sendMessage("friendlist send");
    }
    private void getInfo(ConnectedClient cc,String str) {
    	String[] s=ds.getAllUserInfo(str);
    	cc.sendMessage("Field:"+s[0]+"Info:"+s[1]);
    }
    private void addFriend(ConnectedClient cc,String str) {
    	cc.sendMessage("Friend request from" + cc.getNick() + "to" + str + "send");
    	ds.sendFriend(cc.getNick(), str);
    	//未加入私人通话，不能讲好友申请信息发到接收者
    }
    private void addInfo(ConnectedClient cc,String str) {
    	cc.sendMessage("Info set");
    	String[] strs=str.split("#");
    	cc.sendMessage("Field:"+strs[0]);
    	cc.sendMessage("Info:"+strs[1]);
    	ds.addInfo(cc.getNick(), strs[0], strs[1]);
    	cc.sendMessage("server:"+ds.getAllUserInfo(cc.getNick())[0]+ds.getAllUserInfo(cc.getNick())[1]);
    }
    
    //this is a test
    private void request(ConnectedClient cc,String nick) {
    	System.out.println("received!  "+nick);
    	
    	LinkedList users = (LinkedList)((cc.getConnectionKeeper().users()).clone());
        String msg="";
        while(users.size()>0)
        {
        	ConnectedClient c =(ConnectedClient)users.removeFirst();
        	String test =c.getNick();
        	System.out.println("test: "+test);
            if(test.equalsIgnoreCase(nick))
            {           	 
            	 msg =c.ipNumber+"port: "+Integer.toString(c.portNumber);
            	 c.sendTo(nick, msg);
             }   	
            else
            {
            	msg="not found!";
            }
        }
            //String msg = cc.ipNumber+cc.portNumber;
            System.out.println("received!  "+nick + "  get:  " +msg);
//            cc.sendMessage("order!");
        }
    //this is a test
    
    //this is a test
    void findAndSend(ConnectedClient cc,String nick,String msg) {
    	LinkedList users = (LinkedList)((cc.getConnectionKeeper().users()).clone());
        while(users.size()>0)
        {
        	ConnectedClient c =(ConnectedClient)users.removeFirst();
        	String test =c.getNick();
            if(test.equalsIgnoreCase(nick))
            {           	 
            	 c.sendTo(nick, msg);
             }   	
            /*else
            {
            	msg="not found!";
            }*/
        }
        System.out.println("received!  "+nick + "  get:  " +msg);
    }
    //this is a test
    private void stats(ConnectedClient cc) {
        long runningTime = System.currentTimeMillis() - MainServer.uptime;
        String str = "Server has been running for " + printTime(runningTime) + "<br>" + 
                     "User connects since uptime " + MainServer.connects + "<br>";
                     
                     
        cc.sendMessage(str);
    }
    private String printTime(long time) {
        String str = "";
        if(time<sek) {
            str+="" + time + "ms";
            return str;
        }
        if(time>sek && time<min) {
            long t = time%sek;
            str+="" + (time/sek) + "sek " + printTime(t);
            return str;
        }
        if(time>min && time<hours) {
            long t = time%min;
            str+= "" + (time/min) + "min " + printTime(t);
            return str;
        }
        if(time>hours && time<days) {
            long t= time%hours;
            str+= "" + (time/hours) + "hours " + printTime(t);
        }
        return str;
    }
   
    private void msg(ConnectedClient cc, String user, StringTokenizer strTok) {
        StringBuffer strBuff = new StringBuffer();
        while(strTok.hasMoreTokens())
            strBuff.append(strTok.nextToken() + " ");
        
        cc.sendTo(user, cc.nick + ":" + strBuff.toString());
    }
    private  void users(ConnectedClient cc) {
 //   	JOptionPane.showMessageDialog(null, "alert", "alert", JOptionPane.ERROR_MESSAGE); 
 //   	System.out.println("this is a tests");
        LinkedList users = (LinkedList)((cc.getConnectionKeeper().users()).clone());
        String msg = "Current Connected Users: <br>";
        while(users.size()>0)
            msg += "*" + ((ConnectedClient)(users.removeFirst())).getNick() + "<br>";
        cc.sendMessage(msg);
    }
    private  void setNick(ConnectedClient cc, String str) {
        //System.out.println("" + cc.nick + " is now known as " + str);
        /*cc.nick = str;
        cc.sendMessage("Server: Your are now known as " + str);*/
        cc.verifyedBoolean = false;
        boolean verify = ds.verifyUser(str, "");
        if(verify) {
            if(isNickFree(cc, str)) {
                cc.nick = str;
                cc.verifyedBoolean = true;
            } else 
                cc.sendMessage("nick " + str + " was allready taken");
            
            
        } else {
            cc.verifyedCount = 5;
            cc.tmpNick = str;
            cc.sendMessage("Nick " + str + " is registered so you have to " +
                            "verify that this nick is yours");
        }
    }
    private boolean isNickFree(ConnectedClient cc, String nick) {
        LinkedList users = (LinkedList)((cc.getConnectionKeeper().users()).clone());
        Iterator it = users.iterator();
        while(it.hasNext()) {
            ConnectedClient comp = ((ConnectedClient)(it.next()));
            String compNick = comp.getNick();
            if(nick.equalsIgnoreCase(compNick)) return false;
        }
        return true;
    }
    private void whoAmI(ConnectedClient cc) {
        cc.whoAmI();
    }
    private void registerNick(ConnectedClient cc, String nick, String pass) {
        if(pass.length()<4 || nick.length()<4) {
            cc.sendMessage("Your nick/password needs to be atleast 4 chars long");
        } else {
            if(ds.addUser(nick, pass)) {
                cc.sendMessage("User " + nick + " is now registered and set as your own");
                cc.nick = nick;
                cc.verifyedBoolean = true;
            } else {
                cc.sendMessage("The username is allready taken");
            }
            
        }
    }
    private void verifyNick(ConnectedClient cc, String password) {
        if(ds.verifyUser(cc.tmpNick, password)) {
            cc.nick = cc.tmpNick;
            cc.verifyedBoolean = true;
        } else {
            cc.nick = "" + cc.portNumber;
            cc.sendMessage("Invalid user/pass, your nick is set to " + cc.nick);
        }
    }
    private  void exit(ConnectedClient cc) {
        cc.sendMessage("Server: You are being disconected!");
        try { Thread.sleep(50); } catch(Exception e) {}
        cc.dropClient();
    }
    public void setDataSource(DataSource ds) {
        this.ds = ds;
    }
}
