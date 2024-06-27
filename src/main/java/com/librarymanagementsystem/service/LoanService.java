package com.librarymanagementsystem.service;

import com.librarymanagementsystem.model.Loan;
import com.librarymanagementsystem.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    public Loan createLoan(Loan loan){
        return loanRepository.save(loan);
    }


    public Loan updateLoan(Long id, Loan loanDetails){
        return loanRepository.findById(id)
                .map(loan ->{
                    loan.setUser(loanDetails.getUser());
                    loan.setBooks(loanDetails.getBooks());
                    loan.setLoanDate(loanDetails.getLoanDate());
                    loan.setReturnDate(loanDetails.getReturnDate());
                    return loanRepository.save(loan);
                }).orElse(null);
    }

    public Optional<Loan> getLoanById(Long id){
        return loanRepository.findById(id);
    }

    public List<Loan> getAllLoans(){
        return loanRepository.findAll();
    }

    public void deleteLoan(Long id){
        loanRepository.deleteById(id);
    }

}
