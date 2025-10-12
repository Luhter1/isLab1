package org.itmo.isLab1.dragons.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.itmo.isLab1.common.framework.dto.CrudDto;
import org.itmo.isLab1.coordinates.Coordinate;
import org.itmo.isLab1.dragoncaves.DragonCave;
import org.itmo.isLab1.dragonheads.DragonHead;
import org.itmo.isLab1.dragons.enums.DragonCharacter;
import org.itmo.isLab1.dragons.enums.DragonType;
import org.itmo.isLab1.people.Person;
import org.itmo.isLab1.people.enums.Color;

@Data
@EqualsAndHashCode(callSuper = true)
public class DragonDto extends CrudDto {
    private int id;
    private String name;
    private Coordinate coordinates;
    private DragonCave cave;
    private Person killer;
    private Integer age;
    private Color сolor;
    private DragonType type;
    private DragonCharacter character;
    private DragonHead head;
}

