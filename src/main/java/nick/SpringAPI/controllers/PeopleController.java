package nick.SpringAPI.controllers;


import jakarta.validation.Valid;
import nick.SpringAPI.DTO.PersonDTO;
import nick.SpringAPI.models.Person;
import nick.SpringAPI.services.PeopleService;
import nick.SpringAPI.util.PersonErrorResponse;
import nick.SpringAPI.util.PersonNotCreatedException;
import nick.SpringAPI.util.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @PostMapping
    public ResponseEntity<HttpStatus>create(@RequestBody @Valid PersonDTO personDTO,
                                            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors){
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PersonNotCreatedException(errorMsg.toString());
        }

        peopleService.save(convertToPerson(personDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }




    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse>handleException(PersonNotFoundException exception){
        PersonErrorResponse response = new PersonErrorResponse(
          "Person with this id wasnt found",
          System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); //?????????? - 404 ????????????
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse>handleException(PersonNotCreatedException exception){
        PersonErrorResponse response = new PersonErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); //?????????? - 400 ????????????
    }
    private Person convertToPerson(PersonDTO personDTO) {
        Person person = new Person();

        person.setName(personDTO.getName());
        person.setAge(personDTO.getAge());
        person.setEmail(personDTO.getEmail());

        return person;
    }

}
