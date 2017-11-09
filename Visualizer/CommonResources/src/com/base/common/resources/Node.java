package com.base.common.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Node {

	public static final int RADIUS = 10, MIN_DISTANCE = RADIUS * 2;

	private String name;
	private Vector2f position;
	private List<Node> neighbours;
	private Node nearest, farthest;
	private int weight;

	public Node() {
		this.neighbours = new ArrayList<Node>();
		this.weight = new Random().nextInt(RADIUS) + 10;
	}

	public void addNeighbour(Node node) {
		if (DataInspector.notNull(node)) {
			this.neighbours.add(node);
			Vector2f v = Vector2f.difference(position, node.position);
			if (DataInspector.notNull(nearest)) {
				Vector2f n = Vector2f.difference(position, nearest.position);
				if (v.length() < n.length()) {
					this.nearest = node;
				}
			} else {
				this.nearest = node;
			}
			if (DataInspector.notNull(farthest)) {
				Vector2f n = Vector2f.difference(position, farthest.position);
				if (v.length() > n.length()) {
					this.farthest = node;
				}
			} else {
				this.farthest = node;
			}
		}
	}

	public void resetNeighbours() {
		this.neighbours.clear();
		this.nearest = null;
		this.farthest = null;
	}

	public Node getNearest() {
		return nearest;
	}

	public void setNearest(Node nearest) {
		this.nearest = nearest;
	}

	public Node getFarthest() {
		return farthest;
	}

	public void setFarthest(Node farthest) {
		this.farthest = farthest;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public List<Node> getNeighbours() {
		return neighbours;
	}

	public void setNeighbours(List<Node> neighbours) {
		this.neighbours = neighbours;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void changeWeight(int iWeight){
		this.weight += iWeight;
	}
	
}
