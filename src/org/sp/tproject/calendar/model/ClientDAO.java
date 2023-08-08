package org.sp.tproject.calendar.model;
//client 테이블에 대한 CRUD만을 담당하기 위한 객체

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.sp.tproject.calendar.domain.Client;

import util.DBManager;

public class ClientDAO {
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user ="pomodoro";
	String password = "1234";
	DBManager dbManager;
	
	public ClientDAO(DBManager dbManager) {
		this.dbManager = dbManager;
	}
	//사용자 레코드 1건 등록
	//사용자 회원 가입 시 사용
	//이름/아이디/비밀번호/비밀번호 확인/닉네임/이메일 주소
	public int insert(Client client) {
		Connection con =null;
		PreparedStatement pstmt = null;
		
		int result = 0;	//DML 에 대한 성공, 실패 판당
		con = dbManager.connect();	
		if(con==null) {
			System.out.println("접속 실패");
		}
		StringBuilder sb = new StringBuilder();
		
		sb.append("insert into client(client_idx, name, id, pass, nickname, email)");
		sb.append(" values(seq_client.nextval, ?,?,?,?,?)");
		
		try {
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, client.getName());
			pstmt.setString(2, client.getId());
			pstmt.setString(3, client.getPass());
			pstmt.setString(4, client.getNickname());
			pstmt.setString(5, client.getEmail());
			
			result = pstmt.executeUpdate();	//DML 쿼리 실행
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt);
		}
		
		return result;
	}
	
	//사용자 아이디 중복확인
	public boolean  idDuplicateCheck(String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Boolean idcheck=false;

		con = dbManager.connect();
		if(con == null) {
			System.out.println("idDuplicateCheck 접속 실패");
		}else {
			StringBuilder sb = new StringBuilder();
			
			try {
				sb.append("select * from client where id=?");
				pstmt = con.prepareStatement(sb.toString());
				//바인드 변수 채워넣기
				pstmt.setString(1, id);
				
				//쿼리 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {	//커서를 한칸 이동시 true 가 반환된다면 레코드가 존재. 즉, 아이디 중복
					idcheck = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				dbManager.release(con, pstmt, rs);
			}
			
		}
		return idcheck;
	}
	
	//사용자 닉네임 중복확인
	public boolean nicknameDuplicateCheck(String nickname) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Boolean nicknamecheck = false;
		
		con = dbManager.connect();
		if(con==null) {
			System.out.println("nicknameDuplicateCheck 접속 실패");			
		}else {
			StringBuilder sb = new StringBuilder();
			
			try {
				sb.append("select * from client where nickname =?");
				pstmt = con.prepareStatement(sb.toString());
				//바인드 변수 채워넣기
				pstmt.setString(1, nickname);
				
				//쿼리 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {	//커서 한칸 내렸을 때, 참이라면 = 값이 있다면 = 닉네임 중복
					nicknamecheck = true;
				}
			} catch (SQLException e) {
			
				e.printStackTrace();
			}finally {
				dbManager.release(con, pstmt, rs);
			}
		}
		return nicknamecheck;
	}
	
	//사용자 로그인 메서드
	//사용자 레코드 1건 검색
	//사용자 로그인 시 사용
	public Client login(Client client) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Client dto = null;	//로그인 후, 해당 관리자 1사람 정보를 담기 위한 객체
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url,user,password);
			if(con == null) {
				System.out.println("접속 실패");
			}else {
				String sql = "select * from client where id=? and pass=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, client.getId());
				pstmt.setString(2, client.getPass());
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					dto = new Client();	//비어있는 dto 인스턴스 생성
					//채워넣기
					//이름,아이디,비밀번호,닉네임,이메일 주소
					dto.setClient_idx(rs.getInt("client_idx"));
					dto.setName(rs.getString("name"));
					dto.setId(rs.getString("id"));
					dto.setPass(rs.getString("pass"));
					dto.setNickname(rs.getString("nickname"));
					dto.setEmail(rs.getString("email"));
				}
			}
			
			
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt, rs);
		}

		return dto;
	}
	
	//사용자 아이디 인증(가입한 이름 및 메일인지 확인)
	public boolean findIdAuth(String name, String email) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result=false;	//반환할 boolean 값
		
		con = dbManager.connect();
		if(con==null) {
			System.out.println(" 아이디 찾기 접속 실패");			
		}else {
			StringBuilder sb = new StringBuilder();
			
			try {
				sb.append("select * from client where name=? and email=?");
				pstmt = con.prepareStatement(sb.toString());
				
				//바인드 변수 채워넣기
				pstmt.setString(1, name);
				pstmt.setString(2, email);
				
				//쿼리 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {	
					result = true;
				}
			} catch (SQLException e) {
			
				e.printStackTrace();
			}finally {
				dbManager.release(con, pstmt, rs);
			}
		}
		
		
		return result;
	}
	
	//사용자 아이디 찾기
	public String findId(String name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String yourid=null;	//반환할 사용자 id
		
		con = dbManager.connect();
		if(con==null) {
			System.out.println(" 아이디 찾기 접속 실패");			
		}else {
			StringBuilder sb = new StringBuilder();
			
			try {
				sb.append("select * from client where name =?");
				pstmt = con.prepareStatement(sb.toString());
				
				//바인드 변수 채워넣기
				pstmt.setString(1, name);
				
				//쿼리 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {	
					yourid = rs.getString("id");
				}
			} catch (SQLException e) {
			
				e.printStackTrace();
			}finally {
				dbManager.release(con, pstmt, rs);
			}
		}
		
		return yourid;
	}
	//사용자 비밀번호 찾기 인증
	public boolean findPassAuth(String name, String id,String email) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result=false;	//반환할 boolean 값
		
		con = dbManager.connect();
		if(con==null) {
			System.out.println(" 아이디 찾기 접속 실패");			
		}else {
			StringBuilder sb = new StringBuilder();
			
			try {
				sb.append("select * from client where name=? and id=? and email=?");
				pstmt = con.prepareStatement(sb.toString());
				
				//바인드 변수 채워넣기
				pstmt.setString(1, name);
				pstmt.setString(2, id);
				pstmt.setString(3, email);
				
				//쿼리 실행
				rs = pstmt.executeQuery();
				if(rs.next()) {	
					result = true;
				}
			} catch (SQLException e) {
			
				e.printStackTrace();
			}finally {
				dbManager.release(con, pstmt, rs);
			}
		}
		
		
		return result;
	}
	
	//사용자 비밀번호 변경
	public int changePass(String pass, String name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;	//DML  에 대한 성공, 실패 판단
		
		con = dbManager.connect();
		if(con == null) {
			System.out.println("사용자 비밀번호 변경 접속 실패");
		}
		StringBuilder sb = new StringBuilder();
		
		sb.append("update client set pass =? where name = ?");
		
		try {
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, pass);
			pstmt.setString(2, name);
			
			result = pstmt.executeUpdate();		//DML 쿼리 실행
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbManager.release(con, pstmt);
		}
		
		return result;
		
	}
}
