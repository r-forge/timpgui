//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.06.15 at 01:15:58 PM CEST 
//


package nl.vu.nat.tgmodels.tgo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IterPanelElements complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IterPanelElements">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FLIM" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="iter" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IterPanelElements", propOrder = {
    "flim",
    "iter"
})
public class IterPanelElements {

    @XmlElement(name = "FLIM", defaultValue = "false")
    protected boolean flim;
    @XmlElement(defaultValue = "5")
    protected int iter;

    /**
     * Gets the value of the flim property.
     * 
     */
    public boolean isFLIM() {
        return flim;
    }

    /**
     * Sets the value of the flim property.
     * 
     */
    public void setFLIM(boolean value) {
        this.flim = value;
    }

    /**
     * Gets the value of the iter property.
     * 
     */
    public int getIter() {
        return iter;
    }

    /**
     * Sets the value of the iter property.
     * 
     */
    public void setIter(int value) {
        this.iter = value;
    }

}
