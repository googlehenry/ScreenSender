
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
 * 截屏工具类
 * 
 * @author zhangdi
 *
 */
public class CutPicUtil {

	/**
	 * 屏幕截图
	 * 
	 * @param imageName 存储图片名称
	 * @param path      图片路径
	 * @param imgType   图片类型
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
		System.out.println("提示：可指定参数格式：[间隔N秒] [ip],否则使用默认间隔10秒,和ip 192.168.1.154");
		
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

					FileTransferClient client = new FileTransferClient(passedIP); // 启动客户端连接
					client.sendFile(new File("C:\\screens\\temp\\latestscreen.jpg")); // 传输文件
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