
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Server {

	public static void main(String[] args) {
		
		try{
			
			ServerSocket server = new ServerSocket(11002);
			System.out.println("접속을 기다립니다");
			HashMap hashMap = new HashMap();
			while(true){
				Socket sock = server.accept();
				System.out.println("클라이언트 접속");
				ServerThread thread = new ServerThread(sock, hashMap);
				thread.start();
			}	
		}
		catch(Exception e){
			System.out.println("server1");
			System.out.println(e);
		}
		System.out.println("접속종료");
	}
}

class ServerThread extends Thread{
	private Socket sock;
	private String id;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private HashMap hashMap;
	private boolean initFlag = false;
	
	ServerThread(Socket sock, HashMap hm){
		
		this.sock = sock;
		this.hashMap = hm; 

		try{
			
			ois = new ObjectInputStream(sock.getInputStream());
			oos = new ObjectOutputStream(sock.getOutputStream());
			
			Packet packet = (Packet)ois.readObject();
			id = packet.getId();
			
			System.out.println("접속한 사용자의 아이디는 " + id +"입니다 ");
			
			Packet broadcastPacket = new Packet();
			broadcastPacket.setMsgType(3);
			broadcastPacket.setId(id);
			broadcast(broadcastPacket);
			System.out.println("send broadcastPacket");
			
			synchronized(hashMap){
				hashMap.put(id,  oos);
			}
			initFlag = true;
		}
		catch(Exception e){
			System.out.println("server2");
			System.out.println(e);
		}
	}
	public void run(){
		
		String compiledResult;
		try{
			Object obj = null;
			
			while((obj = ois.readObject()) != null){
				
				Packet packet = (Packet)obj;
				switch(packet.getMsgType()){
				//에디터 타이핑 
					case 0:
						break;
						
					//채팅 
					case 1:
						break;
					
					//컴파일
					case 2:
						new CompileThread(packet, oos);
						break;
				}
			}
		}
		catch(Exception e){
			System.out.println("server1");
			System.out.println(e);
		}
		finally{
			synchronized(hashMap){
				hashMap.remove(id);
			}
			
			Packet endMessagePacket = new Packet();
			
			endMessagePacket.setSourceCode(id+"님이 종료하였습니다.");
			broadcast(endMessagePacket);
			try{
				if(sock != null){
					sock.close();
				}
			}
			catch(Exception e){
				System.out.println(e);
			}
			try{
				if(oos != null){
					oos.close();
				}
			}
			catch(Exception e){
				System.out.println(e);
			}
			try{
				if(ois != null){
					ois.close();
				}
			}
			catch(Exception e){
				System.out.println(e);
			}
		}
	}
	
	public void broadcast(Packet packet){
		synchronized(hashMap){
			Collection collection = hashMap.values();
			Iterator iter = collection.iterator();
			try{
				oos.writeObject(packet);
				oos.flush();
				
				while(iter.hasNext()){
					System.out.println("packet Message Type" + packet.getMsgType());
					ObjectOutputStream oos2 = (ObjectOutputStream)iter.next();
					oos2.writeObject(packet);
					oos2.flush();
				}
			}
			catch(Exception e){
				
			}
		}
	}
	public void CompileProcess(Packet packet){
		// 클라이언트로 부터 전달 받은 패킷에서 
		// 코드부분을 따로 파일로 저장
		
		
	}
}

class CompileThread extends Thread{
	
	CompileThread(Packet packet, ObjectOutputStream oos){
		
		String filePath = "/home/seong/Desktop/archive/test.txt";
		String renameFilePath = "/home/seong/Desktop/archive/test.c";
		String compileResultFilePath = "/home/seong/Desktop/archive/result.txt";
		
		File file = null;
		File renameFile = null;
		File resultFile = null;
		FileWriter fw = null;
		FileInputStream fis = null;
		byte[] buffer = new byte[512];
		int readcount = 0;
		String compiledResult="";
		boolean hasResult = false;
		

		String inputLine;
		StringBuffer stringBuffer;
		BufferedReader reader;
		
		try{
			
			//파일을 archive디렛터리에 txt파일형태로 저장한다.
			file = new File(filePath);
			renameFile = new File(renameFilePath);
			
			fw= new FileWriter(file, true);
			fw.write(packet.getSourceCode());
			fw.flush();
			fw.close();
			
			//txt파일을 .c파일로 이름을 변환한다.
			if(file.renameTo(renameFile)){
				System.out.println("성공적으로 변환하였습니다.");
			}
		
			//result 파일이 존재하는지 검사 
			while(!hasResult){
				
				resultFile = new File(compileResultFilePath);
				
				//존재하면  
				if(resultFile.exists()){
					stringBuffer= new StringBuffer();
					reader = new BufferedReader(new FileReader(compileResultFilePath));
				
					System.out.println("불러오는데 성공햇습니다.");
					fis = new FileInputStream(compileResultFilePath);
					while ((inputLine = reader.readLine()) != null){
					       stringBuffer.append(inputLine);
					}
					
					Packet resultPacket = new Packet();
					resultPacket.setMsgType(2);
					resultPacket.setSourceCode(stringBuffer.toString());
					oos.writeObject(resultPacket);
					oos.flush();
					hasResult = true;
				}
			}
		}
		catch(Exception e){
			
			System.out.println("myCode -> class : server, position : saveFile()");	
			System.out.println(e);
		}
	}
}