package vis.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.base.common.DataElement;
import com.base.common.resources.Range;

public class DataHandler {

	public static final int elementSize = 4;
	
	public static Map<Float, DataElement> parseDataFromFile(String filePath) throws FileNotFoundException, IOException {
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
					}
				}
			}
		}
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
		Map<Float, DataElement> elements = new TreeMap<>();
		if (entireDataSet != null && range != null) {
			float start = range.getLoVal();
			float end = range.getHiVal();
			for (Float timeStamp : entireDataSet.keySet()) {
				if (timeStamp >= start && timeStamp <= end) {
					elements.put(timeStamp, entireDataSet.get(timeStamp));
				}
			}
		}
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

}
