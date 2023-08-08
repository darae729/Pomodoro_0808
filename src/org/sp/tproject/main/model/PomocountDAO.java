package org.sp.tproject.main.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sp.tproject.calendar.domain.Client;
import org.sp.tproject.main.domain.Pomocount;
import org.sp.tproject.main.domain.Pomodate;

import util.DBManager;

public class PomocountDAO {
	DBManager dbManager;
	Pomocount pomocount;
	
	public PomocountDAO(DBManager dbManager) {
		this.dbManager=dbManager;
		//this.pomocount=pomocount;
	}
	
	public int insert(Pomocount pomocount) {
		Connection con=null; 
		PreparedStatement pstmt=null; 
		
		int result=0; //insert문 성공 실패 여부
		
		con=dbManager.connect();
		
		StringBuilder sb=new StringBuilder();
		sb.append("insert into pomocount(pomocount_idx, tomato, pomodate_idx)");
		sb.append(" values(seq_pomocount.nextval, ?, ?)"); //토마토 수는 1로 해도 되지 않나..
		
		try {
			pstmt=con.prepareStatement(sb.toString());
			pstmt.setInt(1, pomocount.getTomato());
			System.out.println("pomocount insert 시 pomodate 는 "+pomocount.getPomodate());
			pstmt.setInt(2, pomocount.getPomodate().getPomodate_idx());
			
			result=pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(con, pstmt);
		}
		
		
		return result;
	}
	
	//일일 누적 토마토수 가져오기 (client, pomodate 조인?)
	public List selectPomo(Client client) { //호출하는 곳에서 client Dto를 넘겨주면 됨
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		List pomoList=new ArrayList<Pomodate>(); //뽑아온 토마토를 담을 리스트?
		
		con=dbManager.connect();
		
		StringBuilder sb=new StringBuilder(); 
		sb.append("select c.name, sum(p.tomato), d.yy, d.mm, d.dd ");
		sb.append(" from pomocount p, pomodate d, client c");
		sb.append(" where p.pomodate_idx=d.pomodate_idx and d.client_idx=c.client_idx");
		sb.append(" and c.client_idx=? ");
		sb.append(" group by c.name, d.yy, d.mm, d.dd order by d.dd desc");
		
		
		try {
			pstmt=con.prepareStatement(sb.toString());
			pstmt.setInt(1, client.getClient_idx());
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				//뭐에 담지요...? dto를 어떤걸 새로 만드는지.. client는 넘겨받아야하는게 아닌지
				Pomocount countDto=new Pomocount();
				Pomodate dateDto=new Pomodate();
				
				dateDto.setClient(client); //회원 정보를 넘겨받아야하는데...
				dateDto.setYy(rs.getInt("yy"));
				dateDto.setMm(rs.getInt("mm"));
				dateDto.setDd(rs.getInt("dd"));
				countDto.setPomodate(dateDto);
				countDto.setTomato(rs.getInt("sum(p.tomato)"));
				
				pomoList.add(countDto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(con, pstmt, rs);
		}
		
		return pomoList;
	}

}
