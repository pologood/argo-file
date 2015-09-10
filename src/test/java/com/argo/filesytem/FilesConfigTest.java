package com.argo.filesytem;

import org.junit.Test;

/**
 * Created by yamingd on 9/10/15.
 */
public class FilesConfigTest {

    @Test
    public void testLoad() throws Exception {
        FilesConfig.load();
        System.out.println(FilesConfig.instance);
    }
}
