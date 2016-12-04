import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import java.lang.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class CodeReviewFrame extends JFrame{
	
	//패널 컴포넌트 
	private JTextArea editor;
	private JTextArea console;
	private JTextArea people;
	private JTextArea chattingBox;
	
	//툴바 컴포넌트 
	private JToolBar toolbar;
	private JComboBox combo;
	private JButton newButton ;
	private JButton openButton ;
	private JButton saveButton;
	private JButton saveAsButton;
	private JButton compileButton;
	private JButton editActive;
	private JButton editDeactive;
	private JTextField chatInput;

	public JTextField getChatInput() {
		return chatInput;
	}

	public void setChatInput(JTextField chatInput) {
		this.chatInput = chatInput;
	}
	//서버와 입출력을 할 소켓정보
	ObjectOutputStream oos ;
	
	//Client클래스로부터 날라온 id
	private String id;
	
	public CodeReviewFrame(ObjectOutputStream oos, String id){
		//id연결
		this.id=id;
		//소켓연결 
		try{
			this.oos = oos;
		}
		catch(Exception e){
			
		}
		
		setTitle("CodeReview");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		//Editor, Console, People, chatting이 있는 패
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(null);

		editor = new JTextArea();
		console = new JTextArea();
		people = new JTextArea();
		chattingBox = new JTextArea();
		
		JScrollPane sp_editor = new JScrollPane(editor);;
		JScrollPane sp_console = new JScrollPane(console);
		JScrollPane sp_people = new JScrollPane(people);
		JScrollPane sp_chattingBox = new JScrollPane(chattingBox);
		
		// ---ScrollPane--
		
		//Editor
		sp_editor.setLocation(30,50);
		sp_editor.setSize(900,580);

		//Console
		sp_console.setLocation(30, 670);
		sp_console.setSize(900, 250);
		
		//People
		sp_people.setLocation(960, 50);
		sp_people.setSize(450, 300);
		
		//ChattingBox
		sp_chattingBox.setLocation(960, 400);
		sp_chattingBox.setSize(450, 480);

		centerPanel.add(sp_editor, BorderLayout.CENTER);
		centerPanel.add(sp_people);
		centerPanel.add(sp_console, BorderLayout.CENTER);
		centerPanel.add(sp_chattingBox);
		
		//Chatting Input Box
		chatInput = new JTextField(20);
		chatInput.setLocation(960, 890);
		chatInput.setSize(450, 30);
		centerPanel.add(chatInput);
		
		//Label
		JLabel editorLabel = new JLabel("Editor");
		JLabel consoleLabel = new JLabel("Console");
		JLabel peopleLabel = new JLabel("People");
		JLabel chattingBoxLabel = new JLabel("Chatting");
		
		editorLabel.setFont(new Font(editorLabel.getName(), Font.PLAIN, 20));
		editorLabel.setSize(200, 20);
		editorLabel.setLocation(30, 30);
		
		consoleLabel.setFont(new Font(consoleLabel.getName(), Font.PLAIN, 20));
		consoleLabel.setSize(200, 20);
		consoleLabel.setLocation(30, 650);
		
		peopleLabel.setFont(new Font(peopleLabel.getName(), Font.PLAIN, 20));
		peopleLabel.setSize(100, 20);
		peopleLabel.setLocation(960, 30);
		
		chattingBoxLabel.setFont(new Font(chattingBoxLabel.getName(), Font.PLAIN, 20));
		chattingBoxLabel.setSize(200, 20);
		chattingBoxLabel.setLocation(960,380);
			
		centerPanel.add(editorLabel);
		centerPanel.add(peopleLabel);
		centerPanel.add(consoleLabel);
		centerPanel.add(chattingBoxLabel);

		// 여기까지가 패널 디자인
		//--------------------------------------------------------------------//
		
		
		toolbar = new JToolBar();
		
		newButton = new JButton("New");
		openButton = new JButton("Open");
		saveButton = new JButton("Save");
		saveAsButton = new JButton("Save As");
		compileButton = new JButton("Compile");
		editActive = new JButton("Activate");
		editDeactive = new JButton("Deactivate");

		JLabel label = new JLabel("Editor");
		
		toolbar.add(newButton);
		toolbar.add(openButton);
		toolbar.add(saveButton);
		toolbar.add(saveAsButton);
		toolbar.addSeparator();
		toolbar.addSeparator();
		toolbar.addSeparator();
		
		toolbar.add(label);
		toolbar.add(editActive);
		toolbar.add(editDeactive);
		toolbar.addSeparator();
		toolbar.addSeparator();
		toolbar.addSeparator();
		
		combo = new JComboBox();
		combo.addItem("C");
		combo.addItem("C++");
		combo.addItem("Python");
		combo.addItem("Java");
		toolbar.add(combo);
		toolbar.addSeparator();
		toolbar.add(compileButton);

		for(int i = 0; i<200; ++i){
			toolbar.addSeparator();
		}		
		//여기까지 툴바 디자인
		//--------------------------------------------------------------------//
		getContentPane().add(toolbar, BorderLayout.NORTH);
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		
		addListener();
		setSize(1440, 1000);
		setVisible(true);
	}
	
	public void addListener(){
		//active버튼 클릭
				this.editActive.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						Packet packet = new Packet();
						packet.setId(id);//지빈이가 입력한 userId 들어가야할 부분.
						packet.setMsgType(4);
						packet.setActivateSignal(true);
						
						try {
							oos.writeObject(packet);
							oos.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
					}
					
				});
				//deactive버튼 클릭
				this.editDeactive.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						Packet packet = new Packet();
						packet.setId(id);//지빈이가 입력한 userId 들어가야할 부분.
						packet.setMsgType(5);
						packet.setActivateSignal(false);
						
						try {
							oos.writeObject(packet);
							oos.flush();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
					
				});

		this.compileButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Packet packet = new Packet();
				packet.setMsgType(2);
				packet.setSourceCode(getEditor());
				packet.setLang(getCombo().getSelectedIndex());
				
				
				try{
					//컴파일할 파일정보 보내기
					oos.writeObject(packet);
					oos.flush();
				}
				catch(Exception e1){
					System.out.println(e1);
				}
				
				//TO DO : 서버로 보내기
			}
		});
	}
	public JToolBar getToolbar() {
		return toolbar;
	}
	public void setToolbar(JToolBar toolbar) {
		this.toolbar = toolbar;
	}
	public JComboBox getCombo() {
		return combo;
	}
	public void setCombo(JComboBox combo) {
		this.combo = combo;
	}
	public String getEditor() {
		return editor.getText();
	}
	public void setEditor(String str) {
		this.editor.append(str);
	}
	public String getConsole() {
		return console.getText();
	}
	public void setConsole(String str) {
		this.console.append(str);;
	}
	public String getPeople() {
		return people.getText();
	}
	public void setPeople(String people) {
		this.people.append(people);;
	}
	public void setChattingBox(String str) {
		this.chattingBox.append(str);;
	}
}
