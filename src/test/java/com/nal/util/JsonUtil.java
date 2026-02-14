package com.nal.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nal.tests.vendorportal.model.VendorPortalTestData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * Created by N on 2/14/2026
 **/

public class JsonUtil {

    public static final Logger log = LoggerFactory.getLogger(JsonUtil.class);
    public static final ObjectMapper mapper = new ObjectMapper();

    public static VendorPortalTestData getTestData(String path) {
        try (InputStream stream = ResourceLoader.getResource(path)) {
            return mapper.readValue(stream, VendorPortalTestData.class);
        } catch (Exception e) {
            log.error("Unable to read test data: {}", path, e);
        }
        return null;
    }
}
