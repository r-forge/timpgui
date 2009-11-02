//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.11.02 at 02:16:08 PM CET 
//


package nl.vu.nat.tgmodels.tgm;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Double2BoolMatrix complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Double2BoolMatrix">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="data" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="c0" type="{http://www.w3.org/2001/XMLSchema}double" maxOccurs="unbounded"/>
 *                   &lt;element name="c1" type="{http://www.w3.org/2001/XMLSchema}double" maxOccurs="unbounded"/>
 *                   &lt;element name="c0fixed" type="{http://www.w3.org/2001/XMLSchema}boolean" maxOccurs="unbounded"/>
 *                   &lt;element name="c1fixed" type="{http://www.w3.org/2001/XMLSchema}boolean" maxOccurs="unbounded"/>
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
@XmlType(name = "Double2BoolMatrix", propOrder = {
    "data"
})
public class Double2BoolMatrix {

    @XmlElement(required = true, nillable = true)
    protected List<Double2BoolMatrix.Data> data;

    /**
     * Gets the value of the data property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the data property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Double2BoolMatrix.Data }
     * 
     * 
     */
    public List<Double2BoolMatrix.Data> getData() {
        if (data == null) {
            data = new ArrayList<Double2BoolMatrix.Data>();
        }
        return this.data;
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
     *         &lt;element name="c0" type="{http://www.w3.org/2001/XMLSchema}double" maxOccurs="unbounded"/>
     *         &lt;element name="c1" type="{http://www.w3.org/2001/XMLSchema}double" maxOccurs="unbounded"/>
     *         &lt;element name="c0fixed" type="{http://www.w3.org/2001/XMLSchema}boolean" maxOccurs="unbounded"/>
     *         &lt;element name="c1fixed" type="{http://www.w3.org/2001/XMLSchema}boolean" maxOccurs="unbounded"/>
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
        "c0",
        "c1",
        "c0Fixed",
        "c1Fixed"
    })
    public static class Data {

        @XmlElement(required = true, nillable = true)
        protected List<Double> c0;
        @XmlElement(type = Double.class)
        protected List<Double> c1;
        @XmlElement(name = "c0fixed", required = true, nillable = true)
        protected List<Boolean> c0Fixed;
        @XmlElement(name = "c1fixed", required = true, nillable = true)
        protected List<Boolean> c1Fixed;

        /**
         * Gets the value of the c0 property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the c0 property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getC0().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Double }
         * 
         * 
         */
        public List<Double> getC0() {
            if (c0 == null) {
                c0 = new ArrayList<Double>();
            }
            return this.c0;
        }

        /**
         * Gets the value of the c1 property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the c1 property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getC1().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Double }
         * 
         * 
         */
        public List<Double> getC1() {
            if (c1 == null) {
                c1 = new ArrayList<Double>();
            }
            return this.c1;
        }

        /**
         * Gets the value of the c0Fixed property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the c0Fixed property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getC0Fixed().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Boolean }
         * 
         * 
         */
        public List<Boolean> getC0Fixed() {
            if (c0Fixed == null) {
                c0Fixed = new ArrayList<Boolean>();
            }
            return this.c0Fixed;
        }

        /**
         * Gets the value of the c1Fixed property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the c1Fixed property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getC1Fixed().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Boolean }
         * 
         * 
         */
        public List<Boolean> getC1Fixed() {
            if (c1Fixed == null) {
                c1Fixed = new ArrayList<Boolean>();
            }
            return this.c1Fixed;
        }

    }

}
