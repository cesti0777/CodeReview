
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

public class Client extends Frame implements ActionListener{

	private TextField idTF = null;	
	private CardLayout cardLayout = null;	
	
	Socket sock=null;
	ObjectInputStream ois = null;
	ObjectOutputStream oos =null;
	boolean endFlag = false;
	String id = "seong";
	CodeReviewFrame frame;
	
	public Client(String ip){
		//전상우등장.
		super("채팅 클라이언트");
		cardLayout = new CardLayout();
		setLayout(cardLayout);
		Panel loginPanel = new Panel();
		loginPanel.setLayout(new BorderLayout());
		loginPanel.add("North", new Label("Input id, then enter   "));
		idTF = new TextField(20);
		idTF.addActionListener(this);
		Panel c = new Panel();
		c.add(idTF);
		loginPanel.add("Center", c);
		add("login", loginPanel);
		
		try{
			sock = new Socket(ip ,11002);
			System.out.println("접속에 성공했습니다.");
			
			oos = new ObjectOutputStream(sock.getOutputStream());
			ois = new ObjectInputStream(sock.getInputStream());

		}
		catch(Exception ex){
			System.out.println(ex);
			
		}		
		setSize(1440, 1000);	
		cardLayout.show(this, "login");	
		addWindowListener(new WindowAdapter(){	
			
		});			
		setVisible(true);			
	}	
	
	
	
	public static void main(String[] args) {
		if(args.length != 1){			
			System.out.println("사용법 : java WinChatClient ip");		
			System.exit(1);	
		}		
		new Client(args[0]);	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == idTF){		
			String id = idTF.getText();	
			if(id == null || id.trim().equals("")){	
				System.out.println("아이디를 다시 입력하여 주세요.");
				return;
			}	
			try{
				Packet packet = new Packet();
				packet.setMsgType(3);
				packet.setId(id);	
				oos.writeObject(packet);
				oos.flush();
				frame = new CodeReviewFrame(oos,id);
				setLayout(cardLayout);
				add("main", frame);
			}
			catch(Exception ex){
				System.out.println(ex);
				
			}		
			InputThread it = new InputThread(sock, ois, frame);
			it.start();	
			cardLayout.last(this);
		}			
		
	}
}

class InputThread extends Thread{
	private Socket sock;
	private ObjectInputStream ois;
	private CodeReviewFrame crf;
	
	InputThread(Socket sock, ObjectInputStream ois, CodeReviewFrame crf){
		this.sock = sock;
		this.ois = ois;
		this.crf = crf;
	}
	
	public void run(){
	
		Packet packet;
		String code;
		
		try {
			
			while((packet = (Packet)ois.readObject()) != null){
				switch(packet.getMsgType()){
				
				//에디터 타이핑 
				case 0:
					break;
					
				//채팅 
				case 1:
					break;
				
				//컴파일
				case 2:
					crf.setConsole(packet.getSourceCode());
					break;
				case 3 :
					crf.setPeople(packet.getId());
					break;
					
				}
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		finally{
			
			try{
				if(ois != null){
					ois.close();
				}
			}
			catch(Exception e){
				
			}
			try{
				if(sock != null){
					sock.close();
				}
			}
			catch(Exception e){
				
			}			
		}
	}
}
