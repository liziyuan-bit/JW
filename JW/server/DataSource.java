import java.util.LinkedList;

public interface DataSource {
    public boolean verifyUser(String user, String pass);
    public boolean addUser(String user, String pass);
    public boolean removeUser(String user, String pass);
    public boolean addInfo(String user, String field, String info);
    public boolean removeInfo(String user, String field, String info);
    public String[] getAllUserInfo(String user);
    public String[] getUserList();
    public void sendFriend(String f1,String f2);
    public LinkedList getFriendRelation(String user);
    public class friendRelation{}
}
