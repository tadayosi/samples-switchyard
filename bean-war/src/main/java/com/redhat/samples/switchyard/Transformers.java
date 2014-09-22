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

    private static final String SAMPLE_NS = "urn:samples-switchyard:bean:1.0";

    private static final String RESPONSE_HELLO = "<sample:helloResponse xmlns:sample=\"urn:samples-switchyard:bean:1.0\"><sample:string>%s</sample:string></sample:helloResponse>";
    private static final String RESPONSE_GOODBYE = "<sample:goodbyeResponse xmlns:sample=\"urn:samples-switchyard:bean:1.0\"><sample:string>%s</sample:string></sample:goodbyeResponse>";

    @Transformer(from = "{urn:samples-switchyard:bean:1.0}hello")
    public String fromHello(Element from) {
        return getElementValue(from, "string");
    }

    @Transformer(to = "{urn:samples-switchyard:bean:1.0}helloResponse")
    public Element toHelloResponse(String from) {
        String response = String.format(RESPONSE_HELLO, from);
        return toElement(response);
    }

    @Transformer(from = "{urn:samples-switchyard:bean:1.0}goodbye")
    public String fromGoodbye(Element from) {
        return getElementValue(from, "string");
    }

    @Transformer(to = "{urn:samples-switchyard:bean:1.0}goodbyeResponse")
    public Element toGoodbyeResponse(String from) {
        String response = String.format(RESPONSE_GOODBYE, from);
        return toElement(response);
    }

    private String getElementValue(Element parent, String elementName) {
        NodeList nodes = parent.getElementsByTagNameNS(SAMPLE_NS, elementName);
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
