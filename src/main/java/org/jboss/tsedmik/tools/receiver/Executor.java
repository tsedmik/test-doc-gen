/*
 * test-doc-gen is a tool which converts JavaDoc to Documentation of 
 * tests in various data formats (HTML, XML, ...)
 * Copyright (C) 2016  Mgr. Tomáš Sedmík (ramm47@gmail.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.jboss.tsedmik.tools.receiver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

/**
 * TODO add JavaDoc
 * 
 * @author tsedmik
 */
public class Executor {

	/**
	 * TODO add JavaDoc
	 * TODO add Logging
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		Receiver receiver = null;
		switch (args[0]) { // in the future there might be more receivers (GitLab, SVN, ...)
		case "github":
			receiver = new GitHubReceiver(new URL(System.getProperty("github-api-resource")), System.getProperty("sourcePath"));
			break;
		default:
			break;
		}

		// fetch java classes
		receiver.receive();

		// add additional information from meta data
		File dir = new File(System.getProperty("sourcePath"));
		String[] files = dir.list();
		for (int i = 0; i < files.length; i++) {
			File file = new File(System.getProperty("sourcePath") + "/" + files[i]);
			File output = new File(System.getProperty("sourcePath") + "/" + files[i] + ".temp");
			FileReader fr = new FileReader(file);
			FileWriter fw = new FileWriter(output);
			BufferedReader br = new BufferedReader(fr);
			BufferedWriter bw = new BufferedWriter(fw);
			String line;
			StringBuilder buffer = new StringBuilder();
			boolean buffering = false;
			while ((line = br.readLine()) != null) {
				
				if (line.contains("/**")) buffering = true; // start buffering JavaDoc comment
				// stop buffering and process buffer
				if ((line.contains("private") || line.contains("public")) && buffering) {

					// add meta data
					// skip classes
					if (!line.contains("class")) {

						// add URL to source
						StringBuilder temp = new StringBuilder();
						temp.append("\n* <a href=\"");
						temp.append(receiver.getMeta(files[i] + "_url"));
						temp.append("\">");
						temp.append(receiver.getMeta(files[i] + "_url"));
						temp.append("</a>");

						buffer.insert(buffer.indexOf("/**") + 3, temp.toString());
					}
					
					bw.append(buffer.toString()); // flush buffer to output
					buffering = false;
					buffer.delete(0, buffer.capacity()); // clear buffer
				}

				if (buffering) {
					buffer.append(line);
					buffer.append("\n");
				} else {
					bw.append(line);
					bw.newLine();
				}
			}
			br.close();
			bw.close();
			fr.close();
			fw.close();

			// update original file with changes
			file.delete();
			output.renameTo(new File(System.getProperty("sourcePath") + "/" + files[i]));
		}
	}

}
