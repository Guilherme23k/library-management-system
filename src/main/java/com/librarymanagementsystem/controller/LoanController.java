package com.librarymanagementsystem.controller;

import com.librarymanagementsystem.model.Loan;
import com.librarymanagementsystem.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan){
        Loan loanCreated = loanService.createLoan(loan);
        return ResponseEntity.ok(loanCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Loan> updateLoan(@PathVariable Long id, @RequestBody Loan loanDetails){
        Loan updatedLoan = loanService.updateLoan(id, loanDetails);
        if (updatedLoan != null){
            return ResponseEntity.ok(updatedLoan);
        }else {
            return ResponseEntity.notFound().build();
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long id){
        Optional<Loan> loan = loanService.getLoanById(id);
        if (loan.isPresent()){
            return ResponseEntity.ok(loan.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<Loan>> getAllLoans(){
        List<Loan> loans = loanService.getAllLoans();
        return ResponseEntity.ok(loans);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Loan> deteleLoan(@PathVariable Long id){
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }


}
