

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
 
/**
 * 本类用于在客户端处理用户选定的文件并将其通过Socket上传至服务器端
 */
public class FileTransferClient extends Socket {
	
    private static final String SERVER_IP = "127.0.0.1";
    
    // 定义文件传输服务器端口
    private static final int SERVER_PORT = 8899; 
 
    private Socket client;
 
    private FileInputStream fis;
 
    private DataOutputStream dos;
 
    /*
     * 与服务器建立连接
     */
    public FileTransferClient() throws Exception {
        super(SERVER_IP, SERVER_PORT);
        this.client = this;
        //System.out.println("Cliect[port:" + client.getLocalPort() + "] 成功连接服务端");
    }
    
    /*
     * 上传文件至服务器
     */
    public void sendFile(String fileDirectory) throws Exception {
        try {
            File file = new File(fileDirectory);
            if(file.exists()) {
                fis = new FileInputStream(file);                //文件输入流
                dos = new DataOutputStream(client.getOutputStream());   //文件输出流
 
                // 读取选定的文件名称以及长度
                dos.writeUTF(file.getName());
                dos.flush();
                dos.writeLong(file.length());
                dos.flush();
 
                // 开始传输文件
                System.out.println("------ Start uploading file ------");
                byte[] bytes = new byte[1024*9];
                int length = 0;
                long progress = 0;
                while((length = fis.read(bytes, 0, bytes.length)) != -1) {
                    dos.write(bytes, 0, length);  //写入输出流 上传至服务端
                    dos.flush();
                    progress += length;
                    System.out.print("| " + (100*progress/file.length()) + "% |");
                }
                System.out.println();
                System.out.println("------ File uploaded successfully ------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fis != null)
                fis.close();
            if(dos != null)
                dos.close();
            client.close();
        }
    }
}
