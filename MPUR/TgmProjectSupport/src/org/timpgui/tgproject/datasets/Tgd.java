//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.04.07 at 05:57:40 AM CEST 
//


package org.timpgui.tgproject.datasets;

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
 *         &lt;element name="fileName" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="path" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="filtype" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="dataset" type="{http://timpgui.org/Schema/TgdSchema}timpDataset" maxOccurs="unbounded" minOccurs="0"/>
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
    "fileName",
    "path",
    "filtype",
    "dataset"
})
@XmlRootElement(name = "tgd")
public class Tgd {

    @XmlElement(required = true)
    protected Object fileName;
    @XmlElement(required = true)
    protected Object path;
    @XmlElement(required = true)
    protected Object filtype;
    protected List<TimpDataset> dataset;

    /**
     * Gets the value of the fileName property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getFileName() {
        return fileName;
    }

    /**
     * Sets the value of the fileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setFileName(Object value) {
        this.fileName = value;
    }

    /**
     * Gets the value of the path property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getPath() {
        return path;
    }

    /**
     * Sets the value of the path property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setPath(Object value) {
        this.path = value;
    }

    /**
     * Gets the value of the filtype property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getFiltype() {
        return filtype;
    }

    /**
     * Sets the value of the filtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setFiltype(Object value) {
        this.filtype = value;
    }

    /**
     * Gets the value of the dataset property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataset property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataset().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TimpDataset }
     * 
     * 
     */
    public List<TimpDataset> getDataset() {
        if (dataset == null) {
            dataset = new ArrayList<TimpDataset>();
        }
        return this.dataset;
    }

}