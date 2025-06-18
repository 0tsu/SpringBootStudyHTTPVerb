package br.com.study.services;

import br.com.study.exception.ResourceNotFoundException;
import br.com.study.model.Person;
import br.com.study.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    public List<Person> findAll(){
        logger.info("Finding All Person!");
        return repository.findAll();

    }

    public Person findById(Long id){
        logger.info("Finding one Person!");

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found this ID")) ;
    }

    private Person mockPerson(int i) {
        Person person =new Person();
        person.setId(counter.incrementAndGet());
        person.setFirtName("FirtName " + i+1);
        person.setLestName("LestName " + i+1);
        person.setAddress("Sine Address in Brasil");
        person.setGender("Bolo de Chocolate");
        return person;
    }

    public Person create(Person person){
        logger.info("Create One Person!");
        return repository.save(person);
    }

    public Person update(Person person){
        logger.info("Updating One Person!");
        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No record found this ID")) ;

        entity.setFirtName(person.getFirtName());
        entity.setLestName(person.getLestName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return repository.save(person);
    }

    public void delete(Long id){
        logger.info("Deleting One Person!");

        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found this ID")) ;

        repository.delete(entity);
    }
}
