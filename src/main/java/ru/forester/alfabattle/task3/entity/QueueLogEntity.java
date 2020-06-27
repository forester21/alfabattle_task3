package ru.forester.alfabattle.task3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "queue_log", schema = "public", catalog = "alfa_battle")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueLogEntity {
    @Id
    private int id;
    private Date data;
    private Time startTimeOfWait;
    private Time endTimeOfWait;
    private Time endTimeOfService;
    private int branchesId;
}
