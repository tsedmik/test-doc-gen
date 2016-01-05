package org.jboss.tsedmik.tools.receiver;

import java.io.IOException;

/**
 * Interface specifies methods which facilitate access to Java classes.
 * Found Java classes are stored in ${sourcePath} (defined in pom.xml).
 * 
 * @author tsedmik
 */
public interface Receiver {

	/**
	 * Accesses and stores Java classes.
	 * 
	 * @return Number of stored classes
	 * @throws IOException 
	 */
	int receive() throws IOException;

	/**
	 * Accesses Meta information about stored Java classes (e.g. URL - where the test can be found).
	 * TODO describe structure of Meta data map
	 * 
	 * @param key Identifier
	 * @return value associate with the given key, null - otherwise
	 */
	String getMeta(String key);
}
