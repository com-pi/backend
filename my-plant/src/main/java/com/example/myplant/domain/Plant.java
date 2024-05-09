package com.example.myplant.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "my_plant")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "generate")

public class RegistPlant {


    @Id
    private Long id;

}
