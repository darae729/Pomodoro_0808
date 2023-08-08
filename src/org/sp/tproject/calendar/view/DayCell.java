package org.sp.tproject.calendar.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

//요일을 표현할 박스 정의
public class DayCell extends Cell{
	
	public DayCell(Color color, int width, int height) {
		super(color, width, height);
		
		la_title.setFont(new Font("", Font.BOLD, 14));
		la_title.setForeground(Color.WHITE);
	}
}