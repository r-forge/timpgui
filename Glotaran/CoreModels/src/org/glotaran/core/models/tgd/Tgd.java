//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.06.16 at 10:55:50 AM CEST 
//


package org.glotaran.core.models.tgd;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="filename" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="filetype" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="datasetNames" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="cacheFolderName" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "filename",
    "path",
    "filetype",
    "datasetNames",
    "cacheFolderName"
})
@XmlRootElement(name = "tgd")
public class Tgd {

    @XmlElement(required = true)
    protected String filename;
    @XmlElement(required = true)
    protected String path;
    @XmlElement(required = true)
    protected String filetype;
    @XmlElement(nillable = true)
    protected List<String> datasetNames;
    @XmlElement(required = true)
    protected String cacheFolderName;

    /**
     * Gets the value of the filename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Sets the value of the filename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilename(String value) {
        this.filename = value;
    }

    /**
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPath(String value) {
        this.path = value;
    }

    /**
     * Gets the value of the filetype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiletype() {
        return filetype;
    }

    /**
     * Sets the value of the filetype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiletype(String value) {
        this.filetype = value;
    }

    /**
     * Gets the value of the datasetNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the datasetNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatasetNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDatasetNames() {
        if (datasetNames == null) {
            datasetNames = new ArrayList<String>();
        }
        return this.datasetNames;
    }

    /**
     * Gets the value of the cacheFolderName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCacheFolderName() {
        return cacheFolderName;
    }

    /**
     * Sets the value of the cacheFolderName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCacheFolderName(String value) {
        this.cacheFolderName = value;
    }

}
