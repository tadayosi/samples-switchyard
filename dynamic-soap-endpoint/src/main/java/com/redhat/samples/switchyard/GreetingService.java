package com.redhat.samples.switchyard;

import com.redhat.samples.switchyard.goodbye.Goodbye;
import com.redhat.samples.switchyard.goodbye.GoodbyeResponse;
import com.redhat.samples.switchyard.hello.Hello;
import com.redhat.samples.switchyard.hello.HelloResponse;

public interface GreetingService {

    HelloResponse hello(Hello hello);
    GoodbyeResponse goodbye(Goodbye goodbye);

}
