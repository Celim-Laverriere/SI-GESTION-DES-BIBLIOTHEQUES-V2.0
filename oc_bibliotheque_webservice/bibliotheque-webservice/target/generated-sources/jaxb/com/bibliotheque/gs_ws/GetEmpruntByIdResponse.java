//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.11 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.01.28 à 11:12:50 AM CET 
//


package com.bibliotheque.gs_ws;

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
 *         &lt;element name="empruntType" type="{http://www.webservice.org/bibliotheque-ws}empruntType"/&gt;
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
    "empruntType"
})
@XmlRootElement(name = "getEmpruntByIdResponse")
public class GetEmpruntByIdResponse {

    @XmlElement(required = true)
    protected EmpruntType empruntType;

    /**
     * Obtient la valeur de la propriété empruntType.
     * 
     * @return
     *     possible object is
     *     {@link EmpruntType }
     *     
     */
    public EmpruntType getEmpruntType() {
        return empruntType;
    }

    /**
     * Définit la valeur de la propriété empruntType.
     * 
     * @param value
     *     allowed object is
     *     {@link EmpruntType }
     *     
     */
    public void setEmpruntType(EmpruntType value) {
        this.empruntType = value;
    }

}