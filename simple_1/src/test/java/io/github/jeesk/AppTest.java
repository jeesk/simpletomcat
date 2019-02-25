package io.github.jeesk;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );


        String str = "/sdfs?1231";
        String str3 = "/sdfs?";
        String str2 = "/1231";



        System.out.println(str.split("\\?").length);
        System.out.println(str2.split("\\?").length);


    }
}
