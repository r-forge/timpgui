//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.11.05 at 12:22:34 PM CET 
//


package nl.vu.nat.tgmodels.tgm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for KinPar complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="KinPar">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="start" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="fixed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="constrained" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;sequence>
 *           &lt;element name="max" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *           &lt;element name="min" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;/sequence>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KinPar", propOrder = {
    "start",
    "fixed",
    "constrained",
    "max",
    "min"
})
public class KinPar {

    protected double start;
    @XmlElement(required = true, type = Boolean.class, defaultValue = "false", nillable = true)
    protected Boolean fixed;
    @XmlElement(required = true, type = Boolean.class, defaultValue = "false", nillable = true)
    protected Boolean constrained;
    protected double max;
    protected double min;

    /**
     * Gets the value of the start property.
     * 
     */
    public double getStart() {
        return start;
    }

    /**
     * Sets the value of the start property.
     * 
     */
    public void setStart(double value) {
        this.start = value;
    }

    /**
     * Gets the value of the fixed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFixed() {
        return fixed;
    }

    /**
     * Sets the value of the fixed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFixed(Boolean value) {
        this.fixed = value;
    }

    /**
     * Gets the value of the constrained property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isConstrained() {
        return constrained;
    }

    /**
     * Sets the value of the constrained property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setConstrained(Boolean value) {
        this.constrained = value;
    }

    /**
     * Gets the value of the max property.
     * 
     */
    public double getMax() {
        return max;
    }

    /**
     * Sets the value of the max property.
     * 
     */
    public void setMax(double value) {
        this.max = value;
    }

    /**
     * Gets the value of the min property.
     * 
     */
    public double getMin() {
        return min;
    }

    /**
     * Sets the value of the min property.
     * 
     */
    public void setMin(double value) {
        this.min = value;
    }

}
