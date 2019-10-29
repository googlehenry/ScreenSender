
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

/**
 * ����������
 * 
 * @author zhangdi
 *
 */
public class CutPicUtil {

	/**
	 * ��Ļ��ͼ
	 * 
	 * @param imageName �洢ͼƬ����
	 * @param path      ͼƬ·��
	 * @param imgType   ͼƬ����
	 * @throws AWTException
	 * @throws IOException
	 */
	public static void cutPic() throws AWTException, IOException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle screenRectangle = new Rectangle(screenSize);
		Robot robot = new Robot();
		BufferedImage image = robot.createScreenCapture(screenRectangle);
//        String name = new SimpleDateFormat("yyyy-MM-dd HH-mm-SS").format(new Date());

		ImageIO.write(image, "jpg", new File("C:\\screens\\temp\\latestscreen.jpg"));
	}

	public static void main(String[] args) throws AWTException, IOException {

		int interval = 10;
		String ip = null;
		System.out.println("��ʾ����ָ��������ʽ��[���N��] [ip],����ʹ��Ĭ�ϼ��10��,��ip 192.168.1.154");
		
		if (args!=null && args.length > 0) {
			try {
				interval = Integer.parseInt(args[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (args!=null && args.length > 1) {
			ip = args[1].trim();
		}
		final String passedIP = ip;
		
		TimerTask tt = new TimerTask() {

			@Override
			public void run() {
				try {
					cutPic();
//					new FileClient(new File("C:\\screens\\temp\\latestscreen.jpg"),
//							new InetSocketAddress("192.168.1.153", 9999))
//					.start();

					FileTransferClient client = new FileTransferClient(passedIP); // �����ͻ�������
					client.sendFile(new File("C:\\screens\\temp\\latestscreen.jpg")); // �����ļ�
					client.close();

					System.out.println("file send once....");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(tt, interval * 1000, interval * 1000);
		
		
	}

}