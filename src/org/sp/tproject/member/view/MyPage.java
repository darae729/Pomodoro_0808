package org.sp.tproject.member.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import org.sp.tproject.main.view.MainFrame;
import org.sp.tproject.main.view.Page;

public class MyPage extends Page{
	MainFrame mainFrame;
	JLabel la_westTitle;
	JLabel la_eastTitle;
	JPanel p_west;
	JPanel p_east; //회원별 일일 토마토 획득 수를 보여줄 테이블(pomocountMyPage)을 부착할 오른쪽 패널
	
	public PomocountMyPage pomocountMyPage; 
		
	public MyPage(MainFrame mainFrame) {
		this.mainFrame=mainFrame;
		
		la_westTitle=new JLabel();
		la_eastTitle=new JLabel();
		p_west=new JPanel();
		p_east=new JPanel();
		pomocountMyPage=new PomocountMyPage(mainFrame);
		
		//스타일
		setLayout(new BorderLayout());
		
		Dimension d1=new Dimension(300, 50);
		la_westTitle.setPreferredSize(d1);
		la_eastTitle.setPreferredSize(d1);
		Dimension d2=new Dimension(600, 700);
		p_west.setPreferredSize(d2);
		p_east.setPreferredSize(d2);
		
		la_eastTitle.setText("나의 일일 획득 토마토");
		la_eastTitle.setFont(new Font("goyang", Font.BOLD, 20));
		la_eastTitle.setHorizontalAlignment(JLabel.CENTER);
		la_eastTitle.setOpaque(true);
		la_eastTitle.setBackground(Color.WHITE);
		//la_westTitle.setText("To-do 달성도");
		
		p_west.setBackground(Color.WHITE);
		p_west.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		p_east.setBackground(Color.WHITE);
		p_east.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		
		//조립
		p_east.add(la_eastTitle, BorderLayout.NORTH);
		add(p_west, BorderLayout.WEST);
		add(p_east, BorderLayout.EAST);
		
		p_east.add(pomocountMyPage);
	
	}

}