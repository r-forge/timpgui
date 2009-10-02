//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.10.02 at 11:01:00 AM CEST 
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
 *         &lt;element name="clp0Enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="clp0Min" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="clp0Max" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
    "coh",
    "clp0Enabled",
    "clp0Min",
    "clp0Max"
})
public class CohspecPanelModel {

    @XmlElement(required = true)
    protected Cohspec cohspec;
    @XmlElement(required = true)
    protected String coh;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean clp0Enabled;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double clp0Min;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double clp0Max;

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

    /**
     * Gets the value of the clp0Enabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isClp0Enabled() {
        return clp0Enabled;
    }

    /**
     * Sets the value of the clp0Enabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setClp0Enabled(Boolean value) {
        this.clp0Enabled = value;
    }

    /**
     * Gets the value of the clp0Min property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getClp0Min() {
        return clp0Min;
    }

    /**
     * Sets the value of the clp0Min property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setClp0Min(Double value) {
        this.clp0Min = value;
    }

    /**
     * Gets the value of the clp0Max property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getClp0Max() {
        return clp0Max;
    }

    /**
     * Sets the value of the clp0Max property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setClp0Max(Double value) {
        this.clp0Max = value;
    }

}
