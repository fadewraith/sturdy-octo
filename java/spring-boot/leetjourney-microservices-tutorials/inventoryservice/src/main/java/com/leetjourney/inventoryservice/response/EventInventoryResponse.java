package com.leetjourney.inventoryservice.response;

import java.math.BigDecimal;

import com.leetjourney.inventoryservice.entity.Venue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventInventoryResponse {

    private Long eventId;
    private String event;
    private Long capacity;
    private Venue venue;
    private BigDecimal ticketPrice;

}
