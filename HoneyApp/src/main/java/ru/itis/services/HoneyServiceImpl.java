package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dto.BacketDto;
import ru.itis.dto.HoneyDto;
import ru.itis.models.Backet;
import ru.itis.models.Honey;
import ru.itis.repositories.BacketRepository;
import ru.itis.repositories.HoneyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HoneyServiceImpl implements HoneyService {

    @Autowired
    private HoneyRepository honeyRepository;

    @Autowired
    private BacketRepository backetRepository;

    @Override
    public void addHoney(HoneyDto honeyDto) {
        Honey honey = Honey.builder()
                .name(honeyDto.getName())
                .description(honeyDto.getDescription())
                .cost(honeyDto.getCost())
                .build();
        honeyRepository.save(honey);
    }

    @Override
    public List<HoneyDto> getAllProducts() {
        return HoneyDto.from(honeyRepository.findAll());
    }

    @Override
    public void addBacket(BacketDto backetDto) {
        Optional<Honey> honey= honeyRepository.find(backetDto.getHoneyId());

        if (honey.isPresent()) {
            backetRepository.save(Backet.builder()
                    .honeyId(honey.get())
                    .owner(backetDto.getOwner())
                    .build());
        } else {
            throw new IllegalArgumentException();
        }
    }


}