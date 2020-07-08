

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
 
/**
 * ���������ڿͻ��˴����û�ѡ�����ļ�������ͨ��Socket�ϴ�����������
 */
public class FileTransferClient extends Socket {
	
    private static final String SERVER_IP = "127.0.0.1";
    
    // �����ļ�����������˿�
    private static final int SERVER_PORT = 8899; 
 
    private Socket client;
 
    private FileInputStream fis;
 
    private DataOutputStream dos;
 
    /*
     * ���������������
     */
    public FileTransferClient() throws Exception {
        super(SERVER_IP, SERVER_PORT);
        this.client = this;
        //System.out.println("Cliect[port:" + client.getLocalPort() + "] �ɹ����ӷ����");
    }
    
    /*
     * �ϴ��ļ���������
     */
    public void sendFile(String fileDirectory) throws Exception {
        try {
            File file = new File(fileDirectory);
            if(file.exists()) {
                fis = new FileInputStream(file);                //�ļ�������
                dos = new DataOutputStream(client.getOutputStream());   //�ļ������
 
                // ��ȡѡ�����ļ������Լ�����
                dos.writeUTF(file.getName());
                dos.flush();
                dos.writeLong(file.length());
                dos.flush();
 
                // ��ʼ�����ļ�
                System.out.println("------ Start uploading file ------");
                byte[] bytes = new byte[1024*9];
                int length = 0;
                long progress = 0;
                while((length = fis.read(bytes, 0, bytes.length)) != -1) {
                    dos.write(bytes, 0, length);  //д������� �ϴ��������
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
