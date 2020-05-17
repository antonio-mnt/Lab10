package it.polito.tdp.bar.model;

import java.time.LocalTime;

public class Event implements Comparable<Event> {
	
	public enum EventType {
		ARRIVO_GRUPPO_CLIENTI, USCITA_GRUPPO_CLIENTI
	}
	
	private LocalTime time;
	private EventType type;
	private int n;
	private int numTavolo;
	
	public Event(LocalTime time, EventType type) {
		super();
		this.time = time;
		this.type = type;
		this.numTavolo = 0;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	@Override
	public int compareTo(Event other) {
		return this.getTime().compareTo(other.time);
	}

	public int getN() {
		return n;
	}

	public void setN() {
		double i = Math.random();
    	this.n = (int) (i*10)+1;
	}

	@Override
	public String toString() {
		return "Event [time=" + time + ", type=" + type + ", n=" + n + "]";
	}

	public int getNumTavolo() {
		return numTavolo;
	}

	public void setNumTavolo(int numTavolo) {
		this.numTavolo = numTavolo;
	}

	
	
	
	
	
	
	

}
