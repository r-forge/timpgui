//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.03.11 at 04:25:05 PM CET 
//
package org.glotaran.core.models.gta;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for GtaModelDiffDO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GtaModelDiffDO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="what" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="dataset" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="start" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GtaModelDiffDO", propOrder = {
    "what",
    "index",
    "dataset",
    "start"
})
public class GtaModelDiffDO {

    @XmlElement(required = true)
    protected String what;
    protected int index;
    protected int dataset;
    protected double start;

    /**
     * Gets the value of the what property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWhat() {
        return what;
    }

    /**
     * Sets the value of the what property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWhat(String value) {
        this.what = value;
    }

    /**
     * Gets the value of the index property.
     * 
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     */
    public void setIndex(int value) {
        this.index = value;
    }

    /**
     * Gets the value of the dataset property.
     * 
     */
    public int getDataset() {
        return dataset;
    }

    /**
     * Sets the value of the dataset property.
     * 
     */
    public void setDataset(int value) {
        this.dataset = value;
    }

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
}
