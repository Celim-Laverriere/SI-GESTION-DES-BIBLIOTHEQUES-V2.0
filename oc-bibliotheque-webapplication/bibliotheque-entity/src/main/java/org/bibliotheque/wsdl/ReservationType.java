//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.0 
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2019.12.17 à 10:45:41 AM CET 
//


package org.bibliotheque.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java pour reservationType complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="reservationType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="dateDemandeDeResa" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="numPositionResa" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ouvrageId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="compteId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reservationType", propOrder = {
    "id",
    "dateDemandeDeResa",
    "numPositionResa",
    "ouvrageId",
    "compteId"
})
public class ReservationType {

    protected int id;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateDemandeDeResa;
    protected int numPositionResa;
    protected int ouvrageId;
    protected int compteId;

    /**
     * Obtient la valeur de la propriété id.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Définit la valeur de la propriété id.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Obtient la valeur de la propriété dateDemandeDeResa.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateDemandeDeResa() {
        return dateDemandeDeResa;
    }

    /**
     * Définit la valeur de la propriété dateDemandeDeResa.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateDemandeDeResa(XMLGregorianCalendar value) {
        this.dateDemandeDeResa = value;
    }

    /**
     * Obtient la valeur de la propriété numPositionResa.
     * 
     */
    public int getNumPositionResa() {
        return numPositionResa;
    }

    /**
     * Définit la valeur de la propriété numPositionResa.
     * 
     */
    public void setNumPositionResa(int value) {
        this.numPositionResa = value;
    }

    /**
     * Obtient la valeur de la propriété ouvrageId.
     * 
     */
    public int getOuvrageId() {
        return ouvrageId;
    }

    /**
     * Définit la valeur de la propriété ouvrageId.
     * 
     */
    public void setOuvrageId(int value) {
        this.ouvrageId = value;
    }

    /**
     * Obtient la valeur de la propriété compteId.
     * 
     */
    public int getCompteId() {
        return compteId;
    }

    /**
     * Définit la valeur de la propriété compteId.
     * 
     */
    public void setCompteId(int value) {
        this.compteId = value;
    }

}
