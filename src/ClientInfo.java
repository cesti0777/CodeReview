import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientInfo {
	private Socket socket;
	//private SharedWhiteboard server;			//���� ��ü�� ��ü�� ���� ����	
	private ObjectOutputStream out;		//��ü�� ����
	private ObjectInputStream in;		//��ü�� ����
	public ClientInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ClientInfo(Socket socket, ObjectOutputStream out, ObjectInputStream in) {
		super();
		this.socket = socket;
		this.out = out;
		this.in = in;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public ObjectOutputStream getOut() {
		return out;
	}
	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}
	public ObjectInputStream getIn() {
		return in;
	}
	public void setIn(ObjectInputStream in) {
		this.in = in;
	}
	@Override
	public String toString() {
		return "ClientInfo [socket=" + socket + ", out=" + out + ", in=" + in + "]";
	}
	
	
	

}
