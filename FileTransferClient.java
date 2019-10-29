import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

/**
 * �ļ�����Client��<br>
 * ����˵����
 * 
 * @author �������޵�С��
 * @Date 2016��09��01��
 * @version 1.0
 */
public class FileTransferClient extends Socket {

	private static String SERVER_IP = "192.168.1.154"; // �����IP
	private static int SERVER_PORT = 9999; // ����˶˿�

	private Socket client;

	private FileInputStream fis;

	private DataOutputStream dos;

	/**
	 * ���캯��<br/>
	 * ���������������
	 * 
	 * @throws Exception
	 */
	public FileTransferClient(String ip) throws Exception {
		super(ip==null?SERVER_IP:ip, SERVER_PORT);
		this.client = this;
		System.out.println("Cliect[port:" + client.getLocalPort() + "] �ɹ����ӷ����");
	}

	/**
	 * �����˴����ļ�
	 * 
	 * @throws Exception
	 */
	public void sendFile(File file) throws Exception {
		try {
			if (file.exists()) {
				fis = new FileInputStream(file);
				dos = new DataOutputStream(client.getOutputStream());

				// �ļ����ͳ���
				dos.writeUTF(file.getName());
				dos.flush();
				dos.writeLong(file.length());
				dos.flush();

				// ��ʼ�����ļ�
				System.out.println("======== ��ʼ�����ļ� ========");
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
				System.out.println("======== �ļ�����ɹ� ========");
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
	 * ���
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			if(args!=null && args.length>0){
				SERVER_IP = args[0];
			}
			FileTransferClient client = new FileTransferClient(null); // �����ͻ�������
			client.sendFile(new File("b")); // �����ļ�
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}