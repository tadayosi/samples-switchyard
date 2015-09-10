package com.redhat.samples.switchyard;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DateUtils {

    public static XMLGregorianCalendar now() {
        GregorianCalendar now = new GregorianCalendar();
        now.setTime(new Date());
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(now);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

}
