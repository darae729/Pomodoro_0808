package org.sp.tproject.member.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.sp.tproject.main.view.MainFrame;
import org.sp.tproject.member.controller.PomoModel;

public class PomocountMyPage extends JPanel {
	MainFrame mainFrame;

	JTable table;
	JScrollPane scroll;
	PomoModel model;

	public PomocountMyPage(MainFrame mainFrame) {
		this.mainFrame = mainFrame;

		model = new PomoModel(mainFrame);
		
		//model.getPomoList(); // db가져오기
		
		table = new JTable(model);
		scroll = new JScrollPane(table);

		// 조립
		add(scroll);

		setPreferredSize(new Dimension(500, 600));
		setBackground(Color.WHITE);

	}

	public void getList() {
		model.getPomoList();
		model.fireTableDataChanged();
	}
}
