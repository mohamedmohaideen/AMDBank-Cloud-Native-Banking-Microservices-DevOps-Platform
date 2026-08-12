package com.deena.accounts.Service;

import com.deena.accounts.Dto.AccountDto;
import com.deena.accounts.Dto.CardsDto;
import com.deena.accounts.Dto.CustomerDetailsDto;
import com.deena.accounts.Dto.LoansDto;
import com.deena.accounts.Entity.Accounts;
import com.deena.accounts.Entity.Customer;
import com.deena.accounts.Exception.ResourceNotFoundException;
import com.deena.accounts.Mapper.AccountsMapper;
import com.deena.accounts.Mapper.CustomerMapper;
import com.deena.accounts.Repository.AccountRepository;
import com.deena.accounts.Repository.CustomerRepository;
import com.deena.accounts.Service.Client.CardsFeignClient;
import com.deena.accounts.Service.Client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImp implements CustomerService {

    private AccountRepository accountsRepository;
    private CustomerRepository customerRepository;
    private LoansFeignClient loansFeignClient;
    private CardsFeignClient cardsFeignClient;


    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId,mobileNumber);
        if(null!=loansDtoResponseEntity) {
            customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
        }
        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId,mobileNumber);
        if(null!=cardsDtoResponseEntity) {
            customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
        }
        return customerDetailsDto;
    }
}
