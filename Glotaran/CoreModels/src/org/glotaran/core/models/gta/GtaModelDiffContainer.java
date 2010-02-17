//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.17 at 10:59:16 PM CET 
//


package org.glotaran.core.models.gta;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GtaModelDiffContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GtaModelDiffContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="free" type="{http://glotaran.org/schema/GlobalAndTargetAnalysisSchema}GtaModelDiffDO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="remove" type="{http://glotaran.org/schema/GlobalAndTargetAnalysisSchema}GtaModelDiffDO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="add" type="{http://glotaran.org/schema/GlobalAndTargetAnalysisSchema}GtaModelDiffDO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="changes" type="{http://glotaran.org/schema/GlobalAndTargetAnalysisSchema}GtaChangesModel" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GtaModelDiffContainer", propOrder = {
    "free",
    "remove",
    "add",
    "changes"
})
public class GtaModelDiffContainer {

    @XmlElement(nillable = true)
    protected List<GtaModelDiffDO> free;
    @XmlElement(nillable = true)
    protected List<GtaModelDiffDO> remove;
    @XmlElement(nillable = true)
    protected List<GtaModelDiffDO> add;
    @XmlElement(nillable = true)
    protected List<GtaChangesModel> changes;

    /**
     * Gets the value of the free property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the free property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFree().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GtaModelDiffDO }
     * 
     * 
     */
    public List<GtaModelDiffDO> getFree() {
        if (free == null) {
            free = new ArrayList<GtaModelDiffDO>();
        }
        return this.free;
    }

    /**
     * Gets the value of the remove property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the remove property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRemove().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GtaModelDiffDO }
     * 
     * 
     */
    public List<GtaModelDiffDO> getRemove() {
        if (remove == null) {
            remove = new ArrayList<GtaModelDiffDO>();
        }
        return this.remove;
    }

    /**
     * Gets the value of the add property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the add property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdd().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GtaModelDiffDO }
     * 
     * 
     */
    public List<GtaModelDiffDO> getAdd() {
        if (add == null) {
            add = new ArrayList<GtaModelDiffDO>();
        }
        return this.add;
    }

    /**
     * Gets the value of the changes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the changes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChanges().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GtaChangesModel }
     * 
     * 
     */
    public List<GtaChangesModel> getChanges() {
        if (changes == null) {
            changes = new ArrayList<GtaChangesModel>();
        }
        return this.changes;
    }

}
