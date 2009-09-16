//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.09.16 at 11:33:23 AM CEST 
//


package nl.vu.nat.tgmodels.tgm;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for KMatrixPanelModel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="KMatrixPanelModel">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="k1matrix" type="{http://www.nat.vu.nl/~jsnel/Schema/TgmSchema}DoubleMatrix"/>
 *         &lt;element name="k2matrix" type="{http://www.nat.vu.nl/~jsnel/Schema/TgmSchema}DoubleMatrix"/>
 *         &lt;element name="jVector" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KMatrixPanelModel", propOrder = {
    "k1Matrix",
    "k2Matrix",
    "jVector"
})
public class KMatrixPanelModel {

    @XmlElement(name = "k1matrix", required = true)
    protected DoubleMatrix k1Matrix;
    @XmlElement(name = "k2matrix", required = true)
    protected DoubleMatrix k2Matrix;
    @XmlElement(type = Integer.class)
    protected List<Integer> jVector;

    /**
     * Gets the value of the k1Matrix property.
     * 
     * @return
     *     possible object is
     *     {@link DoubleMatrix }
     *     
     */
    public DoubleMatrix getK1Matrix() {
        return k1Matrix;
    }

    /**
     * Sets the value of the k1Matrix property.
     * 
     * @param value
     *     allowed object is
     *     {@link DoubleMatrix }
     *     
     */
    public void setK1Matrix(DoubleMatrix value) {
        this.k1Matrix = value;
    }

    /**
     * Gets the value of the k2Matrix property.
     * 
     * @return
     *     possible object is
     *     {@link DoubleMatrix }
     *     
     */
    public DoubleMatrix getK2Matrix() {
        return k2Matrix;
    }

    /**
     * Sets the value of the k2Matrix property.
     * 
     * @param value
     *     allowed object is
     *     {@link DoubleMatrix }
     *     
     */
    public void setK2Matrix(DoubleMatrix value) {
        this.k2Matrix = value;
    }

    /**
     * Gets the value of the jVector property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the jVector property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getJVector().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getJVector() {
        if (jVector == null) {
            jVector = new ArrayList<Integer>();
        }
        return this.jVector;
    }

}
