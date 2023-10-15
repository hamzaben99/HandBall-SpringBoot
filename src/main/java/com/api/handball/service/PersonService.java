package com.api.handball.service;

import com.api.handball.entity.Club;
import com.api.handball.entity.Person;
import com.api.handball.entity.dto.PersonDto;
import com.api.handball.repository.PersonRepository;
import com.api.handball.utils.PersonDtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonDtoMapper mapper;
    private final ClubService clubService;

    @Autowired
    public PersonService(PersonRepository personRepository, PersonDtoMapper mapper, ClubService clubService) {
        this.personRepository = personRepository;
        this.mapper = mapper;
        this.clubService = clubService;
    }

    public List<Person> getPeople() {
        return personRepository
                .findAll()
                .stream()
                .toList();
    }

    public Person getPersonById(Long personId) {
        return personRepository.findById(personId).orElse(null);

    }

    @Transactional
    public Long addPerson(PersonDto personDto) {
        Club club = clubService.getClubById(personDto.getClubId());
        if (club == null)
            throw new RuntimeException("club with id " + personDto.getClubId() + " doesn't exist.");

        Person person = mapper.map(personDto);
        return personRepository.save(person).getPersonId();
    }

    @Transactional
    public void deletePersonById(Long personId) {
        if (!personRepository.existsById(personId))
            throw new RuntimeException("id " + personId + " doesn't exist");
        personRepository.deleteById(personId);
    }

    @Transactional
    public void updatePerson(PersonDto updatedPersonDto, Long personId) {
        Person updatedPerson = personRepository.findById(personId).orElseThrow();

        Club club = clubService.getClubById(updatedPersonDto.getClubId());
        if (club == null)
            throw new RuntimeException("club with id " + updatedPersonDto.getClubId() + " doesn't exist.");

        updatedPerson.setFirstName(updatedPersonDto.getFirstName());
        updatedPerson.setLastName(updatedPersonDto.getLastName());
        updatedPerson.setAddress(updatedPersonDto.getAddress());
        updatedPerson.setBirthDate(updatedPersonDto.getBirthDate());
        updatedPerson.setEntryDate(updatedPersonDto.getEntryDate());
        updatedPerson.setPosition(updatedPersonDto.getPosition());
        updatedPerson.setClub(club);

        personRepository.save(updatedPerson);
    }

    void savePreUpdatedPerson(Person person) {
        personRepository.save(person);
    }
}