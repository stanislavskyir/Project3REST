package com.example.Project3REST.services;

import com.example.Project3REST.models.Sensor;
import com.example.Project3REST.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> findAll(){ return sensorRepository.findAll();}

//    public Sensor findById(Integer id) {
//        Optional<Sensor> foundSensor = sensorRepository.findById(id);
//        return foundSensor.orElseThrow();
//    }

    public Optional<Sensor> findByName(String name) { return sensorRepository.findByName(name); }

    @Transactional
    public void save(Sensor sensor) {
        sensorRepository.save(sensor);
    }

}
