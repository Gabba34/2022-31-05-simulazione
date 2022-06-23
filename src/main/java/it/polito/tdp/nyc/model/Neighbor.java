package it.polito.tdp.nyc.model;


public class Neighbor {
	String name;
	Double distance;
	public Neighbor(String name, Double distance) {
		super();
		this.name = name;
		this.distance = distance;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}

	
	
}
