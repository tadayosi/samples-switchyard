
package com.redhat.samples.switchyard.goodbye;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.redhat.samples.switchyard.goodbye package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Goodbye_QNAME = new QName("urn:samples-switchyard:dynamic-soap-endpoint:1.0", "goodbye");
    private final static QName _GoodbyeResponse_QNAME = new QName("urn:samples-switchyard:dynamic-soap-endpoint:1.0", "goodbyeResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.redhat.samples.switchyard.goodbye
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GoodbyeResponse }
     * 
     */
    public GoodbyeResponse createGoodbyeResponse() {
        return new GoodbyeResponse();
    }

    /**
     * Create an instance of {@link Goodbye }
     * 
     */
    public Goodbye createGoodbye() {
        return new Goodbye();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Goodbye }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:samples-switchyard:dynamic-soap-endpoint:1.0", name = "goodbye")
    public JAXBElement<Goodbye> createGoodbye(Goodbye value) {
        return new JAXBElement<Goodbye>(_Goodbye_QNAME, Goodbye.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GoodbyeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:samples-switchyard:dynamic-soap-endpoint:1.0", name = "goodbyeResponse")
    public JAXBElement<GoodbyeResponse> createGoodbyeResponse(GoodbyeResponse value) {
        return new JAXBElement<GoodbyeResponse>(_GoodbyeResponse_QNAME, GoodbyeResponse.class, null, value);
    }

}
