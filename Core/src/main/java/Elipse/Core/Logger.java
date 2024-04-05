package Elipse.Core;

import java.text.SimpleDateFormat;
import java.util.Date;

// TODO: loggin to file
public class Logger {

	public enum LogMode {
		CORE,
		APPLICATION,
	};

	public enum LogLevel {
		WARN,
		INFO,
		ERROR,
	};

	private static Logger logger;
	private LoggerCallbackFunc Callback;

	/**
	 * Setus up Logger has to be called befor Logging or it wont log
	 */
	public static void InitLogger() {
		logger = new Logger();
		c_info("Logger initialized");
	}

	/**
	 * Set up the callback function for the logger.
	 *
	 * @param callback the callback function to be set
	 */
	public static void SetupCallback(LoggerCallbackFunc callback) {
		logger.Callback = callback;
	}

	/**
	 * Error logging for the application
	 * 
	 * @param message
	 */
	public static void error(String message) {
		logger.log(LogMode.APPLICATION, LogLevel.ERROR, message);
	}

	/**
	 * Warn logging for the application
	 * 
	 * @param message
	 */
	public static void warn(String message) {
		logger.log(LogMode.APPLICATION, LogLevel.WARN, message);
	}

	/**
	 * Info Logging for the application
	 * 
	 * @param message
	 */
	public static void info(String message) {
		logger.log(LogMode.APPLICATION, LogLevel.INFO, message);
	}

	/**
	 * Error logging for the Engien Logger should only be used in Engien code
	 * 
	 * @param message
	 */
	public static void c_error(String message) {
		logger.log(LogMode.CORE, LogLevel.ERROR, message);
	}

	/**
	 * Warn logging for the Engien Logger should only be used in Engien code
	 * 
	 * @param message
	 */
	public static void c_warn(String message) {
		logger.log(LogMode.CORE, LogLevel.WARN, message);
	}

	/**
	 * Info logging for the Engien Logger should only be used in Engien code
	 * 
	 * @param message
	 */
	public static void c_info(String message) {
		logger.log(LogMode.CORE, LogLevel.INFO, message);
	}

	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_WHITE = "\u001B[37m";

	private void log(LogMode mode, LogLevel level, String message) {
		String date = new SimpleDateFormat("HH:mm:ss").format(new Date());
		String f_message = "[";
		switch (mode) {
			case CORE:
				f_message += "CORE: " + date + "] " + message;
				break;
			case APPLICATION:
				f_message += "Application " + date + " ] " + message;
				break;
		}

		switch (level) {
			case ERROR:
				System.out.println(ANSI_RED + f_message + ANSI_WHITE);
				break;
			case WARN:
				System.out.println(ANSI_YELLOW + f_message + ANSI_WHITE);
				break;
			case INFO:
				System.out.println(f_message);
				break;
		}

		if (this.Callback != null)
			Callback.Log(mode, level, f_message);
	}
}
