//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.0 
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2020.01.04 à 11:51:03 AM CET 
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
 *         &lt;element name="livreType" type="{http://www.webservice.org/bibliotheque-ws}livreType"/&gt;
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
    "livreType"
})
@XmlRootElement(name = "addLivreRequest")
public class AddLivreRequest {

    @XmlElement(required = true)
    protected LivreType livreType;

    /**
     * Obtient la valeur de la propriété livreType.
     * 
     * @return
     *     possible object is
     *     {@link LivreType }
     *     
     */
    public LivreType getLivreType() {
        return livreType;
    }

    /**
     * Définit la valeur de la propriété livreType.
     * 
     * @param value
     *     allowed object is
     *     {@link LivreType }
     *     
     */
    public void setLivreType(LivreType value) {
        this.livreType = value;
    }

}
