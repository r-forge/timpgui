//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.02.23 at 12:05:48 PM CET 
//


package nl.vu.nat.tgmodels.tgo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AlgorithmPanelElements complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AlgorithmPanelElements">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="stderrclp" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="nnls" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="algorithm" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AlgorithmPanelElements", propOrder = {
    "stderrclp",
    "nnls",
    "algorithm"
})
public class AlgorithmPanelElements {

    @XmlElement(defaultValue = "false")
    protected boolean stderrclp;
    @XmlElement(defaultValue = "false")
    protected boolean nnls;
    @XmlElement(required = true, defaultValue = "nls")
    protected String algorithm;

    /**
     * Gets the value of the stderrclp property.
     * 
     */
    public boolean isStderrclp() {
        return stderrclp;
    }

    /**
     * Sets the value of the stderrclp property.
     * 
     */
    public void setStderrclp(boolean value) {
        this.stderrclp = value;
    }

    /**
     * Gets the value of the nnls property.
     * 
     */
    public boolean isNnls() {
        return nnls;
    }

    /**
     * Sets the value of the nnls property.
     * 
     */
    public void setNnls(boolean value) {
        this.nnls = value;
    }

    /**
     * Gets the value of the algorithm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * Sets the value of the algorithm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlgorithm(String value) {
        this.algorithm = value;
    }

}
