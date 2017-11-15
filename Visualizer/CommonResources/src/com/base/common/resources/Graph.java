package com.base.common.resources;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Graph {

	private Map<String, Node> nodes;
	private int identifier = 0 ; 

	public Graph() {
		this.nodes = new HashMap<String, Node>();
	}

	public void addNode(Node node) {
		if (DataInspector.notNull(node)) {
			node.setName(String.valueOf(this.identifier));
			this.identifier++;
			this.nodes.put(node.getName(), node);
			this.updateNeighbours();
		}
	}
		
	public void removeNode(Node node){
		if(DataInspector.notNull(node)){
			this.removeNode(node.getName());
			this.updateNeighbours();
		}
	}
	
	private void updateNeighbours(){
		for (Node node : this.nodes.values()) {
			node.resetNeighbours();
		}
		for (Node n1 : this.nodes.values()) {
			for (Node n2 : this.nodes.values()) {
				if(!n1.getName().equals(n2.getName())){
					n1.addNeighbour(n2);
				}
			}
		}
	}
	
	public void removeNode(String name){
		this.nodes.remove(name);
	}
	
	public Map<String, Node> getNodes() {
		return nodes;
	}

	public Collection<Node> getNodesAsList(){
		return nodes.values();
	}
	
	public void setNodes(Map<String, Node> nodes) {
		this.nodes = nodes;
	}

	public boolean isPositionSet(Vector2f position){
		return DataInspector.notNull(this.getNodeByPosition(position));
	}
	
	public Node getNodeByPosition(Vector2f position){
		if(DataInspector.notNull(position)){
			for(Node node: this.getNodesAsList()){
				if(node.getPosition().equals(position)){
					return node ; 
				}
			}
		}
		return null ; 
	}
	
}
