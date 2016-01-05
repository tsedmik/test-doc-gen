package org.jboss.tsedmik.tools.receiver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * Facilitates access to Java classes stored in GitHub repositories
 * 
 * @author tsedmik
 */
public class GitHubReceiver implements Receiver {

	private final static Logger log = Logger.getLogger(GitHubReceiver.class.getName());
	private Map<String,String> meta = new HashMap<String, String>();
	private URL from;
	private String to;

	/**
	 * Sets from where java classes will be fetched
	 * 
	 * @param from where are Java Classes stored. Use GitHub API - https://api.github.com/repos/${user}/${repository}/contents/${path-to-java-classes}
	 * @throws IOException 
	 */
	public GitHubReceiver(URL from, String to) throws IOException {	
		this.from = from;
		this.to = to;
		log.info("Setting URL to: " + from.toString());
	}

	public int receive() throws IOException {

		return getFilesContent(getFolderContent());
	}

	public String getMeta(String key) {
		return meta.get(key);
	}

	private int getFolderContent() throws IOException {

		log.info("Fetching files ...");
		int i = 0;
		try (InputStream is = from.openStream(); JsonReader reader = Json.createReader(is)) {
			for (JsonObject result : reader.readArray().getValuesAs(JsonObject.class)) {
				if (result.getJsonString("type").getString().equals("dir")) continue; // skip folders
				if (!result.getJsonString("name").getString().contains(".java")) continue; // skip non java files
				if (result.getJsonString("name").getString().equals("DefaultTest.java")) continue;
				// TODO skip user defined files ("*Test*", "Activator.java", ...)
				i++;

				// meta data used by Receiver
				meta.put(i + "_url", result.getJsonString("git_url").getString());
				meta.put(i + "_path", result.getJsonString("name").getString());

				// meta data for user purposes (addressed via file name)
				meta.put(result.getJsonString("name").getString() + "_url", result.getJsonString("html_url").getString());
				meta.put(result.getJsonString("name").getString() + "_size", result.getJsonNumber("size").toString());
			}
		}
		log.info(i + " Java files found");
		return i;
	}

	private int getFilesContent(int i) throws IOException {
		
		log.info("Saving files content ...");
		for (int j = 1; j <= i; j++) {

			log.info("Saving - " + meta.get(Integer.toString(j) + "_path"));
			URL fileURL = new URL(meta.get(Integer.toString(j) + "_url"));
			URLConnection conn = fileURL.openConnection();
			conn.setRequestProperty("Accept", "application/vnd.github-blob.raw"); // set header to get raw content of the file
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			// create 'target/resources' directory if needed
			File dir = new File(to);
			dir.mkdirs();
			
			File file = new File(dir.getAbsoluteFile() + "/" + meta.get(Integer.toString(j) + "_path"));
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			String line;
			while ((line = in.readLine()) != null) {
				bw.write(line);
				bw.newLine();
			}
			bw.close();
			fw.close();
		}
		
		return i;
	}
}
