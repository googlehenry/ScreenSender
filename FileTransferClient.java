import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

/**
 * 文件传输Client端<br>
 * 功能说明：
 * 
 * @author 大智若愚的小懂
 * @Date 2016年09月01日
 * @version 1.0
 */
public class FileTransferClient extends Socket {

	private static String SERVER_IP = "192.168.1.154"; // 服务端IP
	private static int SERVER_PORT = 9999; // 服务端端口

	private Socket client;

	private FileInputStream fis;

	private DataOutputStream dos;

	/**
	 * 构造函数<br/>
	 * 与服务器建立连接
	 * 
	 * @throws Exception
	 */
	public FileTransferClient(String ip) throws Exception {
		super(ip==null?SERVER_IP:ip, SERVER_PORT);
		this.client = this;
		System.out.println("Cliect[port:" + client.getLocalPort() + "] 成功连接服务端");
	}

	/**
	 * 向服务端传输文件
	 * 
	 * @throws Exception
	 */
	public void sendFile(File file) throws Exception {
		try {
			if (file.exists()) {
				fis = new FileInputStream(file);
				dos = new DataOutputStream(client.getOutputStream());

				// 文件名和长度
				dos.writeUTF(file.getName());
				dos.flush();
				dos.writeLong(file.length());
				dos.flush();

				// 开始传输文件
				System.out.println("======== 开始传输文件 ========");
				byte[] bytes = new byte[1024];
				int length = 0;
				long progress = 0;
				while ((length = fis.read(bytes, 0, bytes.length)) != -1) {
					dos.write(bytes, 0, length);
					dos.flush();
					progress += length;
					System.out.print("| " + (100 * progress / file.length()) + "% |");
				}
				System.out.println();
				System.out.println("======== 文件传输成功 ========");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null)
				fis.close();
			if (dos != null)
				dos.close();
			client.close();
		}
	}

	/**
	 * 入口
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			if(args!=null && args.length>0){
				SERVER_IP = args[0];
			}
			FileTransferClient client = new FileTransferClient(null); // 启动客户端连接
			client.sendFile(new File("b")); // 传输文件
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}