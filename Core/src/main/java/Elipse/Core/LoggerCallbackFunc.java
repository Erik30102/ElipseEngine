package Elipse.Core;

import Elipse.Core.Logger.LogLevel;
import Elipse.Core.Logger.LogMode;

public interface LoggerCallbackFunc {
	/**
	 * 
	 * @param mode    whether the message if coming from the engine or the
	 *                application
	 * @param level   the importance and serverity of the message
	 * @param message the message which is to be logged
	 */
	void Log(LogMode mode, LogLevel level, String message);
}
