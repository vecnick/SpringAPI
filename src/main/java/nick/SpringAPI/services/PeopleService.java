package nick.SpringAPI.services;

import nick.SpringAPI.models.Person;
import nick.SpringAPI.repositories.PeopleRepository;
import nick.SpringAPI.util.PersonNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findOne(int id){
         Optional<Person> foundPerson =  peopleRepository.findById(id);
         return foundPerson.orElseThrow(PersonNotFoundException::new);
    }

//    @Transactional
//    public void save(Person person){
//        peopleRepository.save(person);
//    }
//
//    @Transactional
//    public void update(int id, Person updatedPerson){
//        updatedPerson.setId(id);
//        peopleRepository.save(updatedPerson);
//    }
//
//    @Transactional
//    public void delete(int id){
//        peopleRepository.deleteById(id);
//    }
//    public Optional<Person> getPersonByName(String fullName) {
//        return peopleRepository.findByName(fullName);
//    }
//
//    public List<Book> getBooksByPersonId(int id) {
//        Optional<Person> person = peopleRepository.findById(id);
//
//        if (person.isPresent()) {
//            Hibernate.initialize(person.get().getBooks());
//            // Мы внизу итерируемся по книгам, поэтому они точно будут загружены, но на всякий случай
//            // не мешает всегда вызывать Hibernate.initialize()
//            // (на случай, например, если код в дальнейшем поменяется и итерация по книгам удалится)
//
//            // Проверка просроченности книг
//            person.get().getBooks().forEach(book -> {
//                long diffInMillies = Math.abs(book.getTakenAt().getTime() - new Date().getTime());
//                // 864000000 милисекунд = 10 суток
//                if (diffInMillies > 864000000)
//                    book.setExpired(true); // книга просрочена
//            });
//
//            return person.get().getBooks();
//        }
//        else {
//            return Collections.emptyList();
//        }
//    }
//    public void test(){
//        System.out.println("Testing here with debug. Inside Hibernate transaction");
//    }
}
