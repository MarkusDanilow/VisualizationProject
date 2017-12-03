//http://commons.apache.org/proper/commons-math/download_math.cgi

package vis.statistics;

import java.util.*;
import java.util.Map.Entry;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import com.base.common.resources.DataElement;
import com.base.common.resources.Regression;

public class Statistic {

	private final static int MAX_VALUE_CONTENT = 5;

	public Statistic() {

	}

	public static void main(String arg[]) {

		TreeMap<Float, DataElement> treemap = new TreeMap<>();
		DataElement d0 = new DataElement(0, 0.1F, 0.2F, 0.3F);
		DataElement d1 = new DataElement(1, 1, 1, 1);
		DataElement d2 = new DataElement(2, 2, 2, 2);
		DataElement d3 = new DataElement(3, 3, 3, 3);
		DataElement d4 = new DataElement(4, 4, 4, 4);
		DataElement d5 = new DataElement(5, 5.1F, 5.2F, 5.3F);
		DataElement d6 = new DataElement(6, 6.1F, 6.2F, 6.3F);
		DataElement d7 = new DataElement(7, 7.1F, 7.2F, 7.3F);
		DataElement d8 = new DataElement(8, 8.1F, 8.2F, 8.3F);
		DataElement d9 = new DataElement(9, 9.1F, 9.2F, 9.3F);

		// Add key-value pairs to the TreeMap
		treemap.put(0F, d0);
		treemap.put(1F, d1);
		treemap.put(2F, d2);
		treemap.put(3F, d3);
		treemap.put(4F, d4);
		treemap.put(5F, d5);
		treemap.put(6F, d6);
		treemap.put(7F, d7);
		treemap.put(8F, d8);
		treemap.put(9F, d9);

		Regression r = getRegAnalysis(treemap, "x");
		System.out.println(r);
		r = getRegAnalysis(treemap, "y");
		System.out.println(r);
		r = getRegAnalysis(treemap, "z");
		System.out.println(r);

	}

	public double getMean() {
		return 0.0;
	}

	public void setMean(double value) {

	}

	public int getValueClass() {
		// int = position (t)
		return 0;
	}

	public void setValueClass() {

	}

	public static Regression getRegAnalysis(TreeMap<Float, DataElement> lastTenResults, String dimension) {
		int counter = 0;
		int size;

		Regression regResult = new Regression();
		SimpleRegression calcRegression = new SimpleRegression();

		// Data-Buffering
		TreeMap<Float, DataElement> tempMap = new TreeMap<>();
		tempMap = lastTenResults;
		size = tempMap.size();

		// getSize of incoming values
		System.out.println("Map Size: " + tempMap.size());
		System.out.println("Limitation: " + MAX_VALUE_CONTENT);
		Set<Entry<Float, DataElement>> set = tempMap.entrySet();
		Iterator<Entry<Float, DataElement>> it = set.iterator();

		if (size < MAX_VALUE_CONTENT) {
			while (it.hasNext()) {
				Map.Entry<Float, DataElement> me = (Map.Entry<Float, DataElement>) it.next();
				switch (dimension) {
				case "x":
					calcRegression.addData((double) me.getKey(), (double) me.getValue().getX());
					break;
				case "y":
					calcRegression.addData((double) me.getKey(), (double) me.getValue().getY());
					break;
				case "z":
					calcRegression.addData((double) me.getKey(), (double) me.getValue().getZ());
					break;
				default:
					break;
				}
				// System.out.println("Key: " + me.getKey());
				// System.out.println("Value: " + me.getValue().getX());
			}
		} else {
			while (it.hasNext()) {
				Map.Entry<Float, DataElement> me = (Map.Entry<Float, DataElement>) it.next();
				if (counter >= size - MAX_VALUE_CONTENT) {
					switch (dimension) {
					case "x":
						calcRegression.addData((double) me.getKey(), (double) me.getValue().getX());
						break;
					case "y":
						calcRegression.addData((double) me.getKey(), (double) me.getValue().getY());
						break;
					case "z":
						calcRegression.addData((double) me.getKey(), (double) me.getValue().getZ());
						break;
					default:
						break;
					}
				}
				counter++;
			}
		}
		regResult.setM((float) calcRegression.getSlope());
		regResult.setN((float) calcRegression.getIntercept());
		regResult.setStdErr((float) calcRegression.getSlopeStdErr());
		// y = ab ^ t
		return regResult;
	}

	

}
