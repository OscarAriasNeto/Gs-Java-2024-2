package br.com.fiap.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int id;
    private int userId;
    private String name;
    private double price;
    private int voltage;
    private Availability availability;
}

