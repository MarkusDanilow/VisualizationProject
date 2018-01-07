//http://commons.apache.org/proper/commons-math/download_math.cgi

package vis.statistics;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import com.base.common.resources.DataElement;
import com.base.common.resources.Range;
import com.base.common.resources.StatisticObject;
import com.base.engine.Settings;

import vis.data.DataHandler;

public class Statistic {

	private final static int MAX_VALUE_CONTENT = 100;
	private final static int AMOUNT_PREDICTION_VALUE = 10;

	private static StatisticObject regResult;
	private static SimpleRegression calcRegression;
	private static StatisticObject meanResult;
	private static DescriptiveStatistics calcMean;
	private static TreeMap<Float, Float> predictData;
	private static int counter = 0;
	private static int size = 0;
	private static Float tmpRegression = 0F;
	@SuppressWarnings("unused")
	private static Float tmpMean = 0F;

	public Statistic() {

	}

	public static void main(String arg[]) {
		// TreeMap für Mean...HashMap für Regression
		Map<Float, DataElement> treemap = new TreeMap<>();
		DataElement d0 = new DataElement(0, 0, 0, 0);
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
		System.out.println(treemap);
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
		treemap.put(6F, d6);
		System.out.println(treemap);
		treemap.put(7F, d7);
		System.out.println(treemap);
		treemap.put(8F, d8);
		System.out.println(treemap);
		treemap.put(9F, d9);
		System.out.println(treemap);

		// setMean(treemap, "x");
		// System.out.println(getMean());

		setRegAnalysis(treemap, "x", 100);
		System.out.println(getRegAnalysis());
		setRegAnalysis(treemap, "y", 100);
		System.out.println(getRegAnalysis());
		setRegAnalysis(treemap, "z", 100);
		System.out.println(getRegAnalysis());

		getPrediction(getRegPrediction());
	}

	public static StatisticObject getMean() {
		return meanResult;
	}

	@SuppressWarnings("static-access")
	public static void setMean(Map<Float, DataElement> elements, String dimension, int lastCount) {
		meanResult = new StatisticObject();
		calcMean = new DescriptiveStatistics();

		// Data-Buffering
		Map<Float, DataElement> tempMap = new TreeMap<>();
		tempMap = elements;
		size = tempMap.size();
		// System.out.println("Map Size before: " + tempMap.size());

		if (lastCount <= MAX_VALUE_CONTENT) {
			tempMap = DataHandler.getPartialData(tempMap, new Range<Float>((float) size - lastCount, (float) size));

		} else {
			System.out.println("Zu viele Elemente");
			tempMap = DataHandler.getPartialData(tempMap,
					new Range<Float>((float) size - MAX_VALUE_CONTENT, (float) size));

		}

		// getSize of incoming values
		// System.out.println("Map Size after: " + tempMap.size());
		Set<Entry<Float, DataElement>> set = tempMap.entrySet();
		Iterator<Entry<Float, DataElement>> it = set.iterator();

		while (it.hasNext()) {
			Map.Entry<Float, DataElement> me = (Map.Entry<Float, DataElement>) it.next();
			switch (dimension) {
			case "x":
				calcMean.addValue((double) me.getValue().getX());
				tmpMean = me.getValue().getX();
				break;
			case "y":
				calcMean.addValue((double) me.getValue().getY());
				tmpMean = me.getValue().getY();
				break;
			case "z":
				calcMean.addValue((double) me.getValue().getZ());
				tmpMean = me.getValue().getZ();
				break;
			default:
				break;
			}
		}

		meanResult.setA((float) calcMean.getMean());
		meanResult.setB((float) calcMean.getStandardDeviation());
		// ggf. über GUI änderbar
		meanResult.setC((float) calcMean.getPercentile(50));
	}

	public int getValueClass() {
		// int = position (t)
		return 0;
	}

	public void setValueClass() {

	}

	public static StatisticObject getRegAnalysis() {
		// Anpassen
		return regResult;
	}

