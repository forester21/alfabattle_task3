package ru.forester.alfabattle.task3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.forester.alfabattle.task3.entity.QueueLogEntity;

import java.util.List;

public interface QueueLogRepository extends JpaRepository<QueueLogEntity, Integer> {

    List<QueueLogEntity> findAllByBranchesIdIs(int branchesId);
}
