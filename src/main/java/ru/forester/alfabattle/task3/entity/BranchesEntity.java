package ru.forester.alfabattle.task3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Table(name = "branches", schema = "public", catalog = "alfa_battle")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class BranchesEntity {
    @Id
    private int id;
    private String title;
    private BigDecimal lon;
    private BigDecimal lat;
    private String address;
}
