package com.deena.loans.Service;

import com.deena.loans.Constants.LoansConstants;
import com.deena.loans.Dto.LoansDto;
import com.deena.loans.Entity.Loans;
import com.deena.loans.Exception.LoanAlreadyExistsException;
import com.deena.loans.Exception.ResourceNotFoundException;
import com.deena.loans.Mapper.LoansMapper;
import com.deena.loans.Repository.LoansRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class LoansServiceImp implements LoansService {

    private final LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);
        if(optionalLoans.isPresent())
        {
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber "+mobileNumber);
        }
        loansRepository.save(createNewLoans(mobileNumber));
    }

    private Loans createNewLoans(String mobileNumber)
    {
        Loans newLoans = new Loans();
         long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
         newLoans.setLoanNumber(Long.toString(randomLoanNumber));
         newLoans.setLoanType(LoansConstants.HOME_LOAN);
         newLoans.setMobileNumber(mobileNumber);
        newLoans.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoans.setAmountPaid(0);
        newLoans.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoans;
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(()-> new ResourceNotFoundException("Loans" , "mobileNumber" , mobileNumber));

        return LoansMapper.mapToLoansDto(loans, new LoansDto());

    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));
        LoansMapper.mapToLoans(loansDto, loans);
        loansRepository.save(loans);
        return  true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }
}
