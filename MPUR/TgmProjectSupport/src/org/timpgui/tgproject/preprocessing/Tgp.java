//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.04.21 at 03:20:09 PM CEST 
//


package org.timpgui.tgproject.preprocessing;

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
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numberOfDimensions" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="selectedData" type="{http://timpgui.org/Schema/TgpSchema}SelectedData" maxOccurs="unbounded"/>
 *         &lt;element name="numberOfTraces" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded"/>
 *         &lt;element name="outlierDetection">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="windowSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="threshold" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "type",
    "numberOfDimensions",
    "selectedData",
    "numberOfTraces",
    "outlierDetection"
})
@XmlRootElement(name = "tgp")
public class Tgp {

    @XmlElement(required = true)
    protected String type;
    protected int numberOfDimensions;
    @XmlElement(required = true, nillable = true)
    protected List<SelectedData> selectedData;
    @XmlElement(type = Integer.class)
    protected List<Integer> numberOfTraces;
    @XmlElement(required = true)
    protected Tgp.OutlierDetection outlierDetection;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the numberOfDimensions property.
     * 
     */
    public int getNumberOfDimensions() {
        return numberOfDimensions;
    }

    /**
     * Sets the value of the numberOfDimensions property.
     * 
     */
    public void setNumberOfDimensions(int value) {
        this.numberOfDimensions = value;
    }

    /**
     * Gets the value of the selectedData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the selectedData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSelectedData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SelectedData }
     * 
     * 
     */
    public List<SelectedData> getSelectedData() {
        if (selectedData == null) {
            selectedData = new ArrayList<SelectedData>();
        }
        return this.selectedData;
    }

    /**
     * Gets the value of the numberOfTraces property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the numberOfTraces property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNumberOfTraces().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getNumberOfTraces() {
        if (numberOfTraces == null) {
            numberOfTraces = new ArrayList<Integer>();
        }
        return this.numberOfTraces;
    }

    /**
     * Gets the value of the outlierDetection property.
     * 
     * @return
     *     possible object is
     *     {@link Tgp.OutlierDetection }
     *     
     */
    public Tgp.OutlierDetection getOutlierDetection() {
        return outlierDetection;
    }

    /**
     * Sets the value of the outlierDetection property.
     * 
     * @param value
     *     allowed object is
     *     {@link Tgp.OutlierDetection }
     *     
     */
    public void setOutlierDetection(Tgp.OutlierDetection value) {
        this.outlierDetection = value;
    }


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
     *         &lt;element name="windowSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="threshold" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
        "windowSize",
        "threshold"
    })
    public static class OutlierDetection {

        protected int windowSize;
        protected double threshold;

        /**
         * Gets the value of the windowSize property.
         * 
         */
        public int getWindowSize() {
            return windowSize;
        }

        /**
         * Sets the value of the windowSize property.
         * 
         */
        public void setWindowSize(int value) {
            this.windowSize = value;
        }

        /**
         * Gets the value of the threshold property.
         * 
         */
        public double getThreshold() {
            return threshold;
        }

        /**
         * Sets the value of the threshold property.
         * 
         */
        public void setThreshold(double value) {
            this.threshold = value;
        }

    }

}
