package vis.interfaces;

import com.base.common.resources.Range;

public interface AppInterface {

	void displayMessage(String message);

	void displayError(Exception exception);

	void loadDataFromFile(String fileName) throws Exception;

	void loadDataFromRemotAPI();

	void quit();

	void quit(Exception exception);

	float getTimelineStep();

	void displaySubData(Range<Float> range);

	void toggleCompleteParallelCoordinates();
	
	void invertColors();

}
