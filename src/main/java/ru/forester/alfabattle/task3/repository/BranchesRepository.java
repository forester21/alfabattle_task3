package ru.forester.alfabattle.task3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.forester.alfabattle.task3.entity.BranchesEntity;

public interface BranchesRepository extends JpaRepository<BranchesEntity, Integer> {
}
