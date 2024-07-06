package com.librarymanagementsystem.service;

import com.librarymanagementsystem.dto.LoanDTO;
import com.librarymanagementsystem.model.Loan;
import com.librarymanagementsystem.repository.BookRepository;
import com.librarymanagementsystem.repository.LoanRepository;
import com.librarymanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public Loan createLoan(LoanDTO loanDTO) {
        Loan loan = new Loan();
        loan.setUser(userRepository.findById(loanDTO.getUserId()).orElse(null));
        loan.setBooks(bookRepository.findAllById(loanDTO.getBookIds()));
        loan.setLoanDate(loanDTO.getLoanDate());
        loan.setReturnDate(loanDTO.getReturnDate());

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

    public Loan getLoanById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    public List<Loan> getAllLoans(){
        return loanRepository.findAll();
    }

    public void deleteLoan(Long id){
        loanRepository.deleteById(id);
    }

}
