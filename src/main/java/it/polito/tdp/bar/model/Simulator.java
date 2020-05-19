package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

import it.polito.tdp.bar.model.Event.EventType;

public class Simulator {
	
	// coda degli eventi
	private PriorityQueue<Event> queue = new PriorityQueue<>();
	
	// parametri di simulazione
	private Map<Integer,Integer> tavoli = new TreeMap<>();
	//private Duration T_IN  = Duration.of(10, ChronoUnit.MINUTES);
	private int T_IN = 10;
	//private final LocalTime oraApertura = LocalTime.of(8, 00);
	private final int oraApertura = 0;
	
	private final int numEventi = 2000;
	
	// modello del mondo
	private Map<Integer,Integer> tavoliRimasti;
	
	// valori da calcolare
	private int clienti;
	private int soddisfatti;
	private int insoddisfatti;
	
	
	// metodi per impostare i parametri
	public void setTavoli() {
		this.tavoli.put(4,5);
		this.tavoli.put(6,4);
		this.tavoli.put(8,4);
		this.tavoli.put(10,2);
	}
	
	public void setFrequenzaArrivi() {
		double i = Math.random();
    	int x = (int) (i*10)+1;
    	//this.T_IN = Duration.of(x, ChronoUnit.MINUTES);
    	this.T_IN = x;
	}
	
	
	// metodi per restituire i risultati
	public int getClienti() {
		return clienti;
	}

	public int getSoddisfatti() {
		return soddisfatti;
	}

	public int getInsoddisfatti() {
		return insoddisfatti;
	}
	
	// simulazione vera e proprio
	public void run() {
		// preparazione iniziale (mondo + coda eventi)
		setTavoli();
		tavoliRimasti = new TreeMap<>(tavoli);
		//System.out.println(tavoliRimasti);//
		this.clienti = 0;
		this.soddisfatti = 0;
		this.insoddisfatti = 0;
		
		this.queue.clear();
		//LocalTime orarioArrivo = this.oraApertura;
		int orarioArrivo = this.oraApertura;
		//System.out.println(orarioArrivo+"");
		for(int i = 0; i<=numEventi; i++) {
			setFrequenzaArrivi();
			//orarioArrivo = orarioArrivo.plus(this.T_IN);
			orarioArrivo = orarioArrivo + this.T_IN;
			Event e = new Event(orarioArrivo,EventType.ARRIVO_GRUPPO_CLIENTI);
			e.setN();
			//System.out.println(e+" "+orarioArrivo+" "+this.T_IN);//
			queue.add(e);
		}
		
		// esecuzione del ciclo di simulazione
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			//System.out.println(e);
			processEvent(e);
		}
		
		
		
		
	}

	private void processEvent(Event e) {
		
		switch(e.getType()) {
		
		case ARRIVO_GRUPPO_CLIENTI:
			this.clienti+=e.getN();
			boolean flag = false;
			
			for(Integer tav: this.tavoli.keySet()) {
				
				double per = tav/2;
				if(e.getN()>=per && e.getN()<=tav && this.tavoliRimasti.get(tav)>0 && flag == false) {
					flag = true;
					this.tavoliRimasti.replace(tav, this.tavoliRimasti.get(tav)-1);
						
					Event nuovo = new Event(e.getTime()+durata(),EventType.USCITA_GRUPPO_CLIENTI);
					nuovo.setNumTavolo(tav);
					this.queue.add(nuovo);
					this.soddisfatti+=e.getN();
				}
			}
			
			if(flag==false) {
				if(calcolaTolleranza()==false) {
					this.insoddisfatti+=e.getN();
				}else {
					this.soddisfatti+=e.getN();
				}
			}
			
			break;
		
		case USCITA_GRUPPO_CLIENTI:
			
			Integer numTav = e.getNumTavolo();
			this.tavoliRimasti.replace(numTav, this.tavoliRimasti.get(numTav)+1);
			
			
			
			break;
		
		}
		
	}
	
	
	public int durata() {
		int i = (int) (Math.random()*100 + 1);
    	int j =  (int) (((i)/100.0 + 1)*60);
    	
    	//return Duration.of(j, ChronoUnit.MINUTES);
    	return j;
	}
	
	public boolean calcolaTolleranza() {
		
		int i = (int) (Math.random()*10);
		
		if(i==0) {
			return false;
		}else {
			return true;
		}
		
	}
	
	
	
	
	
	

}
