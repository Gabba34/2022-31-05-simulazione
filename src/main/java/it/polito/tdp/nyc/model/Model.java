package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.nyc.db.NYCDao;

public class Model {
	NYCDao dao;
	List<String> providers;
	List<Borough> boroughs;
	Graph<Borough, DefaultWeightedEdge> graph;
	
	int duration;
	List<Integer> revisioned;
	
	public Model() {
		dao = new NYCDao();
		providers = new ArrayList<>();
		boroughs = new ArrayList<>();
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	}
	
	public List<String> getProviders() {
		providers = new ArrayList<>(dao.getProvider());
		return providers;
	}
	
	public List<Borough> getBoroughs(String provider){ // VERTICI DEL GRAFO
		boroughs = new ArrayList<>(dao.getBoroughs(provider));
		return boroughs;
	}
	
	public String createGraph()	{
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(graph, boroughs);
		setWeightedEdges(graph);
		return "Grafo creato con "+graph.vertexSet().size()+" vertici e "+graph.edgeSet().size()+" archi.";
	}
	
	public void setWeightedEdges(Graph<Borough, DefaultWeightedEdge> graph) {
		for(Borough b1 : boroughs) {
			for (Borough b2 : boroughs) {
				if(!b1.equals(b2)) {
					double weight = LatLngTool.distance(b1.getPosition(), b2.getPosition(), LengthUnit.KILOMETER);
					Graphs.addEdge(graph, b1, b2, weight);
				}
			}
		}
	}

	public List<Neighbor> getNeighbor(Borough borough) {
		List<Borough> neighbors = Graphs.neighborListOf(this.graph, borough);
		List<Neighbor> neighborDistanceList = new ArrayList<>();
		for (Borough b : neighbors) {
			neighborDistanceList.add(new Neighbor(b.getBoroName(), this.graph.getEdgeWeight(this.graph.getEdge(borough, b))));
		}
		Collections.sort(neighborDistanceList, new Comparator<Neighbor>() {
			@Override
			public int compare(Neighbor o1, Neighbor o2) {
				return o1.getDistance().compareTo(o2.getDistance());
			}
		});
		return neighborDistanceList;
	}
	
	public void simulation(Borough borough, int tech) {
		Simulator sim = new Simulator(graph, boroughs);
		sim.init(borough, tech);
		sim.run();
		this.duration=sim.getDuration();
		this.revisioned=sim.getRevisioned();
	}

	public int getDuration() {
		return duration;
	}

	public List<Integer> getRevisioned() {
		return revisioned;
	}
	
}

