package org.sp.tproject.main.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ProgressBarPan extends JPanel implements Runnable {
	JProgressBar bar;
	Thread bb;

	int num; // 프로그레스바 숫자
	boolean flag; //쓰레드 시작 플래그
	boolean barFlag = false; // 프로그레스바 재생&멈춤 flag
	

	public ProgressBarPan() {
		bar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);

		bar.setPreferredSize(new Dimension(240, 50));
		bar.setBackground(Color.WHITE);
		bar.setStringPainted(true);
		bar.setFont(new Font("goyang", Font.BOLD, 25));

		setLayout(new FlowLayout());

		add(bar);

		setPreferredSize(new Dimension(240, 400));
		setBackground(Color.WHITE);
		setVisible(true);

		bb = new Thread(this);
		// bb.start();
	}

	public void run() {
		
		while(true) {
			try {
				bb.sleep(14870);
				
				if(barFlag) {
					num++;
					bar.setValue(num);
					bar.setString(num+" %");
					
					if (num > 100) {
						num = 0;
						barFlag=false;
						bar.setValue(num);
						bar.setString(num+" %");
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	
		/*
		for (int i = num; i <= 100; i++) {
			if (!barFlag)
				break;
			try {
				bb.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			bar.setValue(i);
			num = i;
			bar.setString(i + " %");
		}

		
		num++; double ratio=(Timer.min/25)*100; 
		double ratio=(num/25)*100; bar.setValue((int)ratio);
		
		 */

	}

	//프로그레스바를 시작하는 메서드 -> 타이머 start 시 호출 
	public void startBar() {
		if(barFlag==false) {
			barFlag = true;
			
			if(flag==false) { //현재 상태가 
			bb.start();
			flag=true;
			}
		}
	}

	// 프로그레스바를 멈추는 메서드 -> 타이머 일시정지 시 호출
	public void pauseBar() {
		barFlag = false;
	}

}
