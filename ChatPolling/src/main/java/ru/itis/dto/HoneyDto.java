package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.Honey;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HoneyDto {
    Long id;
    String name;
    int cost;
    String description;


    public static HoneyDto from(Honey honey) {
        return HoneyDto.builder()
                .id(honey.getId())
                .name(honey.getName())
                .cost(honey.getCost())
                .description(honey.getDescription())
                .build();
    }
    public static List<HoneyDto> from(List<Honey> honeys) {
        return honeys.stream()
                .map(HoneyDto::from)
                .collect(Collectors.toList());
    }
}