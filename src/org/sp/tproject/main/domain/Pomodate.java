package org.sp.tproject.main.domain;

import org.sp.tproject.calendar.domain.Client;

public class Pomodate {
	private int pomodate_idx;
	private int yy;
	private int mm;
	private int dd;
	//누가?
	private Client client;
	
	
	public int getPomodate_idx() {
		return pomodate_idx;
	}
	public void setPomodate_idx(int pomodate_idx) {
		this.pomodate_idx = pomodate_idx;
	}
	public int getYy() {
		return yy;
	}
	public void setYy(int yy) {
		this.yy = yy;
	}
	public int getMm() {
		return mm;
	}
	public void setMm(int mm) {
		this.mm = mm;
	}
	public int getDd() {
		return dd;
	}
	public void setDd(int dd) {
		this.dd = dd;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	
}
