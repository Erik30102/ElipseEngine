package Elipse.Core;

import Elipse.Core.Logger.LogLevel;
import Elipse.Core.Logger.LogMode;

public interface LoggerCallbackFunc {
	void Log(LogMode mode, LogLevel level, String message);
}
