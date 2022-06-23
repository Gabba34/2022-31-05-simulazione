package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.nyc.model.Event.EventType;


public class Simulator {

	// Dati in ingresso
	Graph<Borough, DefaultWeightedEdge> graph;
	List<Borough> boroughs;
	Borough startBorough;
	int tech;
	
	
	// Dati in uscita
	int duration;
	List<Integer> revisioned; // nr. hotspot revisioned by i-tech (0 to tech-1)
	
	
	// Modello del mondo
	List<Borough> boroughsLeft; // boroughs left (excepted current)
	Borough currentBorough;
	int hsLeft; // hs to be revised
	int techOccupied; // when 0 move to other borrough
	
	
	// Coda degli eventi
	PriorityQueue<Event> queue;

	public Simulator(Graph<Borough, DefaultWeightedEdge> graph, List<Borough> boroughs) {
		super();
		this.graph = graph;
		this.boroughs = boroughs;
	}
	
	public void init(Borough start, int tech) {
		this.startBorough = start;
		this.tech = tech;
		// inizializzazione output
		this.duration = 0;
		this.revisioned = new ArrayList<>();
		for(int i=0; i<tech; i++)
			revisioned.add(0);
		// create queue
		this.queue = new PriorityQueue<>();
		// caricamento queue
		int i = 0;
		while(this.techOccupied<this.tech&&this.hsLeft>0) { // se ho tecnici liberi e hotspost da controllare
			// associo tecnico ad hotspot
			queue.add(new Event(0, EventType.START_HS, i));
			this.techOccupied++;
			this.hsLeft--;
			i++;
		}		
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event event = this.queue.poll();
			this.duration = event.getTime();
			processEvent(event);
		}
	}

	private void processEvent(Event event) {
		int time = event.getTime();
		EventType type = event.getType();
		int tech = event.getTech();
		
		switch(type) {
		
		case START_HS:
			this.revisioned.set(tech, this.revisioned.get(tech)+1);
			if(Math.random()<0.1) {
				queue.add(new Event(time+25, EventType.END_HS, tech));
			} else {
				queue.add(new Event(time+10, EventType.END_HS, tech));
			}
			break;
			
		case END_HS:
			this.techOccupied--;
			if(this.hsLeft>0) {
				// mi sposot su altro hs
				int move = (int)(Math.random()*11)+10;
				this.techOccupied++;
				this.hsLeft--;
				queue.add(new Event(time+move, EventType.START_HS, tech));
			} else if(this.techOccupied>0) {
				// non fai nulla
			} else if(this.boroughsLeft.size()>0) {
				// tutti cambiano quartiere
				Borough nextBorough = nextBoro(this.currentBorough, this.boroughsLeft);
				int transfer = (int)(this.graph.getEdgeWeight(this.graph.getEdge(currentBorough, nextBorough))/50.0*60.0);
				this.currentBorough = nextBorough;
				this.boroughsLeft.remove(nextBorough);
				this.hsLeft = this.currentBorough.getNum();
				this.queue.add(new Event(time+transfer, EventType.NEW_BORO, -1));
			} else {
				// end simulation
			}
			break;
			
		case NEW_BORO:
			int i = 0;
			while(this.techOccupied<this.tech&&this.hsLeft>0) {
				// posso assegnate tech ad hs
				queue.add(new Event(time, EventType.START_HS, i));
				this.techOccupied++;
				this.hsLeft--;
				i++;
			}
			break;
		}
	}

	private Borough nextBoro(Borough currentBorough2, List<Borough> boroughsLeft2) {
		double min = 100000.0;
		Borough destinaion = null;
		for(Borough b : boroughsLeft2) {
			double peso = this.graph.getEdgeWeight(this.graph.getEdge(currentBorough2, b));
			if(peso<min) {
				min=peso;
				destinaion=b;
			}
		}
		return destinaion;
	}

	public int getDuration() {
		return duration;
	}

	public List<Integer> getRevisioned() {
		return revisioned;
	}
	
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

