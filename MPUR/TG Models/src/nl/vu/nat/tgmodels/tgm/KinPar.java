//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.10.01 at 09:58:11 AM CEST 
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

    @XmlElement(defaultValue = "NaN")
    protected double start;
    @XmlElement(defaultValue = "false")
    protected boolean fixed;
    @XmlElement(defaultValue = "false")
    protected boolean constrained;
    @XmlElement(defaultValue = "NaN")
    protected double max;
    @XmlElement(defaultValue = "NaN")
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
     */
    public boolean isFixed() {
        return fixed;
    }

    /**
     * Sets the value of the fixed property.
     * 
     */
    public void setFixed(boolean value) {
        this.fixed = value;
    }

    /**
     * Gets the value of the constrained property.
     * 
     */
    public boolean isConstrained() {
        return constrained;
    }

    /**
     * Sets the value of the constrained property.
     * 
     */
    public void setConstrained(boolean value) {
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
