package org.example.app.dto.customer;

import org.example.app.entity.Customer;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

public record CustomerDtoListResponse(
        int statusCode,
        String reasonPhrase,
        boolean success,
        String message,
        List<Customer> customerList) {

    public static final String SUCCESS_MESSAGE = "Customer list has been fetched successfully.";
    public static final String FAILURE_MESSAGE = "Customer list has not been found!";

    public static CustomerDtoListResponse of(boolean isCustomerListEmpty, List<Customer> customerList) {
        if (isCustomerListEmpty)
            return new CustomerDtoListResponse(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    false, FAILURE_MESSAGE, Collections.emptyList());
        else
            return new CustomerDtoListResponse(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    true, SUCCESS_MESSAGE, customerList);
    }

}
