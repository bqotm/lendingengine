package com.peerlender.lendingengine.domain.repository;

import com.peerlender.lendingengine.domain.model.Loan;
import com.peerlender.lendingengine.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findAllByBorrower(User borrower);//jpa takes care of figuring out this and that
    List<Loan> findAllByLender(User lender);
    Optional<Loan> findOneByIdAndBorrower(Long id, User borrower);

}
