import java.io.Serializable;

/*
 * lang : language type , 0='C', 1='C++', 2='Python', 3='Java'
 * 
 * msgType : 
 * 			클라이언트가 어떤 종류의 메세지를 보내는지.
 * 			1은 채팅, 2는 컴파일 ,3은 접속/종료메세지 ,4은 에디터 타이핑 권한 요청, 5는 에디터 타이핑 후 권한 반납 요청 및 타이핑한 메시지가 전체클라이언트에게 전송되어짐.,
 * 			6은 누군가 에디터창에서 수정하고 수정완료해서 수정된 내용을 다른 사람들한테 보내라는 종류의 메시지
 * 
 * id : 
 * 		에디터창 활성화 비 활성화시 보낸사용자의 id
 * 
 * ch : 
 * 		에디터 창에서 타이핑한 문자
 * 
 * activeSignal : 
 * 		1이면 에디터 활성화, 0이면 비활성화
 * 
 * sourceCode : 
 * 		컴파일 할 소스코드 
 */

public class Packet implements Serializable{

	private int lang;
	private int msgType ;
	private String id;	
	private String ch;	 
	private boolean activateSignal; 
	private String sourceCode;
	
	public Packet() {
		// TODO Auto-generated constructor stub
	}
	
	public Packet(int lang, int msgType, String id, char ch, boolean activateSignal, String sourceCode) {
		this.lang = lang;
		this.msgType = msgType;
		this.id = id;
		this.ch = ch;
		this.activateSignal = activateSignal;
		this.sourceCode = sourceCode;
	}
	
	public int getLang() {
		return lang;
	}
	public void setLang(int lang) {
		this.lang = lang;
	}
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCh() {
		return ch;
	}
	public void setCh(String ch) {
		this.ch = ch;
	}
	public boolean isActivateSignal() {
		return activateSignal;
	}
	public void setActivateSignal(boolean activateSignal) {
		this.activateSignal = activateSignal;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	@Override
	public String toString() {
		return "Packet [lang=" + lang + ", msgType=" + msgType + ", id=" + id + ", ch=" + ch + ", activateSignal="
				+ activateSignal + ", sourceCode=" + sourceCode + "]";
	}
	

	
}
