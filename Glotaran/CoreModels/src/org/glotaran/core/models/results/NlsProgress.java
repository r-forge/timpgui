//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.03.24 at 12:12:25 PM CET 
//


package org.glotaran.core.models.results;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NlsProgress complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NlsProgress">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rss" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="paramValue" type="{http://www.w3.org/2001/XMLSchema}double" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NlsProgress", propOrder = {
    "rss",
    "paramValue"
})
public class NlsProgress {

    @XmlElement(required = true, nillable = true)
    protected String rss;
    @XmlElement(type = Double.class)
    protected List<Double> paramValue;

    /**
     * Gets the value of the rss property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRss() {
        return rss;
    }

    /**
     * Sets the value of the rss property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRss(String value) {
        this.rss = value;
    }

    /**
     * Gets the value of the paramValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paramValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParamValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Double }
     * 
     * 
     */
    public List<Double> getParamValue() {
        if (paramValue == null) {
            paramValue = new ArrayList<Double>();
        }
        return this.paramValue;
    }

}
