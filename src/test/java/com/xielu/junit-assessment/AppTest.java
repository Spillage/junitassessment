package com.xielu.junitassessment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;


@RunWith(Parameterized.class)
public class AppTest 
{
    private String year;
    private com.xielu.junitassessment.App app;

    public AppTest(String year){
        super();
        this.year = year;
    }

    @Before
    public void initialize(){
        app = new com.xielu.junitassessment.App();
    }

    @Parameterized.Parameters
    public static Collection input(){
        return Arrays.asList(new Object[][]{{"2010"},{"2011"},{"2012"},{"2013"},{"2020"},{"2021"},{"shfioweh3920"}});
    }

    @Test
    public void testAPIYear() throws IOException {
        String[] res = app.run(year);
        assertTrue("case fail with year " + year, res[0].equals(res[1]));
    }

}
