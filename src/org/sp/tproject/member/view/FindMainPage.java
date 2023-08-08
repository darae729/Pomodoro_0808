package org.sp.tproject.member.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.sp.tproject.calendar.model.ClientDAO;

import util.DBManager;

public class FindMainPage extends Depth1Page{
	ClientLoginPage clientLoginPage;
	ClientDAO clientDAO;
	DBManager dbManager;
	JPanel p_north;
	JPanel p_center;
	JButton bt_find_id;
	JButton bt_find_pass;
	String find_id_name=new String();
	
	//컨텐츠 페이지
	FindPage[] findPages;
	
	public static final int FINDID=0;
	public static final int FINDIDRESULT=1;
	public static final int FINDPASS=2;
	public static final int FINDPASSRESULT=3;
	public static final int FINDPASSEND=4;
	
	public FindMainPage(ClientLoginPage clientLoginPage) {
		this.clientLoginPage = clientLoginPage;
		p_north = new JPanel();
		p_center = new JPanel();
		bt_find_id = new JButton("아이디 찾기");
		bt_find_pass = new JButton("비밀번호 찾기");
		findPages = new FindPage[4];
		dbManager = new DBManager();
		clientDAO = new ClientDAO(dbManager);
		
		findPages = new FindPage[5];
		findPages[FINDID] = new FindIdPage(this);
		findPages[FINDIDRESULT] = new FindIdResultPage(clientLoginPage,this);
		findPages[FINDPASS] = new FindPassPage(this);
		findPages[FINDPASSRESULT] = new FindPassResultPage(this);
		//성공하면 로그인 페이지로 넘어가야 함
		findPages[FINDPASSEND] = new FindPassChange(clientLoginPage,this);
			
		
		//스타일
		Dimension bt_d = new Dimension(200,60);
		p_north.setPreferredSize(new Dimension(1100,80));
		p_center.setPreferredSize(new Dimension(1100,530));
		p_north.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY,1),""));
		bt_find_id.setPreferredSize(bt_d);
		bt_find_id.setBackground(Color.WHITE);
		bt_find_pass.setPreferredSize(bt_d);
		bt_find_pass.setBackground(Color.WHITE);
		
		
		//폰트

		Font find_bt_font = new Font("goyang",Font.BOLD, 20);
		bt_find_id.setFont(find_bt_font);
		bt_find_pass.setFont(find_bt_font);
		
		//조립
//		setLayout(new BorderLayout());
		
		add(p_north, BorderLayout.NORTH);
		add(p_center, BorderLayout.CENTER);
		
		for(int i=0; i<findPages.length; i++) {
			p_center.add(findPages[i]);
		}
		
//		p_north.setBackground(Color.RED);
//		p_center.setBackground(Color.GREEN);
		
		setLayout(new BorderLayout());
		add(p_north,new BorderLayout().NORTH);
		add(p_center);
		
		p_north.setLayout(new FlowLayout(FlowLayout.LEFT));
		p_north.add(bt_find_id);
		p_north.add(bt_find_pass);
		
		setVisible(true);
		setPreferredSize(new Dimension(1100,600));
		
		//아이디 찾기 버튼에 대한 이벤트 연결
		bt_find_id.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				bt_find_id.setBackground(Color.DARK_GRAY);
				bt_find_id.setForeground(Color.WHITE);
				bt_find_pass.setBackground(Color.WHITE);
				bt_find_pass.setForeground(Color.BLACK);
				System.out.println("아이디찾기 누름");
				showFindPage(0);
			}
		});
		bt_find_pass.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				bt_find_id.setBackground(Color.WHITE);
				bt_find_id.setForeground(Color.BLACK);
				bt_find_pass.setBackground(Color.DARK_GRAY);
				bt_find_pass.setForeground(Color.WHITE);
				showFindPage(2);
			}
		});

	}
	
	//페이지 전환처리
	public void showFindPage(int n) {
		for(int i=0; i<findPages.length; i++) {
			if(i == n) {
				findPages[i].setVisible(true); //보이게 처리
			}else {
				
				findPages[i].setVisible(false); //안 보이게 처리
			}
		}
	}
	
	
}
