//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.04.23 at 10:34:15 AM CEST 
//


package stack24174963.datacite;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for funderIdentifierType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="funderIdentifierType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ISNI"/>
 *     &lt;enumeration value="GRID"/>
 *     &lt;enumeration value="Crossref Funder ID"/>
 *     &lt;enumeration value="Other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "funderIdentifierType")
@XmlEnum
public enum FunderIdentifierType {

    ISNI("ISNI"),
    GRID("GRID"),
    @XmlEnumValue("Crossref Funder ID")
    CROSSREF_FUNDER_ID("Crossref Funder ID"),
    @XmlEnumValue("Other")
    OTHER("Other");
    private final String value;

    FunderIdentifierType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FunderIdentifierType fromValue(String v) {
        for (FunderIdentifierType c: FunderIdentifierType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
