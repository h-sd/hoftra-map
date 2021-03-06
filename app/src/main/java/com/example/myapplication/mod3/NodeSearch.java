package com.example.myapplication.mod3;

import java.util.*;
import com.example.myapplication.mod1.*;

public class NodeSearch extends Node implements Comparable<Node>{
	
	protected NodeSearch pathParent; //Parent Node
	public double costFromStart;  //G value 
	public double estimatedCostToGoal;  //H value

	private float weight = .9f;

	public NodeSearch(Node n) {
		super(n);
	}
	
	//Return F value, the cost of the node
	public double getCost()
	{
		return costFromStart + estimatedCostToGoal;
	}
	
	@Override
	public int compareTo(Node other) {
		double thisValue = this.getCost();
	    double otherValue = ((NodeSearch)other).getCost();

	    double v = thisValue - otherValue;
	    return (int) Math.signum(v);
		
		
		
	}



	public double getEstimatedCost(NodeSearch other) {
		return getEstimatedCost(this.coord, other.coord);
	}
	
	//Get cost from current to a specific node
	public double getEstimatedCost(LocationData from, LocationData to) {
		//h(n) = 6(shortest distance between two points) * shortest # of nodes to goal
		double r = 6378100.0;
		
		double theta = Math.abs(from.getLat() - to.getLat());				// Degree diff
		theta = Math.toRadians(theta);

		double phi = Math.abs(from.getLon() - to.getLon());				// Degree diff
		phi = Math.toRadians(phi);
		
		double ct = 2 * r * Math.sin(theta / 2);								// 2r sin(theta / 2)
		double cp = 2 * r * Math.sin(phi / 2);									// 2r sin(phi / 2)
		
		double c = Math.sqrt(Math.pow(ct, 2) + Math.pow(cp, 2));				// c = sqrt( ct^2 + cp^2 )
		
		double delta = 2 * Math.asin((c/2) / r);
		
		//lat lon diff -> meters (double)
		double est = r * delta;

		est = est * weight;
		
		return est;
	}

	public Node to_node(){
		return new Node(this);
	}
	
	public int id() {
		return id;
	}
	public NodeSearch parent() {
		return pathParent;
	}

	//Get the list of all child aka neighbors
	public List<NodeSearch> getNeighbors() {
		List<NodeSearch> nb = new LinkedList<NodeSearch>();
				
		for(Integer i : neighbours.keySet()) {
			nb.add(new NodeSearch(neighbours.get(i).from(id)));
		}
			
		return nb;
	}

}
