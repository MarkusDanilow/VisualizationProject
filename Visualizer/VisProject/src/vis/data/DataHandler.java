package vis.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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

	public static final int ELEMENT_SIZE = 4;
	public static final int NUM_CLUSTERS = 5;

	private static DataBuffer currentBuffer;
	private static List<Cluster> currentClusters;

	public static DataBuffer getCurrentBuffer() {
		return currentBuffer;
	}

	/**
	 * 
	 * @param currentBuffer
	 */
	public static void setCurrentBuffer(DataBuffer currentBuffer) {
		DataHandler.currentBuffer = currentBuffer;
	}

	/**
	 * 
	 * @return
	 */
	public static List<Cluster> getCurrentClusters() {
		return currentClusters;
	}

	/**
	 * 
	 * @param currentClusters
	 */
	public static void setCurrentClusters(List<Cluster> currentClusters) {
		DataHandler.currentClusters = currentClusters;
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static String removeWhitespaces(String s) {
		return s != null ? s.replace("\\s+", "") : "";
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNumber(String s) {
		try {
			Double.parseDouble(s);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Map<Float, DataElement> loadDataFromFile(String filePath) throws FileNotFoundException, IOException {

		boolean useClustering = NUM_CLUSTERS > -1;
		KMeans kmeans = new KMeans(NUM_CLUSTERS);

		Map<Float, DataElement> elements = new TreeMap<>();
		if (filePath != null && filePath.length() > 0) {
			try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
				String line = null;
				while ((line = br.readLine()) != null) {
					String[] parts = line.split("\\|");
					if (parts.length == ELEMENT_SIZE && isNumber(removeWhitespaces(parts[0]))) {
						for (byte i = 0; i < ELEMENT_SIZE; i++) {
							parts[i] = removeWhitespaces(parts[i]);
						}
						float time = Float.parseFloat(parts[0]);
						float x = Float.parseFloat(parts[1]);
						float y = Float.parseFloat(parts[2]);
						float z = Float.parseFloat(parts[3]);
						DataElement element = new DataElement(x, y, z, time);
						elements.put(time, element);
						if (useClustering)
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

	/**
	 * 
	 * @return
	 */
	public static Map<Float, DataElement> loadDataFromRemoteAPI() {
		boolean useClustering = NUM_CLUSTERS > -1;
		KMeans kmeans = new KMeans(NUM_CLUSTERS);
		Map<Float, DataElement> elements = new TreeMap<>();
		try {

			URL url = new URL("http://www.liquidsolution.de");
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			PrintStream ps = new PrintStream(con.getOutputStream());
			ps.print("data=true");

			con.getInputStream();

			// close the print stream
			ps.close();
			ps.flush();
			URL url2 = new URL("http://www.liquidsolution.de/api.php?get=topK&k=10000");
			BufferedReader in = new BufferedReader(new InputStreamReader(url2.openStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				String json = inputLine;

				// only parse if it's a valid string
				if (json.length() > 0) {

					// remove export date and enclosing brackets
					int length = json.length() - 33;
					json = json.substring(1, length);

					// parse each element in the JSON string
					String[] parts = json.split("\\},\\{");
					for (String partialJson : parts) {

						float x = 0, y = 0, z = 0, time = 0;

						// parse attributes
						String[] keyValuePairs = partialJson.split(",");
						for (String keyValue : keyValuePairs) {
							String[] parsedKeyValues = keyValue.split(":");
							String key = parsedKeyValues[0];
							String value = "";
							for (int i = 1; i < parsedKeyValues.length; i++) {
								value += parsedKeyValues[i];
							}

							if (key.equals("id")) {
								time = Float.parseFloat(value);
							} else if (key.equals("xPos")) {
								x = Float.parseFloat(value);
							} else if (key.equals("yPos")) {
								z = Float.parseFloat(value);
							} else if (key.equals("zPos")) {
								y = Float.parseFloat(value);
							}

						}

						// create new data element
						DataElement e = new DataElement(x, y, z, time);
						elements.put(time, e);
						if (useClustering)
							kmeans.addPoint(e.getPoint());

					}
				}
			}

			in.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (useClustering) {
			currentClusters = kmeans.getPointsClusters();
		}
		currentBuffer = new DataBuffer();
		currentBuffer.setData(elements);
		return elements;
	}

	/**
	 * 
	 * @param entireDataSet
	 * @param range
	 * @return
	 */
	public static Map<Float, DataElement> getPartialData(Map<Float, DataElement> entireDataSet, Range<Float> range) {
		Map<Float, DataElement> elements = new TreeMap<>();
		boolean useClustering = NUM_CLUSTERS > -1;
		KMeans kmeans = new KMeans(NUM_CLUSTERS);
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

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static List<DataElement> convertToRenderableList(Map<Float, DataElement> data) {
		return data != null ? new ArrayList<>(data.values()) : new ArrayList<>();
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static DataElement[] getDataBounds(Map<Float, DataElement> data) {
		TreeMap<Float, DataElement> castMap = (TreeMap<Float, DataElement>) data;
		DataElement first = castMap.firstEntry().getValue();
		DataElement last = castMap.lastEntry().getValue();
		return new DataElement[] { first, last };
	}

	/**
	 * 
	 * @param data
	 * @param currentTimeStamp
	 * @return
	 */
	public static DataElement getNext(Map<Float, DataElement> data, float currentTimeStamp) {
		return null;
	}

	/**
	 * 
	 * @param setSize
	 * @param range
	 * @return
	 */
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
