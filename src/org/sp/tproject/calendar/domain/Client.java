package org.sp.tproject.calendar.domain;

//Client 테이블과 1:1 대응되는 DTO 목적으로 정의
public class Client {
	//이름/아이디/비밀번호/비밀번호 확인/닉네임/이메일 주소
	private int client_idx;
	private String name;
	private String id;
	private String pass;
	private String nickname;
	private String email;
	public int getClient_idx() {
		return client_idx;
	}
	public void setClient_idx(int client_idx) {
		this.client_idx = client_idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}