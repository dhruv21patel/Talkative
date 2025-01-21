package org.example.GroupMessageService;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple GroupMessageService.
 */
public class GroupMessageService
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public GroupMessageService(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( GroupMessageService.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
