//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.01.20 at 01:59:22 PM CET 
//


package org.glotaran.core.models.gta;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GtaConnection complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GtaConnection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="datasetContainerID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="modelID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="active" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="modelDifferences" type="{http://glotaran.org/schema/GlobalAndTargetAnalysisSchema}GtaModelDifferences"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GtaConnection", propOrder = {
    "datasetContainerID",
    "modelID",
    "name",
    "active",
    "modelDifferences",
    "id"
})
public class GtaConnection {

    @XmlElement(required = true)
    protected String datasetContainerID;
    @XmlElement(required = true)
    protected String modelID;
    @XmlElement(required = true)
    protected String name;
    protected boolean active;
    @XmlElement(required = true, nillable = true)
    protected GtaModelDifferences modelDifferences;
    @XmlElement(required = true)
    protected String id;

    /**
     * Gets the value of the datasetContainerID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatasetContainerID() {
        return datasetContainerID;
    }

    /**
     * Sets the value of the datasetContainerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatasetContainerID(String value) {
        this.datasetContainerID = value;
    }

    /**
     * Gets the value of the modelID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelID() {
        return modelID;
    }

    /**
     * Sets the value of the modelID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelID(String value) {
        this.modelID = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the active property.
     * 
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the value of the active property.
     * 
     */
    public void setActive(boolean value) {
        this.active = value;
    }

    /**
     * Gets the value of the modelDifferences property.
     * 
     * @return
     *     possible object is
     *     {@link GtaModelDifferences }
     *     
     */
    public GtaModelDifferences getModelDifferences() {
        return modelDifferences;
    }

    /**
     * Sets the value of the modelDifferences property.
     * 
     * @param value
     *     allowed object is
     *     {@link GtaModelDifferences }
     *     
     */
    public void setModelDifferences(GtaModelDifferences value) {
        this.modelDifferences = value;
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

}