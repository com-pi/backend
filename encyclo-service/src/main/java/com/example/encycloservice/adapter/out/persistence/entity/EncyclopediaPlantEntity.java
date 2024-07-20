package com.example.encycloservice.adapter.out.persistence.entity;

import com.example.common.baseentity.BaseTimeAbstractEntity;
import com.example.encycloservice.domain.PlantBrief;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter
@Entity
@Table(name = "encyclopedia_plant")
public class  EncyclopediaPlantEntity extends BaseTimeAbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "plant_species_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PlantSpeciesEntity plantEntity;

    @JoinColumn(name = "my_encyclopedia_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private MyEncyclopediaEntity myEncyclopediaEntity;

    public PlantBrief toBrief(){
        if(Hibernate.isInitialized(this)){
            return plantEntity.toBrief();
        } else {
            return new PlantBrief(plantEntity.getId());
        }
    }

}
