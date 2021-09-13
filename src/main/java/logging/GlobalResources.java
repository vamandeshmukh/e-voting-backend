package logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalResources {

	@SuppressWarnings("rawtypes")
	public static Logger getLogger(Class className) {
		return LoggerFactory.getLogger(className);

	}

}
