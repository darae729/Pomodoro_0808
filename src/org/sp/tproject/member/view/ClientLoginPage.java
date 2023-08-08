package org.sp.tproject.member.view;

import java.awt.FlowLayout;

import javax.swing.JFrame;

import org.sp.tproject.main.view.MainFrame;

public class ClientLoginPage extends JFrame{
	MainFrame mainFrame;
	Depth1Page[] pages;	//클라이언트의 로그인 및 아이디/비밀번호 페이지
	public static final int LOGIN=0;
	public static final int FIND=1;
	
	public ClientLoginPage(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		pages = new Depth1Page[2];
		pages[LOGIN] = new LoginForm(mainFrame, this);
		pages[FIND] = new FindMainPage(this);
		
		setLayout(new FlowLayout());
	
		for(int i=0; i<pages.length; i++) {
			add(pages[i]);
		}
		
		pack();
		
		if(mainFrame.clientDTO==null) {
			setVisible(true);
		}
		setLocationRelativeTo(null);
		
		showHideDepth1(LOGIN);
		
	}
	
	//화면전환(페이지전환)
	//pages[0] : 로그인
	//pages[1] : 아이디/비밀번호 찾기
	public void showHideDepth1(int n) {
		for(int i=0; i<pages.length; i++) {
			if(i==n) {
				pages[i].setVisible(true);
			}else {
				pages[i].setVisible(false);
			}
		}
		pack();
	}
	
	//메인페이지를 보이게 함
	public void showMain() {
		System.out.println("showmain에서 "+ mainFrame);
		mainFrame.setVisible(true);
		this.setVisible(false);
	}
	

}
