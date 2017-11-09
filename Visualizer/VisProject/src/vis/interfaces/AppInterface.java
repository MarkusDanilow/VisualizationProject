package vis.interfaces;

import com.base.common.resources.Range;

public interface AppInterface {

	void displayMessage(String message);

	void displayError(Exception exception);

	void loadData(String fileName) throws Exception;

	void quit();

	void quit(Exception exception);

	float getTimelineStep();
	
	void displaySubData(Range<Float> range);

}
