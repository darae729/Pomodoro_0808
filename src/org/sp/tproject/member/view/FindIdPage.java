package org.sp.tproject.member.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.sp.tproject.calendar.model.ClientDAO;

import util.DBManager;
import util.MailSender;

public class FindIdPage extends FindPage{
	FindMainPage findMainPage;
	JPanel p_center;
	JPanel p_name;
	JTextField t_name;
	JPanel p_email;
	JTextField t_email;
	JPanel p_auth;
	JTextField t_auth;
	
	JButton bt_email;
	JButton bt_auth;
	JPanel p_check;
	JButton bt_check;
	String random="1111";	//보낼 인증번호
	//아이디를 찾기위해 clientDAO 호출
	ClientDAO clientDAO;
	DBManager dbManager;
	//메일 관련
	MailSender mailSender;
	
	
	public FindIdPage(FindMainPage findMainPage) {
		
		p_center = new JPanel();
		p_name = new JPanel();
		t_name = new JTextField("이름을 입력하세요");
		p_email = new JPanel();
		t_email = new JTextField("이메일을 입력하세요");
		bt_email = new JButton("전송하기");
		p_auth = new JPanel();
		t_auth = new JTextField("인증번호를 입력하세요");
		bt_auth = new JButton("인증하기");
		p_check = new JPanel();
		bt_check = new JButton("아이디 찾기");
		
		dbManager = new DBManager();
		clientDAO = new ClientDAO(dbManager);
		//메일 관련
		mailSender = new MailSender();

		//폰트
		Font regist_text_font = new Font("goyang", Font.PLAIN, 30);
		Font find_bt_font = new Font("goyang", Font.BOLD, 20);
		
		t_name.setFont(regist_text_font);
		t_email.setFont(regist_text_font);
		t_auth.setFont(regist_text_font);
		bt_email.setFont(find_bt_font);
		bt_auth.setFont(find_bt_font);
		bt_check.setFont(find_bt_font);
		
		//스타일
		Dimension find_panel_d = new Dimension(850,120);
		Dimension find_text_d = new Dimension(600,80);
		Dimension find_bt_d = new Dimension(150,60);
		p_center.setPreferredSize(new Dimension(1500,600));
		p_name.setPreferredSize(find_panel_d);
		t_name.setPreferredSize(find_text_d);
		p_email.setPreferredSize(find_panel_d);
		t_email.setPreferredSize(find_text_d);
		bt_email.setPreferredSize(find_bt_d);
		p_auth.setPreferredSize(find_panel_d);
		t_auth.setPreferredSize(find_text_d);
		bt_auth.setPreferredSize(find_bt_d);
		p_check.setPreferredSize(find_panel_d);
		bt_check.setPreferredSize(new Dimension(250,60));
		
		setLayout(new FlowLayout());
		
		
		//부착
		add(p_center, BorderLayout.CENTER);
		p_name.setLayout(new FlowLayout(FlowLayout.LEFT));
		p_email.setLayout(new FlowLayout(FlowLayout.LEFT));
		p_auth.setLayout(new FlowLayout(FlowLayout.LEFT));
		add(p_center);
//		p_center.setBackground(Color.DARK_GRAY);
		p_center.add(p_name);
		p_name.add(t_name);
		p_center.add(p_email);
		p_email.add(t_email);
		p_email.add(bt_email);
		p_center.add(p_auth);
		p_auth.add(t_auth);
		p_auth.add(bt_auth);
		p_center.add(p_check);
		p_check.add(bt_check);
		
		//인증완료 전에는 아이디 찾기 버튼 비활성화
		bt_check.setEnabled(false);
		
		
		bt_email.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				clickSendBt();
			}
		});
		bt_auth.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				clickAuthBt();
			}
		});
		bt_check.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println("아이디 찾기 클릭"+t_name.getText());
				findMainPage.showFindPage(findMainPage.FINDIDRESULT);
				FindIdResultPage findIdResultPage=(FindIdResultPage)findMainPage.findPages[findMainPage.FINDIDRESULT];
				findIdResultPage.setId(t_name.getText());
				System.out.println(t_name.getText());
			}
			
		});
	}
	//전송 버튼 클릭하면...
	public void clickSendBt() {
		random=makeRandom();
		boolean check = clientDAO.findIdAuth(t_name.getText(), t_email.getText());
		System.out.println(t_name.getText()+t_email.getText());
		if(check == true) {
			boolean flag = mailSender.send(t_email.getText(), "Pomo 인증번호", "인증번호는 "+random+" 입니다.");
		}else {
			JOptionPane.showMessageDialog(this, "이름 및 이메일을 확인해주세요");
		}
		
	}
	//인증버튼 클릭하면...
	public void clickAuthBt() {
		System.out.println("인증 클릭");
		System.out.println(random);
		if(random.equals(t_auth.getText())) {
			System.out.println("인증완료");
			//인증완료 후에는 아이디 찾기 버튼 비활성화
			bt_check.setEnabled(true);
		}
		
	}
	
	//랜덤 문자열 생성
	public String makeRandom() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();

		String generatedString = random.ints(leftLimit,rightLimit + 1)
		  .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
		  .limit(targetStringLength)
		  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		  .toString();

		System.out.println(generatedString);
		return generatedString;
	}
	
	
	
}
