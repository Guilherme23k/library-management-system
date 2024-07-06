package com.librarymanagementsystem.controller;

import com.librarymanagementsystem.dto.LoanDTO;
import com.librarymanagementsystem.model.Loan;
import com.librarymanagementsystem.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.librarymanagementsystem.model.Book;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping
    public ResponseEntity<LoanDTO> createLoan(@RequestBody LoanDTO loanDTO) {
        Loan createdLoan = loanService.createLoan(loanDTO);
        LoanDTO respondeDTO = mapToDTO(createdLoan);
        return ResponseEntity.ok(respondeDTO);
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
    public ResponseEntity<LoanDTO> getLoanById(@PathVariable Long id) {
        Loan loan = loanService.getLoanById(id);
        if (loan == null) {
            return ResponseEntity.notFound().build();
        }
        LoanDTO loanDTO = mapToDTO(loan);
        return ResponseEntity.ok(loanDTO);
    }
    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        List<Loan> loans = loanService.getAllLoans();
        List<LoanDTO> loanDTOs = loans.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(loanDTOs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Loan> deteleLoan(@PathVariable Long id){
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }

    private LoanDTO mapToDTO(Loan loan) {
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setId(loan.getId());
        loanDTO.setUserId(loan.getUser().getId());

        List<Long> bookIds = loan.getBooks().stream()
                .map(Book::getId)
                .collect(Collectors.toList());

        loanDTO.setBookIds(bookIds);
        loanDTO.setLoanDate(loan.getLoanDate());
        loanDTO.setReturnDate(loan.getReturnDate());
        return loanDTO;
    }


}
