package vis.data;

import java.util.Map;
import java.util.TreeMap;

import com.base.common.resources.DataElement;

public class DataBuffer {

	private Map<Float, DataElement> data;
	private DataElement [] bounds = null;
	private float step = 0f;

	public DataBuffer() {
		this.data = new TreeMap<Float, DataElement>();
	}

	public Map<Float, DataElement> getData() {
		return data;
	}

	public void setData(Map<Float, DataElement> data) {
		this.data = data;
		this.bounds = DataHandler.getDataBounds(data);
		this.calculateStep();
	}

	public float getStep() {
		return step;
	}

	public void calculateStep() {
		if (this.data != null && this.data.size() > 0) {
			this.step = Math.round((bounds[1].getTime() - bounds[0].getTime()) / data.size());
			System.out.println(step);
		}
	}

}
