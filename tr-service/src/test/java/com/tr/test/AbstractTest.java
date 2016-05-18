package com.tr.test;

import com.tr.test.config.AppConfig;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Created by erez on 11/26/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class AbstractTest {

    @Test
    public void testRun() {
        System.out.println("Running abstract");
        Assert.assertEquals(true, true);
    }
}
