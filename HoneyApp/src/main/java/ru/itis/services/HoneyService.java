package ru.itis.services;

import ru.itis.dto.BacketDto;
import ru.itis.dto.HoneyDto;

import java.util.List;


public interface HoneyService {
    void addHoney(HoneyDto honeyDto);

    List<HoneyDto> getAllProducts();

    void addBacket(BacketDto backetDto);
}