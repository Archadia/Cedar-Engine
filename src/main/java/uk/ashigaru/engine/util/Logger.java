package uk.ashigaru.engine.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
		String outString = "[" + timeStamp() + "][OUT] " + string;
		collectedLogs.add(outString);
		System.out.println(outString);
	}
	
	public static void err(String string) {
		String outString = "[" + timeStamp() + "][ERR] " + string;
		collectedLogs.add(outString);
		System.out.println(outString);
	}
	
	public static void dbg(String string) {
		String outString = "[" + timeStamp() + "][DBG] " + string;
		collectedLogs.add(outString);
		System.out.println(outString);
	}
	
	public static boolean saveLogs(String name) {
		File file = new File(SystemLocation.LOGS.path() + name + ".txt");
		file.getParentFile().mkdirs();
		try {
			if(!file.exists()) file.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for(String line : collectedLogs) {
				writer.write(line);
				writer.newLine();
				writer.flush();
			}
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
