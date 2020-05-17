package it.polito.tdp.bar.model;

public class Model {
	
	private Simulator s = new Simulator();
	
	public void esegui() {
		s.run();
	}
	
	public int getClienti() {
		return s.getClienti();
	}
	
	public int getSoddisfatti() {
		return s.getSoddisfatti();
	}
	
	public int getInsoddisfatti() {
		return s.getInsoddisfatti();
	}

}
