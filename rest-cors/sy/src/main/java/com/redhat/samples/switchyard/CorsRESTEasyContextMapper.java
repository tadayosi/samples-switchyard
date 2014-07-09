package com.redhat.samples.switchyard;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.switchyard.Context;
import org.switchyard.component.resteasy.composer.RESTEasyBindingData;
import org.switchyard.component.resteasy.composer.RESTEasyContextMapper;

public class CorsRESTEasyContextMapper extends RESTEasyContextMapper {

    @Override
    public void mapTo(Context context, RESTEasyBindingData target) throws Exception {
        super.mapTo(context, target);
        Map<String, List<String>> httpHeaders = target.getHeaders();
        httpHeaders.put("Access-Control-Allow-Origin", Arrays.asList("http://localhost:18080"));
    }

}
