package com.petcove.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.petcove.orderservice.model.OrderLineItems;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor

public class OrderRequest {
    //pass the order info to query their stock
    @NotNull(message = "Customer Id cannot be null")
    @JsonProperty("customer_id")
    private Long customerId;
    @NotEmpty(message = "Order items are required.")
    @JsonProperty("order_items")
    private List<OrderLineItemsDto> orderLineItemsDtoList;

}
