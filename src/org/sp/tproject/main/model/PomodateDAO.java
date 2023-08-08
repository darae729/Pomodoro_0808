package org.sp.tproject.main.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.sp.tproject.main.domain.Pomodate;
import org.sp.tproject.main.view.MainFrame;

import util.DBManager;

public class PomodateDAO {
	MainFrame mainFrame;
	DBManager dbManager;
	Pomodate pomodate;
	
	public PomodateDAO(DBManager dbManager) {
		this.dbManager=dbManager;
		//this.pomodate=pomodate;
	}
	
	//이미 중복된 일정이 잇는지 여부 판단 
	public Pomodate select(Pomodate pomodate) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;		
		Pomodate dto=null; 
		
		con=dbManager.connect();
		
		String sql="select * from pomodate where yy=? and mm=?  and dd=? and client_idx=?";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, pomodate.getYy());
			pstmt.setInt(2, pomodate.getMm());
			pstmt.setInt(3, pomodate.getDd());
			pstmt.setInt(4, pomodate.getClient().getClient_idx());
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new Pomodate();
				dto.setPomodate_idx(rs.getInt("pomodate_idx"));
				dto.setYy(rs.getInt("yy"));
				dto.setMm(rs.getInt("mm"));
				dto.setDd(rs.getInt("dd"));
				dto.setClient(pomodate.getClient());
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(con, pstmt, rs);
		}
		
		return dto;
	}
	
	
	public int insert(Pomodate pomodate) {
		Connection con=null; 
		PreparedStatement pstmt=null; 
		ResultSet rs=null;
		int result=0; //insert문 성공 실패 여부
		
		con=dbManager.connect();
		String sql="insert into pomodate(pomodate_idx, yy, mm, dd, client_idx)";
		sql+=" values(seq_pomodate.nextval, ?, ?, ?, ?)";
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, pomodate.getYy());
			pstmt.setInt(2, pomodate.getMm());
			pstmt.setInt(3, pomodate.getDd());
			pstmt.setInt(4, pomodate.getClient().getClient_idx());
			
			result=pstmt.executeUpdate(); //실행
			
			if(result>0) {
				sql="select seq_pomodate.currval as pomodate_idx from dual";
				pstmt=con.prepareStatement(sql);
				rs=pstmt.executeQuery();
				
				if(rs.next()) {
					result=rs.getInt("pomodate_idx");
					pomodate.setPomodate_idx(result);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(con, pstmt, rs);
		}
		
		return result;
	}

}
