package com.example.Project3REST.services;

import com.example.Project3REST.models.Measurement;
import com.example.Project3REST.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;

        this.sensorService = sensorService;
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    @Transactional
    public void addMeasurement(Measurement measurement) {
        enrichMeasurement(measurement);
        measurementRepository.save(measurement);
    }

    public void enrichMeasurement(Measurement measurement) {
        // мы должны сами найти сенсор из БД по имени и вставить объект из Hibernate persistence context'а
        measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()).get());

        measurement.setMeasurementDateTime(LocalDateTime.now());
    }


}
