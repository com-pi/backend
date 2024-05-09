package com.example.myplant.application;

import com.example.myplant.application.port.in.RegisterPlantCommand;
import com.example.myplant.application.port.in.PostPlantUseCase;
import com.example.myplant.domain.Plant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostRegisterPlantService implements PostPlantUseCase {


    public void postRegisterPlant(RegisterPlantCommand command) {


    }
}
