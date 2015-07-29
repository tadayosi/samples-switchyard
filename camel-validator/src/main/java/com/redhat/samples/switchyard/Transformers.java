package com.redhat.samples.switchyard;

import java.io.StringReader;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

import org.switchyard.annotations.Transformer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public final class Transformers {

    private static final String NAMESPACE = "urn:samples-switchyard:camel-validator:1.0";

    // @formatter:off
    private static final String RESPONSE_HELLO =
              "<ns:helloResponse xmlns:ns=\"urn:samples-switchyard:camel-validator:1.0\">\n"
            + "    <ns:message>%s</ns:message>\n"
            + "</ns:helloResponse>";
    private static final String RESPONSE_GOODBYE = 
              "<ns:goodbyeResponse xmlns:ns=\"urn:samples-switchyard:camel-validator:1.0\">\n"
            + "    <ns:message>%s</ns:message>\n"
            + "</ns:goodbyeResponse>";
    // @formatter:on

    @Transformer(from = "{urn:samples-switchyard:camel-validator:1.0}hello")
    public String helloToString(Element from) {
        return getElementValue(from, "name");
    }

    @Transformer(from = "{urn:samples-switchyard:camel-validator:1.0}goodbye")
    public String goodbyeToString(Element from) {
        return getElementValue(from, "name");
    }

    @Transformer(to = "{urn:samples-switchyard:camel-validator:1.0}helloResponse")
    public Element stringToHelloResponse(String from) {
        return toElement(String.format(RESPONSE_HELLO, from));
    }

    @Transformer(to = "{urn:samples-switchyard:camel-validator:1.0}goodbyeResponse")
    public Element stringToGoodbyeResponse(String from) {
        return toElement(String.format(RESPONSE_GOODBYE, from));
    }

    private String getElementValue(Element parent, String elementName) {
        NodeList nodes = parent.getElementsByTagNameNS(NAMESPACE, elementName);
        if (nodes.getLength() < 1) return null;
        return nodes.item(0).getChildNodes().item(0).getNodeValue();
    }

    private Element toElement(String xml) {
        DOMResult result = new DOMResult();
        try {
            TransformerFactory.newInstance().newTransformer()
                    .transform(new StreamSource(new StringReader(xml)), result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((Document) result.getNode()).getDocumentElement();
    }

}
