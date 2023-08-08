package org.sp.tproject.main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Scrollbar;
import java.net.MalformedURLException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

//메인에 배치될 컨텐츠들을 담을 페이지
//플레이어, 투두리스트 | 타이머, 프로그레스바 | 현재시간, 뽀모도로  
public class MainPage extends Page{
	MainFrame mainFrame;
	
	JPanel p_west; //플레이어, 투두리스트를 담을 왼쪽 패널
	JPanel p_center; //타이머, 프로그레스바를 담을 중앙 패널
	JPanel p_east; //현재시간, 토마토판을 담을 오른쪽 패널
	
	MusicPlayer mp; // music player
    TodoList todo; // to-do list
	CurrentTime ct; //현재 시간
	Timer timer; //타이머
	ProgressBarPan barPan; //프로그레스바 판
	PomoTree pomoTree; //토마토 나무
	PomoPan pan; //토마토 판
	
	JScrollPane panscroll;
	BasicScrollBarUI scrollUI;
	
	public MainPage(MainFrame mainFrame) {
		this.mainFrame=mainFrame;
		
		p_west=new JPanel();
		p_center=new JPanel();
		p_east=new JPanel();
		
		//컨텐츠 생성
		mp = new MusicPlayer();
	    todo = new TodoList();
		ct=new CurrentTime();
		pan=new PomoPan();
		panscroll=new JScrollPane(pan);
		barPan=new ProgressBarPan();
		try {
			pomoTree=new PomoTree();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		timer=new Timer(pan, ct, barPan, mainFrame);
		
		//스타일
		setLayout(new BorderLayout());
		//p_east.setLayout(new BorderLayout());
		p_center.setLayout(new BorderLayout());
		
		Dimension d=new Dimension(400, 700);
		p_west.setPreferredSize(d);
		p_center.setPreferredSize(d);
		p_east.setPreferredSize(d);
		
		p_west.setBackground(Color.WHITE);
		p_west.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		p_center.setBackground(Color.WHITE);
		p_center.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		p_east.setBackground(Color.WHITE);
		p_east.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		
		//조립
		p_west.add(mp, BorderLayout.NORTH);
	    p_west.add(todo, BorderLayout.SOUTH);
		p_east.add(ct); //현재 시간을 오른쪽 영역 상단에 부착
		p_east.add(panscroll); //토마토 판을 오른쪽 영역 하단에 부착
		panscroll.setPreferredSize(new Dimension(280, 400));
		panscroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		//panscroll.setUI(BasicScrollBarUI scrollUI);
		
		p_center.add(timer, BorderLayout.NORTH); //타이머를 중앙 상단에 부착
		p_center.add(barPan); //프로그레스바를 중앙 하단에 부착
		p_center.add(pomoTree, BorderLayout.SOUTH); //토마토나무 이미지를 프로그레스바 아래에 부착
		
		add(p_west, BorderLayout.WEST);
		add(p_center);
		add(p_east, BorderLayout.EAST);
		
	}
}