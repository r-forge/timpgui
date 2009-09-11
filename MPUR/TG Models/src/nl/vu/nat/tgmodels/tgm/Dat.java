//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.09.11 at 10:39:34 AM CEST 
//


package nl.vu.nat.tgmodels.tgm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Dat complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Dat">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="kinparPanel" type="{http://www.nat.vu.nl/~jsnel/Schema/TgmSchema}KinparPanelModel"/>
 *         &lt;element name="modelName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="modType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="irfparPanel" type="{http://www.nat.vu.nl/~jsnel/Schema/TgmSchema}IrfparPanelModel"/>
 *         &lt;element name="cohspecPanel" type="{http://www.nat.vu.nl/~jsnel/Schema/TgmSchema}CohspecPanelModel"/>
 *         &lt;element name="flimPanel" type="{http://www.nat.vu.nl/~jsnel/Schema/TgmSchema}FlimPanelModel"/>
 *         &lt;element name="kMatrixPanel" type="{http://www.nat.vu.nl/~jsnel/Schema/TgmSchema}KMatrixPanelModel"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dat", propOrder = {
    "kinparPanel",
    "modelName",
    "modType",
    "irfparPanel",
    "cohspecPanel",
    "flimPanel",
    "kMatrixPanel"
})
public class Dat {

    @XmlElement(required = true, nillable = true)
    protected KinparPanelModel kinparPanel;
    @XmlElement(required = true, defaultValue = "SpecifyModelName", nillable = true)
    protected String modelName;
    @XmlElement(required = true, defaultValue = "kin", nillable = true)
    protected String modType;
    @XmlElement(required = true, nillable = true)
    protected IrfparPanelModel irfparPanel;
    @XmlElement(required = true, nillable = true)
    protected CohspecPanelModel cohspecPanel;
    @XmlElement(required = true, nillable = true)
    protected FlimPanelModel flimPanel;
    @XmlElement(required = true, nillable = true)
    protected KMatrixPanelModel kMatrixPanel;

    /**
     * Gets the value of the kinparPanel property.
     * 
     * @return
     *     possible object is
     *     {@link KinparPanelModel }
     *     
     */
    public KinparPanelModel getKinparPanel() {
        return kinparPanel;
    }

    /**
     * Sets the value of the kinparPanel property.
     * 
     * @param value
     *     allowed object is
     *     {@link KinparPanelModel }
     *     
     */
    public void setKinparPanel(KinparPanelModel value) {
        this.kinparPanel = value;
    }

    /**
     * Gets the value of the modelName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * Sets the value of the modelName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelName(String value) {
        this.modelName = value;
    }

    /**
     * Gets the value of the modType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModType() {
        return modType;
    }

    /**
     * Sets the value of the modType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModType(String value) {
        this.modType = value;
    }

    /**
     * Gets the value of the irfparPanel property.
     * 
     * @return
     *     possible object is
     *     {@link IrfparPanelModel }
     *     
     */
    public IrfparPanelModel getIrfparPanel() {
        return irfparPanel;
    }

    /**
     * Sets the value of the irfparPanel property.
     * 
     * @param value
     *     allowed object is
     *     {@link IrfparPanelModel }
     *     
     */
    public void setIrfparPanel(IrfparPanelModel value) {
        this.irfparPanel = value;
    }

    /**
     * Gets the value of the cohspecPanel property.
     * 
     * @return
     *     possible object is
     *     {@link CohspecPanelModel }
     *     
     */
    public CohspecPanelModel getCohspecPanel() {
        return cohspecPanel;
    }

    /**
     * Sets the value of the cohspecPanel property.
     * 
     * @param value
     *     allowed object is
     *     {@link CohspecPanelModel }
     *     
     */
    public void setCohspecPanel(CohspecPanelModel value) {
        this.cohspecPanel = value;
    }

    /**
     * Gets the value of the flimPanel property.
     * 
     * @return
     *     possible object is
     *     {@link FlimPanelModel }
     *     
     */
    public FlimPanelModel getFlimPanel() {
        return flimPanel;
    }

    /**
     * Sets the value of the flimPanel property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlimPanelModel }
     *     
     */
    public void setFlimPanel(FlimPanelModel value) {
        this.flimPanel = value;
    }

    /**
     * Gets the value of the kMatrixPanel property.
     * 
     * @return
     *     possible object is
     *     {@link KMatrixPanelModel }
     *     
     */
    public KMatrixPanelModel getKMatrixPanel() {
        return kMatrixPanel;
    }

    /**
     * Sets the value of the kMatrixPanel property.
     * 
     * @param value
     *     allowed object is
     *     {@link KMatrixPanelModel }
     *     
     */
    public void setKMatrixPanel(KMatrixPanelModel value) {
        this.kMatrixPanel = value;
    }

}
