//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.09.18 at 11:28:11 AM CEST 
//


package nl.vu.nat.tgmodels.tgm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FlimPanelModel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FlimPanelModel">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mirf" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="convalg" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="reftau" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlimPanelModel", propOrder = {
    "mirf",
    "convalg",
    "reftau"
})
public class FlimPanelModel {

    @XmlElement(defaultValue = "false")
    protected boolean mirf;
    @XmlElement(defaultValue = "2")
    protected int convalg;
    @XmlElement(defaultValue = "NaN")
    protected double reftau;

    /**
     * Gets the value of the mirf property.
     * 
     */
    public boolean isMirf() {
        return mirf;
    }

    /**
     * Sets the value of the mirf property.
     * 
     */
    public void setMirf(boolean value) {
        this.mirf = value;
    }

    /**
     * Gets the value of the convalg property.
     * 
     */
    public int getConvalg() {
        return convalg;
    }

    /**
     * Sets the value of the convalg property.
     * 
     */
    public void setConvalg(int value) {
        this.convalg = value;
    }

    /**
     * Gets the value of the reftau property.
     * 
     */
    public double getReftau() {
        return reftau;
    }

    /**
     * Sets the value of the reftau property.
     * 
     */
    public void setReftau(double value) {
        this.reftau = value;
    }

}
