package org.sp.tproject.member.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.sp.tproject.calendar.model.ClientDAO;

import oracle.net.aso.p;
import util.DBManager;
import util.HashConverter;

public class FindPassResultPage extends FindPage{
	ClientDAO clientDAO;
	DBManager dbManager;
	FindMainPage findMainPage;
	JPanel p_center;
	JLabel yourpass;
	JPanel p_south;
	JPanel p_pass;
	JPanel p_pass_check;
	JPasswordField t_pass;
	JPasswordField t_pass_check;
	JLabel la_pass_check;
	JButton bt_findpass_login;
	
	String whois;	//누구의 비밀번호를 바꿀건지...
	
	HashConverter hashConverter;
	
	
	public FindPassResultPage(FindMainPage findMainPage) {
		this.findMainPage = findMainPage;
		dbManager = new DBManager();
		clientDAO = new ClientDAO(dbManager);
		p_center = new JPanel();
		yourpass = new JLabel("님의 비밀번호는 000 입니다.");
		p_south = new JPanel();
		p_pass = new JPanel();
		p_pass_check = new JPanel();
		t_pass = new JPasswordField("");
		t_pass_check = new JPasswordField("");
		la_pass_check = new JLabel();
		bt_findpass_login = new JButton("비밀번호 변경");
		
		hashConverter = new HashConverter();

		//스타일
		Dimension find_panel_d = new Dimension(850,120);
		Dimension find_text_d = new Dimension(600,80);
		Dimension la_d = new Dimension(800,20);
		Dimension find_bt = new Dimension(400,50);
		
		yourpass.setPreferredSize(new Dimension(1000,100));
		p_center.setPreferredSize(new Dimension(1000,300));
//		yourpass.setPreferredSize(la_d);
		p_pass.setPreferredSize(find_panel_d);
		p_pass_check.setPreferredSize(find_panel_d);
		t_pass.setPreferredSize(find_text_d);
		t_pass_check.setPreferredSize(find_text_d);
		bt_findpass_login.setPreferredSize(find_bt);
		la_pass_check.setPreferredSize(la_d);
		la_pass_check.setForeground(Color.RED);
		la_pass_check.setBackground(Color.BLUE);
		
//		yourpass.setHorizontalAlignment(JLabel.CENTER);
		p_center.setLocation(800, 500);
		
		p_pass_check.setLayout(new FlowLayout());
		

		//폰트
		Font la_font = new Font("goyang", Font.PLAIN, 30);
		t_pass.setFont(la_font);
		t_pass_check.setFont(la_font);
		bt_findpass_login.setFont(la_font);

		//부착
			
//		setLayout(new GridBagLayout());
		add(p_center);
//		p_center.add(yourpass);
		p_center.add(p_pass);
		p_center.add(p_pass_check);
		p_pass.add(t_pass);
		p_pass_check.add(t_pass_check);
		p_pass_check.add(la_pass_check);
		add(p_south, new BorderLayout().SOUTH);
		p_south.add(bt_findpass_login);
		bt_findpass_login.setEnabled(false);
		
		t_pass_check.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				passCheck();
			}
		});
		bt_findpass_login.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				changePass();
			}
		});

	}
	
	

	//비밀번호 일치 확인
	public boolean passCheck() {
		//사용자가 처음 입력한 비밀번호와 비밀번호 확인에 누름 비밀번호가 일치하는지 확인
		String pass = new String(t_pass.getPassword());
		String pass_check = new String(t_pass_check.getPassword());
		System.out.println(pass);
		System.out.println(pass_check);
		//비밀번호가 일치한다면..
		boolean ox = (pass == pass_check);
		//비밀번호가 일치한다면 true 반환
//		System.out.println(ox);
		if(pass.equals(pass_check)) {	
//			System.out.println("비밀번호 일치함");
			la_pass_check.setText("                                      비밀번호가 일치합니다.");
			bt_findpass_login.setEnabled(true);
		}else {
			la_pass_check.setText("                                      비밀번호가 일치하지 않습니다");
		}
		
		return ox;
		
	}
	//어떤 사용자의 비밀번호을 대입할건지...
	public String setPass(String who) {
		whois = who;
		System.out.println(whois);
		return whois;
	}
	//비밀번호 변경
	public void changePass() {
		System.out.println("비밀번호 변경 클릭");
		String who = setPass(whois);
		
		String pass = hashConverter.convertToHash(new String(t_pass.getPassword()));
		int result = clientDAO.changePass(pass,who);	//비밀번호 변경 메서드 호출
		if(result >0) {
			findMainPage.showFindPage(findMainPage.FINDPASSEND);
			System.out.println("업데이트 성공");
		}else {
			System.out.println("업데이트 안됨");
		}
		
	}
}
