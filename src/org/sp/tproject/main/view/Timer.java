package org.sp.tproject.main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.sp.tproject.main.domain.Pomocount;
import org.sp.tproject.main.domain.Pomodate;

import util.RoundedButton;
import util.StringManager;

public class Timer extends JPanel implements Runnable {
	MainFrame mainFrame;
	CurrentTime currentTime;
	PomoPan pan; // 토마토 판 : 25분 될때마다 토마토를 붙이기 위해
	ProgressBarPan barPan; //프로그레스바 판 : 프로그레스바와 타이머 연동을 위해

	JLabel la_worktap;
	JLabel la_break;

	JLabel label;
	RoundedButton bt_start;
	RoundedButton bt_pause;

	public static int sec;
	public static int min;

	Thread tt;

	public boolean flag = false; // 타이머를 제어하는 플래그
	//public boolean bt_startFlag=true; //재생버튼 플래그

	int timerType = 25; // 타이머 타입(25min/5min)을 결정하는 변수
	boolean startFlag;//쓰레드의 start 상태를 판단하는 변수  

	public Timer(PomoPan pan, CurrentTime currentTime, ProgressBarPan barPan, MainFrame mainFrame) {
		// setSec();
		this.pan = pan;
		this.currentTime = currentTime;
		this.barPan=barPan;
		this.mainFrame = mainFrame;

		String timer = StringManager.getNumString(min) + " : " + StringManager.getNumString(sec);
		la_worktap = new JLabel(" pomodoro");
		la_break = new JLabel(" break");
		label = new JLabel(timer);
		bt_start = new RoundedButton("▶");
		bt_pause = new RoundedButton("⏸");

		setLayout(new FlowLayout());
		// setLayout(new BorderLayout());
		setBorder(new LineBorder(Color.WHITE, 1));

		la_worktap.setPreferredSize(new Dimension(170, 25));
		la_worktap.setOpaque(true);
		la_worktap.setBackground(new Color(204, 051, 051));
		la_worktap.setFont(new Font("goyang", Font.BOLD, 20));

		la_break.setPreferredSize(new Dimension(170, 25));
		la_break.setOpaque(true);
		la_break.setBackground(new Color(051, 204, 102));
		la_break.setFont(new Font("goyang", Font.BOLD, 20));

		label.setPreferredSize(new Dimension(350, 150));
		label.setOpaque(true);
		label.setBackground(Color.WHITE);
		label.setFont(new Font("digital-7", Font.BOLD, 70));
		label.setHorizontalAlignment(JLabel.CENTER);

		add(la_worktap);
		add(la_break);
		add(label);
		add(bt_start);
		add(bt_pause);

		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(300, 250));
		setVisible(true);
		// setSize(300, 300);
		// setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 타이머를 동작할 쓰레드
		tt = new Thread(this);
		//tt.start();

		// 버튼과 타이머 쓰레드 start 연결
		bt_start.addActionListener(new ActionListener() { // 타이머 시작
			public void actionPerformed(ActionEvent e) {
				flag = true;
				System.out.println(barPan);
				//bt_start.setEnabled(!bt_startFlag);
				createDate(); //pomodate 테이블에 레코드 추가
				
				//if(barPan.barFlag==false) {
				
				//double ratio=(min/25)*100;
				
				barPan.startBar();
				//barPan.barFlag=true;
				//barPan.bb.start();
				//barPan.barFlag=true;
				//barPan.bar.setValue((int)ratio);
				//}
			}
		});
		// 쓰레드를 죽이는 것이 아닌 flag를 통해 제어
		bt_pause.addActionListener(new ActionListener() { // 타이머 일시정지
			public void actionPerformed(ActionEvent e) {
				flag = false;
				//bt_startFlag=true;
				barPan.pauseBar();
				//System.out.println(flag);
			}
		});

		// 라벨탭과 이벤트 연결
		la_worktap.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				label.setBackground(new Color(204, 051, 051));
				timerType = 25;
			}
		});
		la_break.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				label.setBackground(new Color(051, 204, 102));
				timerType = 5;
			}
		});

	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);

				if (flag) {
					setSec(timerType);
					label.setText(StringManager.getNumString(min) + " : " + StringManager.getNumString(sec));
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// pomodate 테이블에 오늘의 날짜 레코드를 추가
	public void createDate() {
		Pomodate pomodate = new Pomodate();
		int yy = currentTime.cal.get(Calendar.YEAR);
		int mm = currentTime.cal.get(Calendar.MONTH);
		int dd = currentTime.cal.get(Calendar.DATE);

		pomodate.setYy(yy);
		pomodate.setMm(mm + 1);
		pomodate.setDd(dd);
		//pomodate.setClient(mainFrame.client);
		pomodate.setClient(mainFrame.clientDTO);

		Pomodate dto = mainFrame.pomodateDAO.select(pomodate);
		
		
		// 중복된 날짜가 없다면
		if (dto == null) { //들어간 데이터가 기존에 없을 겨우 
			int pomodate_idx=mainFrame.pomodateDAO.insert(pomodate);
			mainFrame.pomodate=pomodate;
		}else { //들어간 데이터 있다면 , 그 데이터를 대입 
			mainFrame.pomodate=dto;
		}
		
		System.out.println("쓰레드 호출 직전의 pomodate는 "+mainFrame.pomodate.getPomodate_idx());
		
		
		if(startFlag==false) {
			tt.start();
			startFlag=true;
		}
		
	}

	// pomocount 테이블에 토마토 1개 레코드를 추가
	public void oneCount() {
		Pomocount pomocount=new Pomocount();
		
		pomocount.setTomato(1);
		System.out.println("pomodate_idx ="+mainFrame);
		pomocount.setPomodate(mainFrame.pomodate);
		//토마토정보 
		mainFrame.pomocountDAO.insert(pomocount);
		
	}

	public int setSec(int timerType) { // 초를 설정하는 메서드
		sec++;
		if (sec >= 59) {
			sec = 0;
			setMin(timerType);
		}

		return sec;
	}

	public int setMin(int timerType) { // 분을 설정하는 메서드
		min++;
		if (min == timerType) { // 25분이 되면
			min = 0;
			// 1) 타이머멈춤
			flag = false;

			if (timerType == 25) {
				// 2-1) timerType=25일 경우만 토마토 판에 토마토 1개 생성
				pan.addPomo();
				// 2-2) timerType=25일 경우 pomocount테이블에 insert
				oneCount();
			}
			
			if(timerType==5) {
				barPan.pauseBar();
			}
		}

		return min;
	}

}