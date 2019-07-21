package uk.ashigaru.engine.misc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Logger {
	
	private static List<String> collectedLogs = new ArrayList<String>();
	
	private static String timeStamp() {
		return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
	}
	
	public static void log(String string) {
		String outString = "[" + timeStamp() + "][OUT][" + Thread.currentThread().getName() + "] " + string;
		collectedLogs.add(outString);
		System.out.println(outString);
	}
	
	public static void log(String string, Object... args) {
		String outString = "[" + timeStamp() + "][OUT][" + Thread.currentThread().getName() + "] " + String.format(string, args);
		collectedLogs.add(outString);
		System.out.println(outString);
	}
	
	public static void err(String string) {
		String outString = "[" + timeStamp() + "][ERR] " + string;
		collectedLogs.add(outString);
		System.err.println(outString);
	}

	public static void err(String string, Object... args) {
		String outString = "[" + timeStamp() + "][ERR] " + String.format(string, args);
		collectedLogs.add(outString);
		System.err.println(outString);
	}
	
	public static void dbg(String string) {
		String outString = "[" + timeStamp() + "][DBG] " + string;
		collectedLogs.add(outString);
		System.out.println(outString);
	}
}
