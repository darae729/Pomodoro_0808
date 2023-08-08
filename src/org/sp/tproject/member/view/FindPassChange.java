package org.sp.tproject.member.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.sp.tproject.calendar.model.ClientDAO;

import util.DBManager;

public class FindPassChange extends FindPage{
	ClientLoginPage clientLoginPage;
	ClientDAO clientDAO;
	DBManager dbManager;
	JPanel p_center;
	JLabel yourid;
	JPanel p_south;
	JButton bt_findid_login;
	
	
	public FindPassChange(ClientLoginPage clientLoginPage, FindMainPage findMainPage) {
		dbManager = new DBManager();
		clientDAO = new  ClientDAO(dbManager);
		p_center = new JPanel();

		yourid = new JLabel("비밀번호가 성공적으로 변경되었습니다.");
		p_south = new JPanel();
		bt_findid_login = new JButton("로그인 하기");
		

		//스타일
		
		Dimension la_d = new Dimension(800,100);
		Dimension find_bt = new Dimension(400,50);
		
		yourid.setPreferredSize(new Dimension(1000,100));
		p_center.setPreferredSize(new Dimension(1000,300));
		yourid.setPreferredSize(la_d);
		bt_findid_login.setPreferredSize(new Dimension(find_bt));

		bt_findid_login.setHorizontalAlignment(JButton.CENTER);
		yourid.setHorizontalAlignment(JLabel.CENTER);
		p_center.setLocation(800, 500);
		p_south.setLocation(900, 600);

		//폰트
		Font la_font = new Font("goyang", Font.PLAIN, 30);
		yourid.setFont(la_font);
		bt_findid_login.setFont(la_font);

		//부착

		add(p_center);
		p_center.add(yourid);
		add(p_south,new BorderLayout().SOUTH);
		p_south.add(bt_findid_login);
		
		
		
		bt_findid_login.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println("로그인 하기 누름");
				clientLoginPage.showHideDepth1(clientLoginPage.LOGIN);
				
			}
		});

	}

	
}
