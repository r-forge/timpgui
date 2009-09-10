//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.09.10 at 04:28:35 PM CEST 
//


package nl.vu.nat.tgmodels.tgm;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for KinparPanelModel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="KinparPanelModel">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="positivepar" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="kinpar" type="{http://www.nat.vu.nl/~jsnel/Schema/TgmSchema}KinPar" maxOccurs="unbounded"/>
 *         &lt;element name="seqmod" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KinparPanelModel", propOrder = {
    "positivepar",
    "kinpar",
    "seqmod"
})
public class KinparPanelModel {

    @XmlElement(defaultValue = "false")
    protected boolean positivepar;
    @XmlElement(required = true)
    protected List<KinPar> kinpar;
    @XmlElement(defaultValue = "false")
    protected boolean seqmod;

    /**
     * Gets the value of the positivepar property.
     * 
     */
    public boolean isPositivepar() {
        return positivepar;
    }

    /**
     * Sets the value of the positivepar property.
     * 
     */
    public void setPositivepar(boolean value) {
        this.positivepar = value;
    }

    /**
     * Gets the value of the kinpar property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the kinpar property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKinpar().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KinPar }
     * 
     * 
     */
    public List<KinPar> getKinpar() {
        if (kinpar == null) {
            kinpar = new ArrayList<KinPar>();
        }
        return this.kinpar;
    }

    /**
     * Gets the value of the seqmod property.
     * 
     */
    public boolean isSeqmod() {
        return seqmod;
    }

    /**
     * Sets the value of the seqmod property.
     * 
     */
    public void setSeqmod(boolean value) {
        this.seqmod = value;
    }

}
