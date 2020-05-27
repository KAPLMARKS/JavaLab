package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BacketDto {
    Long id;
    Long honeyId;
    User owner;
}