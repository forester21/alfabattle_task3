package ru.forester.alfabattle.task3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.forester.alfabattle.task3.entity.BranchesEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class BranchesWithDistance {
    @Id
    private int id;
    private String title;
    private BigDecimal lon;
    private BigDecimal lat;
    private String address;
    private long distance;

    public BranchesWithDistance(BranchesEntity branchesEntity) {
        this.id = branchesEntity.getId();
        this.title = branchesEntity.getTitle();
        this.lon = branchesEntity.getLon();
        this.lat = branchesEntity.getLat();
        this.address = branchesEntity.getAddress();
        this.distance = 0;
    }
}
