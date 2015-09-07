package com.redhat.samples.switchyard;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.switchyard.component.bean.Reference;
import org.switchyard.component.bean.Service;

@Service(ExampleService.class)
public class ExampleServiceBean implements ExampleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleServiceBean.class);

    @Inject
    @Reference("ExampleReference")
    private ExampleService reference;

    @Override
    public void process(Object o) {
        LOGGER.info("============================================================");
        LOGGER.info("message = {}", o);
        LOGGER.info("============================================================");
        reference.process(o);
    }

}
