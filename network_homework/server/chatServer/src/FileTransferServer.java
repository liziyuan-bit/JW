

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.RoundingMode;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
/*
本类定义了文件及图片传输所需要的方法
 */

public class FileTransferServer extends ServerSocket {
	// 设置文件传输Socket端口
    private static final int SERVER_PORT = 8899; 
    public static final String downloadDirec = "D:\\tmp/";

    private static DecimalFormat df = null;
 
    static {
        // 设置数字格式，保留一位有效小数
        df = new DecimalFormat("#0.0");
        df.setRoundingMode(RoundingMode.HALF_UP);
        df.setMinimumFractionDigits(1);
        df.setMaximumFractionDigits(1);
    }
 
    public FileTransferServer() throws Exception {
        super(SERVER_PORT);
    }
 
    /**
     * 对于每个客户端新建线程以防止阻塞
     */
    public void load() throws Exception {
        while (true) {
            Socket socket = this.accept();
            new Thread(new Task(socket)).start();
        }
    }
 
    /**
     * 处理从客户端传输过来的文件的线程类
     */
    class Task implements Runnable {
 
        private Socket socket;
 
        private DataInputStream dis;
 
        private FileOutputStream fos;
 
        public Task(Socket socket) {
            this.socket = socket;
        }
 
        @Override
        public void run() {
            try {
                dis = new DataInputStream(socket.getInputStream());
                // 定义文件名称以及长度
                String fileName = dis.readUTF();
                long fileLength = dis.readLong();
                File directory = new File(downloadDirec);
                if(!directory.exists()) {
                    directory.mkdir();
                }
                File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);
                fos = new FileOutputStream(file);
 
                // 开始接收文件
                byte[] bytes = new byte[1024*9];
                int length = 0;
                while((length = dis.read(bytes, 0, bytes.length)) != -1) {
                    fos.write(bytes, 0, length);  //接收文件，写入文件输出端
                    fos.flush();
                }
                System.out.println("------ File transmitted successfully [File Name：" + fileName + "] [Size：" + getFormatFileSize(fileLength) + "] ------");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if(fos != null)
                        fos.close();
                    if(dis != null)
                        dis.close();
                    socket.close();
                } catch (Exception e) {}
            }
        }
    }
 
    /**
     * 格式化文件大小
     */
    private String getFormatFileSize(long length) {
        double size = ((double) length) / (1 << 30);
        if(size >= 1) {
            return df.format(size) + "GB";
        }
        size = ((double) length) / (1 << 20);
        if(size >= 1) {
            return df.format(size) + "MB";
        }
        size = ((double) length) / (1 << 10);
        if(size >= 1) {
            return df.format(size) + "KB";
        }
        return length + "B";
    }
}
