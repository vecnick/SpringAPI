package nick.SpringAPI.controllers;


import nick.SpringAPI.models.Person;
import nick.SpringAPI.services.PeopleService;
import nick.SpringAPI.util.PersonErrorResponse;
import nick.SpringAPI.util.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping()
    public List<Person> getPeople(){
        return peopleService.findAll();
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable("id") int id){
        return peopleService.findOne(id);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse>handleException(PersonNotFoundException exception){
        PersonErrorResponse response = new PersonErrorResponse(
          "Person with this id wasnt found",
          System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); //ответ - 404 статус
    }
}
