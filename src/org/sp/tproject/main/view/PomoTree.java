package org.sp.tproject.main.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.MalformedURLException;

import javax.swing.JPanel;

public class PomoTree extends JPanel{
	Image image;
	
	public PomoTree() throws MalformedURLException{
		image=Toolkit.getDefaultToolkit().createImage("res/img/Growing Tomato Plant.gif");
		
		setPreferredSize(new Dimension(240, 350));
		setBackground(Color.WHITE);
		setVisible(true);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(image!=null) {
			g.drawImage(image, 70, 0, this);
			
		}
	}
}
