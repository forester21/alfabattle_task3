package ru.forester.alfabattle.task3.service;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.forester.alfabattle.task3.entity.BranchesEntity;
import ru.forester.alfabattle.task3.entity.QueueLogEntity;
import ru.forester.alfabattle.task3.model.BranchesWithDistance;
import ru.forester.alfabattle.task3.model.BranchesWithPrediction;
import ru.forester.alfabattle.task3.repository.BranchesRepository;
import ru.forester.alfabattle.task3.repository.QueueLogRepository;
import ru.forester.alfabattle.task3.util.GeoDistance;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BranchesService {

    @Autowired
    private BranchesRepository branchesRepository;

    @Autowired
    private QueueLogRepository queueLogRepository;

    public Optional<BranchesEntity> getById(int id){
        return branchesRepository.findById(id);
    }

    public BranchesWithDistance findNearest(BigDecimal lat, BigDecimal lon) {
        return branchesRepository.findAll().stream()
                .map(BranchesWithDistance::new)
                .map(
                        it -> it.setDistance(Math.round(
                                GeoDistance.calculateDistance(lat.doubleValue(),
                                        it.getLat().doubleValue(), lon.doubleValue(), it.getLon().doubleValue())
                                        * 1000)
                        )).sorted(Comparator.comparingLong(BranchesWithDistance::getDistance)).findFirst().get();
    }

    public BranchesWithPrediction calculatePrediction(int id, int dayOfWeek, int hourOfDay) {
        Optional<BranchesEntity> optionalBranchesEntity = branchesRepository.findById(id);
        if (!optionalBranchesEntity.isPresent()) {
            throw new RuntimeException("not found");
        }
        BranchesWithPrediction branchesWithPrediction = new BranchesWithPrediction(optionalBranchesEntity.get());
        branchesWithPrediction.setDayOfWeek(dayOfWeek);
        branchesWithPrediction.setHourOfDay(hourOfDay);

        List<QueueLogEntity> queueLogEntities = queueLogRepository.findAllByBranchesIdIs(id)
                .stream()
                .filter(it -> it.getData().toLocalDate().getDayOfWeek().getValue() == dayOfWeek)
                .filter(it -> it.getEndTimeOfWait().toLocalTime().getHour() == hourOfDay)
                .collect(Collectors.toList());

        double[] ranges = ArrayUtils.toPrimitive(queueLogEntities.stream().map(it ->
                Long.valueOf(Duration.between(it.getStartTimeOfWait().toLocalTime(), it.getEndTimeOfWait().toLocalTime())
                .toMillis() / 1000).doubleValue()).toArray(Double[]::new));

        Median median = new Median();
        branchesWithPrediction.setPredicting(Math.round(median.evaluate(ranges)));
        return branchesWithPrediction;
    }
}
