package com.reactive.server.stream.event.dto;

import com.reactive.server.stream.event.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductEvent {

    private String name;
    private int price;
    private User user;
}
