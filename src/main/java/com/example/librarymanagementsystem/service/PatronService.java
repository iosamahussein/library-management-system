package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.entity.Patron;
import com.example.librarymanagementsystem.exception.EntityAlreadyExistsException;
import com.example.librarymanagementsystem.exception.ResourceNotFoundException;
import com.example.librarymanagementsystem.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PatronService {

    @Autowired
    private PatronRepository patronRepository;

    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    public Patron getPatronById(Long id) {
        if (!patronRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patron not found with id: " + id);
        }
        return patronRepository.findById(id).get();
    }

    public Patron addPatron(Patron patron) {
        
        if (patronRepository.existsByEmail(patron.getEmail())) {
            throw new EntityAlreadyExistsException ("Patron with email " + patron.getEmail() + " already exists");
        }
        return patronRepository.save(patron);
    }

    public Patron updatePatron(Long id, Patron patronDetails) {
        Patron patron = patronRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patron not found with id: " + id));
        patron.setName(patronDetails.getName());
        patron.setEmail(patronDetails.getEmail());
        patron.setPhone(patronDetails.getPhone());
        patron.setBirthdate(patronDetails.getBirthdate());
        return patronRepository.save(patron);
    }

    public void deletePatron(Long id) {
        patronRepository.deleteById(id);
    }
}
