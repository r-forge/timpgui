//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.11.10 at 12:02:19 PM CET 
//


package org.glotaran.core.models.tgm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dat" type="{http://www.nat.vu.nl/~jsnel/Schema/TgmSchema}Dat"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "dat"
})
@XmlRootElement(name = "tgm")
public class Tgm {

    @XmlElement(required = true, nillable = true)
    protected Dat dat;

    /**
     * Gets the value of the dat property.
     * 
     * @return
     *     possible object is
     *     {@link Dat }
     *     
     */
    public Dat getDat() {
        return dat;
    }

    /**
     * Sets the value of the dat property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dat }
     *     
     */
    public void setDat(Dat value) {
        this.dat = value;
    }

}