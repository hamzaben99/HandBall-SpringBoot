package com.api.handball.service;

import com.api.handball.entity.Person;
import com.api.handball.entity.Team;
import com.api.handball.entity.dto.CoachDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoachService {
    private final PersonService personService;
    private final TeamService teamService;

    @Autowired
    public CoachService(PersonService personService, TeamService teamService) {
        this.personService = personService;
        this.teamService = teamService;
    }

    public List<Person> getCoachesByTeamId(Long teamId) {
        Team team = teamService.getTeamById(teamId);
        if (team == null)
            throw new RuntimeException("team " + teamId + " doesn't exist.");

        return List.copyOf(team.getCoaches());
    }

    public List<Team> getTeamTrainedBy(Long coachId) {
        Person person = personService.getPersonById(coachId);
        if (person == null)
            throw new RuntimeException("person " + coachId + " doesn't exist.");

        return List.copyOf(person.getTeams());
    }

    public List<CoachDto> getCoaches() {
        List<Team> teams = teamService.getTeams();
        return teams.stream()
                .flatMap(t -> {
                    List<CoachDto> coaches = new ArrayList<>();
                    for (Person p: teamService.getTeamById(t.getTeamId()).getCoaches())
                        coaches.add(new CoachDto(p.getPersonId(), t.getTeamId()));

                    return coaches.stream();
                })
                .toList();
    }

    @Transactional
    public void addCoach(CoachDto coachDto) {
        Person person = personService.getPersonById(coachDto.personId());
        Team team = teamService.getTeamById(coachDto.teamId());
        if (person == null || team == null)
            throw new RuntimeException("invalid input.");
        /* todo:
            person.addTeam(team)
            personService.savePreUpdatedPerson(person)
        */
        team.addCoach(person);
        teamService.savePreUpdatedTeam(team);
    }

    @Transactional
    public void deleteCoach(CoachDto coachDto) {
        Person person = personService.getPersonById(coachDto.personId());
        Team team = teamService.getTeamById(coachDto.teamId());
        if (person == null || team == null)
            throw new RuntimeException("invalid input.");

        team.removeCoach(person);
        teamService.savePreUpdatedTeam(team);
    }

    @Transactional
    public void updateCoach(CoachDto coachDto, Long newTeamId) {
        Person person = personService.getPersonById(coachDto.personId());
        Team team = teamService.getTeamById(coachDto.teamId());
        Team newTeam = teamService.getTeamById(newTeamId);
        if (person == null || team == null || newTeam == null)
            throw new RuntimeException("invalid input.");

        person.setClub(newTeam.getClub());
        personService.savePreUpdatedPerson(person);
        team.removeCoach(person);
        teamService.savePreUpdatedTeam(team);
        newTeam.addCoach(person);
        teamService.savePreUpdatedTeam(newTeam);
    }
}
