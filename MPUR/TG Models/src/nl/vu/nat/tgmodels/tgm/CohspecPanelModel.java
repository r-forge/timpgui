//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.09.15 at 01:21:06 PM CEST 
//


package nl.vu.nat.tgmodels.tgm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CohspecPanelModel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CohspecPanelModel">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cohspec" type="{http://www.nat.vu.nl/~jsnel/Schema/TgmSchema}Cohspec"/>
 *         &lt;element name="coh" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CohspecPanelModel", propOrder = {
    "cohspec",
    "coh"
})
public class CohspecPanelModel {

    @XmlElement(required = true)
    protected Cohspec cohspec;
    @XmlElement(required = true)
    protected String coh;

    /**
     * Gets the value of the cohspec property.
     * 
     * @return
     *     possible object is
     *     {@link Cohspec }
     *     
     */
    public Cohspec getCohspec() {
        return cohspec;
    }

    /**
     * Sets the value of the cohspec property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cohspec }
     *     
     */
    public void setCohspec(Cohspec value) {
        this.cohspec = value;
    }

    /**
     * Gets the value of the coh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoh() {
        return coh;
    }

    /**
     * Sets the value of the coh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoh(String value) {
        this.coh = value;
    }

}
