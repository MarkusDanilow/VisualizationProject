package com.base.common.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cluster {

	private final List<Point> points;
	private Point centroid;

	public final String id;

	public Cluster(Point firstPoint) {
		points = new ArrayList<Point>();
		centroid = firstPoint;
		this.id = UUID.randomUUID().toString();
	}

	public Point getCentroid() {
		return centroid;
	}

	public void updateCentroid() {
		double newx = 0d, newy = 0d, newz = 0d;
		for (Point point : points) {
			newx += point.x;
			newy += point.y;
			newz += point.z;
		}
		centroid = new Point(newx / points.size(), newy / points.size(), newz / points.size());
	}

	public List<Point> getPoints() {
		return points;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder("This cluster contains the following points:\n");
		for (Point point : points)
			builder.append(point.toString() + ",\n");
		return builder.deleteCharAt(builder.length() - 2).toString();
	}

	public int getNumPoints() {
		return points.size();
	}

}
