//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.03.22 at 11:25:06 AM CET 
//


package org.glotaran.core.models.results;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Summary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Summary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="usedAnalysisSchema" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="initModelCall" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fitModelCall" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Summary", propOrder = {
    "usedAnalysisSchema",
    "initModelCall",
    "fitModelCall"
})
public class Summary {

    @XmlElement(required = true)
    protected Object usedAnalysisSchema;
    @XmlElement(required = true)
    protected String initModelCall;
    @XmlElement(required = true)
    protected String fitModelCall;

    /**
     * Gets the value of the usedAnalysisSchema property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUsedAnalysisSchema() {
        return usedAnalysisSchema;
    }

    /**
     * Sets the value of the usedAnalysisSchema property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUsedAnalysisSchema(Object value) {
        this.usedAnalysisSchema = value;
    }

    /**
     * Gets the value of the initModelCall property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInitModelCall() {
        return initModelCall;
    }

    /**
     * Sets the value of the initModelCall property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInitModelCall(String value) {
        this.initModelCall = value;
    }

    /**
     * Gets the value of the fitModelCall property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFitModelCall() {
        return fitModelCall;
    }

    /**
     * Sets the value of the fitModelCall property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFitModelCall(String value) {
        this.fitModelCall = value;
    }

}
