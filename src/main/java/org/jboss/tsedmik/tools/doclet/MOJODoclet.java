package org.jboss.tsedmik.tools.doclet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;

/**
 * The main class converts JavaDoc to HTML format (suitable for MOJO)
 * 
 * @author tsedmik
 */
public class MOJODoclet {

	public static boolean start(RootDoc root) throws IOException {

		StringBuilder output = new StringBuilder();
		output.append("<body>")
		// the following line append 'Table of content' in MOJO
		.append("<h1><img class=\"jive_macro jive_macro_toc\" src=\"/8.0.2.7f31811/images/tiny_mce3/plugins/jiveemoticons/images/spacer.gif\" jivemacro=\"toc\" data-renderedposition=\"8_8_61_72\"/></h1>");

		// Sort classes alphabetically
		List<String> temp = new ArrayList<String>();
		for (ClassDoc item : root.classes()) {
			temp.add(item.name());
		}
		Collections.sort(temp);

		// Fill content
		ClassDoc[] classes = root.classes();
		for (String name : temp) {

			// TODO skip user defined classes (in property file?)
			// skip unwanted classes
			if (name.equals("Activator") || name.equals("SAPInstallationTest.InstallationWizard") || name.equals("DefaultTest")) continue;
			
			// Find correct class (to keep alphabet order)
			ClassDoc item = null;
			for (ClassDoc classe : classes) {
				if (classe.name().equals(name)) {
					item = classe;
					break;
				}
			}

			output.append("<p></p>")
			.append("<p></p>")
			.append("<h2>")
			.append(item.name())
			.append("</h2>")
			.append("<p>")
			.append(item.commentText())
			.append("</p>");
			for (MethodDoc method : item.methods()) {

				// TODO skip user defined methods (in property file?)
				// skip unwanted methods
				if (method.name().startsWith("setup") || name.equals("data")) continue;

				output.append("<p></p>")
				.append("<h3>")
				.append(method.name())
				.append("</h3>")
				.append(method.commentText());
			}
		}

		File file = new File("../../javadoc.html");
		try {
			file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		PrintWriter writer = new PrintWriter(file);
		writer.print(output.toString());
		writer.flush();
		writer.close();

		return true;
		}
}
