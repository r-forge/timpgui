//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.01.20 at 01:22:16 PM CET 
//


package org.glotaran.core.models.gta;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GtaDatasetContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GtaDatasetContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="layout" type="{http://glotaran.org/schema/GlobalAndTargetAnalysisSchema}GtaLayout"/>
 *         &lt;element name="datasets" type="{http://glotaran.org/schema/GlobalAndTargetAnalysisSchema}GtaDataset" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "GtaDatasetContainer", propOrder = {
    "layout",
    "datasets",
    "id"
})
public class GtaDatasetContainer {

    @XmlElement(required = true)
    protected GtaLayout layout;
    @XmlElement(nillable = true)
    protected List<GtaDataset> datasets;
    @XmlElement(required = true)
    protected String id;

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
     * Gets the value of the datasets property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datasets property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatasets().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GtaDataset }
     * 
     * 
     */
    public List<GtaDataset> getDatasets() {
        if (datasets == null) {
            datasets = new ArrayList<GtaDataset>();
        }
        return this.datasets;
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