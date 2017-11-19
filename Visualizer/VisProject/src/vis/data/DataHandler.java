package vis.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import com.base.common.resources.Cluster;
import com.base.common.resources.DataElement;
import com.base.common.resources.KMeans;
import com.base.common.resources.Range;

public class DataHandler {

	public static final int elementSize = 4;

	private static DataBuffer currentBuffer;
	private static List<Cluster> currentClusters;

	public static DataBuffer getCurrentBuffer() {
		return currentBuffer;
	}

	public static void setCurrentBuffer(DataBuffer currentBuffer) {
		DataHandler.currentBuffer = currentBuffer;
	}

	public static List<Cluster> getCurrentClusters() {
		return currentClusters;
	}

	public static void setCurrentClusters(List<Cluster> currentClusters) {
		DataHandler.currentClusters = currentClusters;
	}

	public static Map<Float, DataElement> parseDataFromFile(String filePath) throws FileNotFoundException, IOException {
		return parseDataFromFile(filePath, -1);
	}

	public static Map<Float, DataElement> parseDataFromFile(String filePath, int clusters)
			throws FileNotFoundException, IOException {

		boolean useClustering = clusters > -1;
		KMeans kmeans = new KMeans(clusters);

		Map<Float, DataElement> elements = new TreeMap<>();
		if (filePath != null && filePath.length() > 0) {
			try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
				String line = null;
				while ((line = br.readLine()) != null) {
					String[] parts = line.split("\\|");
					if (parts.length == elementSize && isNumber(removeWhitespaces(parts[0]))) {
						for (byte i = 0; i < elementSize; i++) {
							parts[i] = removeWhitespaces(parts[i]);
						}
						float time = Float.parseFloat(parts[0]);
						float x = Float.parseFloat(parts[1]);
						float y = Float.parseFloat(parts[2]);
						float z = Float.parseFloat(parts[3]);
						DataElement element = new DataElement(x, y, z, time);
						elements.put(time, element);
						kmeans.addPoint(element.getPoint());
					}
				}
			}
		}
		if (useClustering) {
			currentClusters = kmeans.getPointsClusters();
		}
		currentBuffer = new DataBuffer();
		currentBuffer.setData(elements);
		return elements;
	}

	public static String removeWhitespaces(String s) {
		return s != null ? s.replace("\\s+", "") : "";
	}

	public static boolean isNumber(String s) {
		try {
			Double.parseDouble(s);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static Map<Float, DataElement> getPartialData(Map<Float, DataElement> entireDataSet, Range<Float> range) {
		return getPartialData(entireDataSet, range, 2);
	}

	public static Map<Float, DataElement> getPartialData(Map<Float, DataElement> entireDataSet, Range<Float> range,
			int clusters) {
		Map<Float, DataElement> elements = new TreeMap<>();
		boolean useClustering = clusters > -1;
		KMeans kmeans = new KMeans(clusters);
		if (entireDataSet != null && range != null) {
			float start = range.getLoVal();
			float end = range.getHiVal();
			for (Float timeStamp : entireDataSet.keySet()) {
				if (timeStamp >= start && timeStamp <= end) {
					DataElement element = entireDataSet.get(timeStamp);
					elements.put(timeStamp, element);
					if (useClustering)
						kmeans.addPoint(element.getPoint());
				}
			}
		}
		if (useClustering)
			currentClusters = kmeans.getPointsClusters();
		return elements;
	}

	public static List<DataElement> convertToRenderableList(Map<Float, DataElement> data) {
		return data != null ? new ArrayList<>(data.values()) : new ArrayList<>();
	}

	public static DataElement[] getDataBounds(Map<Float, DataElement> data) {
		TreeMap<Float, DataElement> castMap = (TreeMap<Float, DataElement>) data;
		DataElement first = castMap.firstEntry().getValue();
		DataElement last = castMap.lastEntry().getValue();
		return new DataElement[] { first, last };
	}

	public static DataElement getNext(Map<Float, DataElement> data, float currentTimeStamp) {
		return null;
	}

	public static Map<Float, DataElement> generateDataSet(int setSize, Range<Float> range) {
		Map<Float, DataElement> elements = new TreeMap<>();
		float min = range.getLoVal();
		float max = range.getHiVal();
		KMeans kmeans = new KMeans(10);
		Random r = new Random();
		for (int i = 0; i < setSize; i++) {
			float x = r.nextFloat() * (max - min) + min;
			float y = r.nextFloat() * (max - min) + min;
			float z = r.nextFloat() * (max - min) + min;
			DataElement vertex = new DataElement(x, y, z, i);
			elements.put((float) i, vertex);
			kmeans.addPoint(vertex.getPoint());
		}
		currentBuffer = new DataBuffer();
		currentBuffer.setData(elements);
		currentClusters = kmeans.getPointsClusters();
		return elements;
	}

}
