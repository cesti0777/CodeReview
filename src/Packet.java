import java.io.Serializable;

/*
 * lang : language type , 0='C', 1='C++', 2='Python', 3='Java'
 * 
 * msgType : 
 * 			클라이언트가 어떤 종류의 메세지를 보내는지.
 * 			0은 에디터 타이핑, 1은 채팅, 2는 컴파일 ,3은 접속/종료메세지 
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
	private char ch;	 
	private boolean activateSignal; 
	private String sourceCode;
	
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
	public char getCh() {
		return ch;
	}
	public void setCh(char ch) {
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

	
}
