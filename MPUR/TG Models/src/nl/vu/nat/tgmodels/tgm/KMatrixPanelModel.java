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
 * <p>Java class for KMatrixPanelModel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="KMatrixPanelModel">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="kMatrix" type="{http://www.nat.vu.nl/~jsnel/Schema/TgmSchema}IntMatrix"/>
 *         &lt;element name="relationsMatrix" type="{http://www.nat.vu.nl/~jsnel/Schema/TgmSchema}Double2BoolMatrix"/>
 *         &lt;element name="jVector" type="{http://www.nat.vu.nl/~jsnel/Schema/TgmSchema}JVector"/>
 *         &lt;element name="spectralContraints" type="{http://www.nat.vu.nl/~jsnel/Schema/TgmSchema}SpectralConstraints"/>
 *         &lt;element name="contrainsMatrix" type="{http://www.nat.vu.nl/~jsnel/Schema/TgmSchema}Double2Matrix"/>
 *         &lt;element name="KinScal" type="{http://www.nat.vu.nl/~jsnel/Schema/TgmSchema}KinPar" maxOccurs="unbounded"/>
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
    "kMatrix",
    "relationsMatrix",
    "jVector",
    "spectralContraints",
    "contrainsMatrix",
    "kinScal"
})
public class KMatrixPanelModel {

    @XmlElement(required = true)
    protected IntMatrix kMatrix;
    @XmlElement(required = true, nillable = true)
    protected Double2BoolMatrix relationsMatrix;
    @XmlElement(required = true, nillable = true)
    protected JVector jVector;
    @XmlElement(required = true, nillable = true)
    protected SpectralConstraints spectralContraints;
    @XmlElement(required = true, nillable = true)
    protected Double2Matrix contrainsMatrix;
    @XmlElement(name = "KinScal", required = true, nillable = true)
    protected List<KinPar> kinScal;

    /**
     * Gets the value of the kMatrix property.
     * 
     * @return
     *     possible object is
     *     {@link IntMatrix }
     *     
     */
    public IntMatrix getKMatrix() {
        return kMatrix;
    }

    /**
     * Sets the value of the kMatrix property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntMatrix }
     *     
     */
    public void setKMatrix(IntMatrix value) {
        this.kMatrix = value;
    }

    /**
     * Gets the value of the relationsMatrix property.
     * 
     * @return
     *     possible object is
     *     {@link Double2BoolMatrix }
     *     
     */
    public Double2BoolMatrix getRelationsMatrix() {
        return relationsMatrix;
    }

    /**
     * Sets the value of the relationsMatrix property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double2BoolMatrix }
     *     
     */
    public void setRelationsMatrix(Double2BoolMatrix value) {
        this.relationsMatrix = value;
    }

    /**
     * Gets the value of the jVector property.
     * 
     * @return
     *     possible object is
     *     {@link JVector }
     *     
     */
    public JVector getJVector() {
        return jVector;
    }

    /**
     * Sets the value of the jVector property.
     * 
     * @param value
     *     allowed object is
     *     {@link JVector }
     *     
     */
    public void setJVector(JVector value) {
        this.jVector = value;
    }

    /**
     * Gets the value of the spectralContraints property.
     * 
     * @return
     *     possible object is
     *     {@link SpectralConstraints }
     *     
     */
    public SpectralConstraints getSpectralContraints() {
        return spectralContraints;
    }

    /**
     * Sets the value of the spectralContraints property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpectralConstraints }
     *     
     */
    public void setSpectralContraints(SpectralConstraints value) {
        this.spectralContraints = value;
    }

    /**
     * Gets the value of the contrainsMatrix property.
     * 
     * @return
     *     possible object is
     *     {@link Double2Matrix }
     *     
     */
    public Double2Matrix getContrainsMatrix() {
        return contrainsMatrix;
    }

    /**
     * Sets the value of the contrainsMatrix property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double2Matrix }
     *     
     */
    public void setContrainsMatrix(Double2Matrix value) {
        this.contrainsMatrix = value;
    }

    /**
     * Gets the value of the kinScal property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the kinScal property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKinScal().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KinPar }
     * 
     * 
     */
    public List<KinPar> getKinScal() {
        if (kinScal == null) {
            kinScal = new ArrayList<KinPar>();
        }
        return this.kinScal;
    }

}
