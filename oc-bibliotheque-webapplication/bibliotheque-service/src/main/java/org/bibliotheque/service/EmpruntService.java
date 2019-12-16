package org.bibliotheque.service;

import org.bibliotheque.repository.EmpruntRepository;
import org.bibliotheque.repository.LivreRepository;
import org.bibliotheque.repository.OuvrageRepository;
import org.bibliotheque.wsdl.EmpruntType;
import org.bibliotheque.wsdl.LivreType;
import org.bibliotheque.wsdl.OuvrageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


@Service
public class EmpruntService {

    @Autowired
    private EmpruntRepository empruntRepository;

    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private OuvrageRepository ouvrageRepository;


    /**
     * ==== CETTE METHODE RECUPERER TOUS LES EMPRUNTS ====
     * @return LA LISTE DES EMPRUNTS
     */
    public List<EmpruntType> getAllEmprunt(){
        return empruntRepository.getAllEmprunt();
    }


    /**
     * ==== CETTE METHODE RECUPERER TOUS LES EMPRUNTS D'UN CLIENT ====
     * @param id
     * @return LA LISTE DES EMPRUNTS D'UN COMPTE
     * @see EmpruntRepository#getAllEmpruntByCompteId(Integer)
     */
    public List<EmpruntType> getAllEmpruntByCompteId(Integer id){
        return empruntRepository.getAllEmpruntByCompteId(id);
    }


    /**
     * ==== CETTE METHODE RECUPERER TOUS LES LIVRES EMPRUNTES PAR UN CLIENT ====
     * @param empruntTypeList
     * @return UNE LISTE DE LIVRES
     * @see LivreRepository#livreById(Integer)
     */
    public List<LivreType> livreTypeListEmprunter(List<EmpruntType> empruntTypeList){

        List<LivreType> livreTypeList = new ArrayList<>();

        for (EmpruntType empruntType : empruntTypeList){
            LivreType livreType = livreRepository.livreById(empruntType.getLivreId());
            livreTypeList.add(livreType);
        }

        return livreTypeList;
    }

    /**
     * ==== CETTE METHODE RECUPERER TOUS LES OUVRAGES DES LIVRES EMPRUNTES PAR LE CLIENT ====
     * @param livreTypeList
     * @return UNE LISTE D'OUVRAGES
     * @see OuvrageRepository#ouvrageById(Integer)
     */
    public List<OuvrageType> ouvrageTypeListEmprunter(List<LivreType> livreTypeList){

        List<OuvrageType> ouvrageTypeList = new ArrayList<>();

        for (LivreType livreType : livreTypeList){
            OuvrageType ouvrageType = ouvrageRepository.ouvrageById(livreType.getOuvrageId());
            ouvrageTypeList.add(ouvrageType);
        }

        return ouvrageTypeList;
    }


    /**
     * ==== CETTE METHODE MET A JOUR LA DATE DE FIN D'UN EMPRUNT SUITE A UNE PROLONGATION DE CELUI-CI ====
     * @param empruntId
     * @return UN STATUT-CODE DE CONFIRMATION
     * @see EmpruntRepository#getEmpruntById(Integer)
     * @see EmpruntRepository#upEmpruntType(EmpruntType)
     */
    public String upEmpruntProlongation(Integer empruntId) throws DatatypeConfigurationException, ParseException {

        EmpruntType empruntType = empruntRepository.getEmpruntById(empruntId);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(empruntType.getDateFin().toString());

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(GregorianCalendar.WEEK_OF_MONTH, 4);
        XMLGregorianCalendar dateProlongation = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        empruntType.setDateFin(dateProlongation);
        empruntType.setProlongation(true);

        String statusCode = empruntRepository.upEmpruntType(empruntType);

        return statusCode;
    }
}
