package com.api.handball.utils;

import com.api.handball.entity.Club;
import com.api.handball.entity.Person;
import com.api.handball.entity.dto.PersonDto;
import com.api.handball.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonDtoMapper {
    @Autowired
    private ClubService clubService;

    public Person map(PersonDto dto) {
        Person person = new Person();

        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person.setBirthDate(dto.getBirthDate());
        person.setEntryDate(dto.getEntryDate());
        person.setAddress(dto.getAddress());
        person.setPosition(dto.getPosition());
        person.setClub(clubService.getClubById(dto.getClubId()));

        return person;
    }

    public PersonDto map(Person person) {
        return PersonDto.builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .birthDate(person.getBirthDate())
                .address(person.getAddress())
                .clubId(person.getClub().getClubId())
                .position(person.getPosition())
                .entryDate(person.getEntryDate())
                .build();
    }
}
