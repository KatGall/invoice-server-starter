package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticsDTO;

import java.util.List;

public interface PersonService {

    PersonDTO addPerson(PersonDTO personDTO);

    void removePerson(long id);

    List<PersonDTO> getAll();

    PersonDTO editPerson(Long personId, PersonDTO personDTO);

    PersonDTO getPersonById(Long personId);


    List<PersonStatisticsDTO> getPersonStatistics();
}
