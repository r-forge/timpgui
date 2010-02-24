//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.24 at 01:38:42 PM CET 
//


package org.glotaran.core.models.gta;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GtaOutput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GtaOutput">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="outputModule" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="layout" type="{http://glotaran.org/schema/GlobalAndTargetAnalysisSchema}GtaLayout"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="outputPath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="iterations" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GtaOutput", propOrder = {
    "outputModule",
    "layout",
    "id",
    "outputPath",
    "iterations"
})
public class GtaOutput {

    @XmlElement(nillable = true)
    protected List<String> outputModule;
    @XmlElement(required = true)
    protected GtaLayout layout;
    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true, nillable = true)
    protected String outputPath;
    @XmlElement(required = true, nillable = true)
    protected String iterations;

    /**
     * Gets the value of the outputModule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outputModule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutputModule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOutputModule() {
        if (outputModule == null) {
            outputModule = new ArrayList<String>();
        }
        return this.outputModule;
    }

    /**
     * Gets the value of the layout property.
     * 
     * @return
     *     possible object is
     *     {@link GtaLayout }
     *     
     */
    public GtaLayout getLayout() {
        return layout;
    }

    /**
     * Sets the value of the layout property.
     * 
     * @param value
     *     allowed object is
     *     {@link GtaLayout }
     *     
     */
    public void setLayout(GtaLayout value) {
        this.layout = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the outputPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputPath() {
        return outputPath;
    }

    /**
     * Sets the value of the outputPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputPath(String value) {
        this.outputPath = value;
    }

    /**
     * Gets the value of the iterations property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIterations() {
        return iterations;
    }

    /**
     * Sets the value of the iterations property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIterations(String value) {
        this.iterations = value;
    }

}