	@SuppressWarnings("static-access")
	public static void setRegAnalysis(Map<Float, DataElement> elements, String dimension, int lastCount) {
		regResult = new StatisticObject();
		calcRegression = new SimpleRegression();

		// Data-Buffering
		Map<Float, DataElement> tempMap = new TreeMap<>();
		tempMap = elements;
		size = tempMap.size();

		if (lastCount <= MAX_VALUE_CONTENT) {
			tempMap = DataHandler.getPartialData(tempMap, new Range<Float>((float) size - lastCount, (float) size));

		} else {
			System.out.println("Zu viele Elemente");
			tempMap = DataHandler.getPartialData(tempMap,
					new Range<Float>((float) size - MAX_VALUE_CONTENT, (float) size));

		}

		// getSize of incoming values
		// System.out.println("Map Size: " + tempMap.size());
		Set<Entry<Float, DataElement>> set = tempMap.entrySet();
		Iterator<Entry<Float, DataElement>> it = set.iterator();

		while (it.hasNext()) {
			Map.Entry<Float, DataElement> me = (Map.Entry<Float, DataElement>) it.next();
			switch (dimension) {
			case "x":
				calcRegression.addData((double) me.getKey(), (double) me.getValue().getX());
				tmpRegression = me.getValue().getX();
				break;
			case "y":
				calcRegression.addData((double) me.getKey(), (double) me.getValue().getY());
				tmpRegression = me.getValue().getY();
				break;
			case "z":
				calcRegression.addData((double) me.getKey(), (double) me.getValue().getZ());
				tmpRegression = me.getValue().getZ();
				break;
			default:
				break;
			}
		}

		regResult.setA((float) calcRegression.getSlope());
		regResult.setB((float) calcRegression.getIntercept());
		regResult.setC((float) calcRegression.getSlopeStdErr());
		// y = ab ^ t
	}

	public static Map<Float, Float> getRegPrediction() {
		predictData = new TreeMap<Float, Float>();

		while (counter <= AMOUNT_PREDICTION_VALUE - 1) {
			tmpRegression = tmpRegression * 1.1F;
			predictData.put((float) size++, (float) calcRegression.predict(tmpRegression));
			counter++;
		}
		// System.out.println("Amount of predicted Values:" +
		// predictData.size());
		return predictData;
	}

	public static void getPrediction(Map<Float, Float> data) {

		System.out.println("DEBUGGING:");
		Set<Entry<Float, Float>> set = data.entrySet();
		Iterator<Entry<Float, Float>> it = set.iterator();

		while (it.hasNext()) {
			Map.Entry<Float, Float> me = (Map.Entry<Float, Float>) it.next();
			System.out.print("Key: " + me.getKey());

			DecimalFormat df = new DecimalFormat("#.0");
			System.out.println("\tValue: " + Float.parseFloat(df.format(me.getValue()).replace(",", ".")));
		}

	}

	public static List<DataElement> getRenderableSampledList(Map<Float, DataElement> input) {
		List<DataElement> outputData = new ArrayList<>();
		List<DataElement> inputData = DataHandler.convertToRenderableList(input);
		int maxItemsInChart = Settings.getMaxItemsInChart();
		if (input.size() / 2 <= maxItemsInChart) {
			outputData = inputData;
		} else {
			int bound = input.size() / maxItemsInChart;
			float x = 0f, y = 0f, z = 0f, time0 = 0, time1 = 0, lat = 0f, lng = 0f;
			for (int i = 0; i < inputData.size(); i++) {
				x += inputData.get(i).getX();
				y += inputData.get(i).getY();
				z += inputData.get(i).getZ();
				lat += inputData.get(i).getRealLat();
				lng += inputData.get(i).getRealLng();
				if (i % bound == 0) {
					time0 = inputData.get(i).getTime();
				}
				if (i % bound == bound - 1) {
					x /= (float) bound;
					y /= (float) bound;
					z /= (float) bound;
					lat /= (float) bound;
					lng /= (float) bound;
					time1 = inputData.get(i).getTime();
					DataElement e = new DataElement(x, y, z, time0 + (time1 - time0) / 2f);
					e.setLat(lat);
					e.setLng(lng);
					if (bound > 1) {
						e.setTimeRange(new Range<Float>(time0, time1));
						e.setSampleRate(bound);
					}
					x = y = z = time0 = time1 = lat = lng = 0f;
					outputData.add(e);
					if (outputData.size() >= maxItemsInChart)
						break;
				}
			}
		}

		return outputData;
	}

}
