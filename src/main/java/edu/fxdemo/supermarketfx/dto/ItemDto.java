package edu.fxdemo.supermarketfx.dto;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ItemDto {


        private String itemId;
        private String itemName;
        private int quantity;
        private double price;


}
