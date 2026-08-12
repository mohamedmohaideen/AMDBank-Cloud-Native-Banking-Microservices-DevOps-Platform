package com.deena.accounts.Service.Client;

import com.deena.accounts.Dto.CardsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeignClient{
    @Override
    public ResponseEntity<CardsDto> fetchCardDetails(String correlationId, String mobileNumber) {
        CardsDto cardsDto = new CardsDto();

        cardsDto.setCardType("Service Unavailable");

        return ResponseEntity.ok(cardsDto);
    }
}
