package org.sp.tproject.member.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.sp.tproject.main.domain.Pomocount;
import org.sp.tproject.main.model.PomocountDAO;
import org.sp.tproject.main.view.MainFrame;

import util.DBManager;

//마이페이지 J테이블에 정보를 제공해주는 pomocount(+pomodae) 전용 컨트롤러
public class PomoModel extends AbstractTableModel{
	MainFrame mainFrame;
	
	PomocountDAO pomocountDAO;
	DBManager dbManager;
	List< Pomocount> list=new ArrayList< Pomocount>();
	String[] column= {"연도", "월", "일", "토마토 총합"};
	
	public PomoModel(MainFrame mainFrame) {
		this.mainFrame=mainFrame;
		
		dbManager=new DBManager();
		pomocountDAO=new PomocountDAO(dbManager);
	}
	
	public void getPomoList() {
		System.out.println(mainFrame.client);
		list=pomocountDAO.selectPomo(mainFrame.clientDTO); //여기서 클라이언트를 넘겨주기
	}
	

	@Override
	public int getRowCount() {
		
		return list.size();
		//return 7;
	}

	@Override
	public int getColumnCount() {
		
		return 4;
	}
	@Override
	public String getColumnName(int col) {
	
		return column[col];
	}
	

	@Override
	public Object getValueAt(int row, int col) {
		Pomocount pomocount=list.get(row);
		
		String value="";
		
		switch(col) {
		case 0: value=Integer.toString(pomocount.getPomodate().getYy());break;
		case 1: value=Integer.toString(pomocount.getPomodate().getMm());break;
		case 2: value=Integer.toString(pomocount.getPomodate().getDd());break;
		case 3: value=Integer.toString(pomocount.getTomato()); break;
		
		}
		
		return value;
	}
	
	

}
