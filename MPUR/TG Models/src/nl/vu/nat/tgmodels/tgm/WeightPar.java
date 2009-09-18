//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.09.18 at 11:28:11 AM CEST 
//


package nl.vu.nat.tgmodels.tgm;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WeightPar complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WeightPar">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="min1" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="min2" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="max1" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="max2" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="weight" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WeightPar", propOrder = {
    "min1",
    "min2",
    "max1",
    "max2",
    "weight"
})
public class WeightPar {

    @XmlElementRef(name = "min1", namespace = "http://www.nat.vu.nl/~jsnel/Schema/TgmSchema", type = JAXBElement.class)
    protected JAXBElement<Double> min1;
    @XmlElementRef(name = "min2", namespace = "http://www.nat.vu.nl/~jsnel/Schema/TgmSchema", type = JAXBElement.class)
    protected JAXBElement<Double> min2;
    @XmlElementRef(name = "max1", namespace = "http://www.nat.vu.nl/~jsnel/Schema/TgmSchema", type = JAXBElement.class)
    protected JAXBElement<Double> max1;
    @XmlElementRef(name = "max2", namespace = "http://www.nat.vu.nl/~jsnel/Schema/TgmSchema", type = JAXBElement.class)
    protected JAXBElement<Double> max2;
    @XmlElementRef(name = "weight", namespace = "http://www.nat.vu.nl/~jsnel/Schema/TgmSchema", type = JAXBElement.class)
    protected JAXBElement<Double> weight;

    /**
     * Gets the value of the min1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getMin1() {
        return min1;
    }

    /**
     * Sets the value of the min1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setMin1(JAXBElement<Double> value) {
        this.min1 = ((JAXBElement<Double> ) value);
    }

    /**
     * Gets the value of the min2 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getMin2() {
        return min2;
    }

    /**
     * Sets the value of the min2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setMin2(JAXBElement<Double> value) {
        this.min2 = ((JAXBElement<Double> ) value);
    }

    /**
     * Gets the value of the max1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getMax1() {
        return max1;
    }

    /**
     * Sets the value of the max1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setMax1(JAXBElement<Double> value) {
        this.max1 = ((JAXBElement<Double> ) value);
    }

    /**
     * Gets the value of the max2 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getMax2() {
        return max2;
    }

    /**
     * Sets the value of the max2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setMax2(JAXBElement<Double> value) {
        this.max2 = ((JAXBElement<Double> ) value);
    }

    /**
     * Gets the value of the weight property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getWeight() {
        return weight;
    }

    /**
     * Sets the value of the weight property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setWeight(JAXBElement<Double> value) {
        this.weight = ((JAXBElement<Double> ) value);
    }

}
