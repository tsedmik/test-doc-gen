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

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

/**
 * Tests {@link GitHubReceiver} functionality
 * 
 * @author tsedmik
 */
public class GitHubReceiverTest {

	//@Test
	public void testBasic() throws IOException {
		String[] args = {"github"};
		Executor.main(args);
		System.out.println(System.getProperty("github-resource"));
		fail();
		// TODO add real test case 
	}
}
