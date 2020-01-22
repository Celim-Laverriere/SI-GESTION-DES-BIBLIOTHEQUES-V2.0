//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.0 
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.01.17 à 10:12:08 AM CET 
//


package org.bibliotheque.wsdl;

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
 *         &lt;element name="reservationType" type="{http://www.webservice.org/bibliotheque-ws}reservationType"/&gt;
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
    "reservationType"
})
@XmlRootElement(name = "addReservationRequest")
public class AddReservationRequest {

    @XmlElement(required = true)
    protected ReservationType reservationType;

    /**
     * Obtient la valeur de la propriété reservationType.
     * 
     * @return
     *     possible object is
     *     {@link ReservationType }
     *     
     */
    public ReservationType getReservationType() {
        return reservationType;
    }

    /**
     * Définit la valeur de la propriété reservationType.
     * 
     * @param value
     *     allowed object is
     *     {@link ReservationType }
     *     
     */
    public void setReservationType(ReservationType value) {
        this.reservationType = value;
    }

}
