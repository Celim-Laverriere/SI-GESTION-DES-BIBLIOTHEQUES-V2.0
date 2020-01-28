//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.11 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.01.28 à 11:12:50 AM CET 
//


package com.bibliotheque.gs_ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="empruntType" type="{http://www.webservice.org/bibliotheque-ws}empruntType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="serviceStatus" type="{http://www.webservice.org/bibliotheque-ws}serviceStatus"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "empruntType",
    "serviceStatus"
})
@XmlRootElement(name = "getAllEmpruntByCompteIdResponse")
public class GetAllEmpruntByCompteIdResponse {

    protected List<EmpruntType> empruntType;
    @XmlElement(required = true)
    protected ServiceStatus serviceStatus;

    /**
     * Gets the value of the empruntType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the empruntType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEmpruntType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EmpruntType }
     * 
     * 
     */
    public List<EmpruntType> getEmpruntType() {
        if (empruntType == null) {
            empruntType = new ArrayList<EmpruntType>();
        }
        return this.empruntType;
    }

    /**
     * Obtient la valeur de la propriété serviceStatus.
     * 
     * @return
     *     possible object is
     *     {@link ServiceStatus }
     *     
     */
    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    /**
     * Définit la valeur de la propriété serviceStatus.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceStatus }
     *     
     */
    public void setServiceStatus(ServiceStatus value) {
        this.serviceStatus = value;
    }

}
