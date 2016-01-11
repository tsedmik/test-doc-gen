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

import java.io.IOException;

/**
 * Interface specifies methods which facilitate access to Java classes. Found Java classes are stored in ${sourcePath}
 * (defined in pom.xml).
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
	 * Accesses Meta information about stored Java classes (e.g. URL - where the test can be found). The structure of
	 * meta data is described in classes which implement this interface.
	 * 
	 * @param key
	 *            Identifier
	 * @return value associate with the given key, null - otherwise
	 */
	String getMeta(String key);
}
