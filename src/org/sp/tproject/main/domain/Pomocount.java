package org.sp.tproject.main.domain;

public class Pomocount {
	private int pomocount_idx;
	private int tomato;
	//언제의 기록?
	private Pomodate pomodate;
	
	
	public int getPomocount_idx() {
		return pomocount_idx;
	}
	public void setPomocount_idx(int pomocount_idx) {
		this.pomocount_idx = pomocount_idx;
	}
	public int getTomato() {
		return tomato;
	}
	public void setTomato(int tomato) {
		this.tomato = tomato;
	}
	public Pomodate getPomodate() {
		return pomodate;
	}
	public void setPomodate(Pomodate pomodate) {
		this.pomodate = pomodate;
	}
	
	
}
