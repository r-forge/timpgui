//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.09.21 at 11:21:08 AM CEST 
//


package nl.vu.nat.tgmodels.tgm;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IrfparPanelModel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IrfparPanelModel">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dispmufun" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="disptaufun" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="parmu" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="partau" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dispmu" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="disptau" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="lamda" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="irf" type="{http://www.w3.org/2001/XMLSchema}double" maxOccurs="unbounded"/>
 *         &lt;element name="fixed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IrfparPanelModel", propOrder = {
    "dispmufun",
    "disptaufun",
    "parmu",
    "partau",
    "dispmu",
    "disptau",
    "lamda",
    "irf",
    "fixed"
})
public class IrfparPanelModel {

    @XmlElement(required = true)
    protected String dispmufun;
    @XmlElement(required = true)
    protected String disptaufun;
    @XmlElement(required = true)
    protected String parmu;
    @XmlElement(required = true)
    protected String partau;
    @XmlElement(defaultValue = "false")
    protected boolean dispmu;
    @XmlElement(defaultValue = "false")
    protected boolean disptau;
    @XmlElement(defaultValue = "NaN")
    protected double lamda;
    @XmlElement(type = Double.class, defaultValue = "0")
    protected List<Double> irf;
    protected boolean fixed;

    /**
     * Gets the value of the dispmufun property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDispmufun() {
        return dispmufun;
    }

    /**
     * Sets the value of the dispmufun property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDispmufun(String value) {
        this.dispmufun = value;
    }

    /**
     * Gets the value of the disptaufun property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisptaufun() {
        return disptaufun;
    }

    /**
     * Sets the value of the disptaufun property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisptaufun(String value) {
        this.disptaufun = value;
    }

    /**
     * Gets the value of the parmu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParmu() {
        return parmu;
    }

    /**
     * Sets the value of the parmu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParmu(String value) {
        this.parmu = value;
    }

    /**
     * Gets the value of the partau property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartau() {
        return partau;
    }

    /**
     * Sets the value of the partau property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartau(String value) {
        this.partau = value;
    }

    /**
     * Gets the value of the dispmu property.
     * 
     */
    public boolean isDispmu() {
        return dispmu;
    }

    /**
     * Sets the value of the dispmu property.
     * 
     */
    public void setDispmu(boolean value) {
        this.dispmu = value;
    }

    /**
     * Gets the value of the disptau property.
     * 
     */
    public boolean isDisptau() {
        return disptau;
    }

    /**
     * Sets the value of the disptau property.
     * 
     */
    public void setDisptau(boolean value) {
        this.disptau = value;
    }

    /**
     * Gets the value of the lamda property.
     * 
     */
    public double getLamda() {
        return lamda;
    }

    /**
     * Sets the value of the lamda property.
     * 
     */
    public void setLamda(double value) {
        this.lamda = value;
    }

    /**
     * Gets the value of the irf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the irf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIrf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Double }
     * 
     * 
     */
    public List<Double> getIrf() {
        if (irf == null) {
            irf = new ArrayList<Double>();
        }
        return this.irf;
    }

    /**
     * Gets the value of the fixed property.
     * 
     */
    public boolean isFixed() {
        return fixed;
    }

    /**
     * Sets the value of the fixed property.
     * 
     */
    public void setFixed(boolean value) {
        this.fixed = value;
    }

}
