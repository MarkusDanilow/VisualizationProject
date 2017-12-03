//http://commons.apache.org/proper/commons-math/download_math.cgi

package vis.statistics;

import java.util.*;
import java.util.Map.Entry;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import com.base.common.resources.DataElement;
import com.base.common.resources.Regression;

public class Statistic {

	private final static int MAX_VALUE_CONTENT = 5;
	private final static int AMOUNT_PREDICTION_VALUE = 10;
	private static Regression regResult;
	private static SimpleRegression calcRegression;
	private static HashMap<Float, Float> predictData;
	private static int counter = 0;
	private static int size = 0;
	private static Float bufferValue = 0F;

	public Statistic() {

	}

	public static void main(String arg[]) {

		Map<Float, DataElement> treemap = new HashMap<>();
		DataElement d0 = new DataElement(0.1F, 0.1F, 0.2F, 0.3F);
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
	
		treemap.put(1F, d1);
		System.out.println(treemap);
		treemap.put(2F, d2);
		System.out.println(treemap);
		treemap.put(3F, d3);
		System.out.println(treemap);
		treemap.put(4F, d4);
		System.out.println(treemap);
		treemap.put(5F, d5);
		System.out.println(treemap);
		System.out.println(treemap.hashCode());
		treemap.put(6F, d6);
		System.out.println(treemap);
		treemap.put(7F, d7);
		System.out.println(treemap);
		treemap.put(8F, d8);
		System.out.println(treemap);
		treemap.put(9F, d9);
		System.out.println(treemap);
		System.out.println(treemap.hashCode());
		treemap.put(2.1F, d0);
		System.out.println(treemap);
		Regression r = getRegAnalysis(treemap, "x");
		System.out.println(r);
		/*r = getRegAnalysis(treemap, "y");
		System.out.println(r);
		r = getRegAnalysis(treemap, "z");
		System.out.println(r);*/
		getRegPrediction();

		getPrediction(predictData);

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

	public static Regression getRegAnalysis(Map<Float, DataElement> lastTenResults, String dimension) {

		regResult = new Regression();
		calcRegression = new SimpleRegression();

		// Data-Buffering
		Map<Float, DataElement> tempMap = new HashMap<>();
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
						bufferValue = me.getValue().getX();
						System.out.println(bufferValue);
						break;
					case "y":
						calcRegression.addData((double) me.getKey(), (double) me.getValue().getY());
						bufferValue = me.getValue().getY();
						break;
					case "z":
						calcRegression.addData((double) me.getKey(), (double) me.getValue().getZ());
						bufferValue = me.getValue().getZ();
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
		counter = 0;
		return regResult;
	}

	public static HashMap<Float, Float> getRegPrediction() {
		predictData = new HashMap<>();

		while (counter <= AMOUNT_PREDICTION_VALUE-1) {
			bufferValue = bufferValue * 1.1F;
			predictData.put((float) size++, (float) calcRegression.predict(bufferValue));
			counter++;
		}
		System.out.println("Amount of predicted Values:" + predictData.size());
		return predictData;
	}

	public static void getPrediction(HashMap<Float, Float> data) {
		
		System.out.println("DEBUGGING:" );
		Set<Entry<Float, Float>> set = data.entrySet();
		Iterator<Entry<Float, Float>> it = set.iterator();

		while (it.hasNext()) {
			Map.Entry<Float, Float> me = (Map.Entry<Float, Float>) it.next();
			System.out.print("Key: " + me.getKey());
			System.out.println("\tValue: " + me.getValue());
		}

	}

}
