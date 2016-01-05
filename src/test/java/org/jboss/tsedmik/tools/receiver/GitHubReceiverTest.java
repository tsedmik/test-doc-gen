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
