import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Client {
	public static void main(String[] args) {

		Socket sock = null;
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		boolean endFlag = false;
		String name;
		CodeReviewFrame frame;

		name = JOptionPane.showInputDialog(null, "log-in할 아이디를 입력하세요", "log in", JOptionPane.YES_OPTION);

		try {
			sock = new Socket("127.0.0.1", 11002);
			System.out.println("접속에 성공하였습니다.");

			oos = new ObjectOutputStream(sock.getOutputStream());
			ois = new ObjectInputStream(sock.getInputStream());
			frame = new CodeReviewFrame(oos,name);

			//접속자 id를 알려 주는 패킷
			Packet packet = new Packet();
			packet.setMsgType(3);
			packet.setId(name);
			oos.writeObject(packet);
			oos.flush();

			System.out.println("메세지 보내기 성공");

			InputThread it = new InputThread(sock, ois, frame);
			it.start();

		} catch (Exception e) {
			System.out.println(e);

		}
	}
}

class InputThread extends Thread {
	private Socket sock;
	private ObjectInputStream ois;
	private CodeReviewFrame crf;

	InputThread(Socket sock, ObjectInputStream ois, CodeReviewFrame crf) {
		this.sock = sock;
		this.ois = ois;
		this.crf = crf;
	}

	public void run() {

		Packet packet;
		String code;

		try {

			while ((packet = (Packet) ois.readObject()) != null) {
				switch (packet.getMsgType()) {

				// 에디터 타이핑
				case 0:
					break;

				// 채팅
				case 1:
					break;

				// 컴파일
				case 2:
					crf.setConsole(packet.getSourceCode());
					break;
				case 3:
					crf.setPeople(packet.getId());
					break;

				}
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {

			try {
				if (ois != null) {
					ois.close();
				}
			} catch (Exception e) {

			}
			try {
				if (sock != null) {
					sock.close();
				}
			} catch (Exception e) {

			}
		}
	}
}
