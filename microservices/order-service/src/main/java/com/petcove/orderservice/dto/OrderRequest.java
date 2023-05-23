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
    //@NotNull(message = "Customer Id cannot be null")
    @JsonProperty("customer_id")
    private Long customerId;

/*
    //@DecimalMin(value = "0.0", message = "Total amount must be greater than or equal to 0")
    @JsonProperty("total_amount")
    private BigDecimal totalAmount;

    //@NotNull(message = "Order date cannot be null")
    @JsonProperty("order_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;
    */

    @JsonProperty("order_items")
    private List<OrderLineItemsDto> orderLineItemsDtoList;

}
