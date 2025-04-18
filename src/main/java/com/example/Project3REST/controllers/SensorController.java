package com.example.Project3REST.controllers;

import com.example.Project3REST.dto.SensorDTO;
import com.example.Project3REST.models.Sensor;
import com.example.Project3REST.services.SensorService;
import com.example.Project3REST.util.ErrorsUtil;
import com.example.Project3REST.util.MeasurementException;
import com.example.Project3REST.util.SensorErrorResponse;
import com.example.Project3REST.util.SensorNotCreatedException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> sensorRegistration(@RequestBody @Valid SensorDTO sensorDTO,
                                                         BindingResult bindingResult) {

        Sensor sensorToAdd = convertToSensor(sensorDTO);

//        if (bindingResult.hasErrors()) {
//            StringBuilder errorMsg = new StringBuilder();
//
//            List<FieldError> errors = bindingResult.getFieldErrors();
//            for (FieldError error : errors) {
//                errorMsg.append(error.getField())
//                        .append(" - ").append(error.getDefaultMessage())
//                        .append(";");
//            }
//            throw new SensorNotCreatedException((errorMsg.toString()));
//        }

        if (bindingResult.hasErrors()) {
            throw new SensorNotCreatedException(ErrorsUtil.returnErrorsToClient(bindingResult));
        }

        sensorService.register(sensorToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
