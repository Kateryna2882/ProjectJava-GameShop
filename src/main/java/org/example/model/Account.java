package org.example.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private int userId;
    private double balance;
}
