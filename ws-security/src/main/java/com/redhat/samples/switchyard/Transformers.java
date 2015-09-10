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

    //@formatter:off
    private static final String SOAP_REQUEST =
              "<ns1:ping xmlns:ns1=\"http://ws.switchyard.samples.redhat.com/\">"
            + "<time>%s</time>"
            + "</ns1:ping>";
    //@formatter:on

    @Transformer(to = "{http://ws.switchyard.samples.redhat.com/}ping")
    public Element transformRequest(String from) {
        DOMResult result = new DOMResult();
        try {
            TransformerFactory.newInstance().newTransformer()
                    .transform(new StreamSource(new StringReader(String.format(SOAP_REQUEST, from))), result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ((Document) result.getNode()).getDocumentElement();
    }

    @Transformer(from = "{http://ws.switchyard.samples.redhat.com/}pingResponse")
    public String transformResponse(Element from) {
        NodeList ret = from.getElementsByTagName("return");
        if (ret.getLength() == 0) return null;
        return ret.item(0).getChildNodes().item(0).getTextContent();
    }

}
