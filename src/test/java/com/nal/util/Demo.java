package com.nal.util;

import com.nal.tests.vendorportal.model.VendorPortalTestData;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by N on 2/14/2026
 **/

public class Demo {

    public static void main(String[] args) throws Exception {

        InputStream stream = ResourceLoader.getResource("demotestdata/demo.txt");
        InputStream stream2 = ResourceLoader.getResource("test-suites/demotestdata/demo.txt");
        String content = IOUtils.toString(stream, StandardCharsets.UTF_8);
        String content2 = IOUtils.toString(stream2, StandardCharsets.UTF_8);
        System.out.println(content);
        System.out.println(content2);

        VendorPortalTestData testData = JsonUtil.getTestData("test-data/vendor-portal/john.json");
        System.out.println(testData.monthlyEarnings());
        System.out.println(testData.searchResultsCount());
        System.out.println(testData.username());
    }
}
