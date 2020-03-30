package ru.itis.services;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.dto.SignUpDto;
import ru.itis.dto.UserDto;
import ru.itis.models.State;
import ru.itis.models.User;
import ru.itis.repositories.UsersRepository;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ExecutorService threadPool;

    @Autowired
    private EmailService emailService;

    @Autowired
    private MessageService messageService;

    @SneakyThrows
    @Override
    public void signUp(SignUpDto form) {
        User user = User.builder()
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .username(form.getUsername())
                .confirmCode(UUID.randomUUID().toString())
                .state(State.NOT_CONFIRMED)
                .build();

        String code = messageService.fromEmailToFtl(user.getConfirmCode());


        threadPool.submit(() -> {
            emailService.sendMail("Регистрация", code,
                    user.getEmail());
        });
        usersRepository.save(user);
    }

    @Override
    public Optional<UserDto> confirm(String code) {
        Optional<User> optionalUser = usersRepository.findByConfirmCode(code);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setState(State.CONFIRMED);
            usersRepository.update(user.getEmail());
            return Optional.of(UserDto.builder()
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .id(user.getId())
                    .build());
        }
        return Optional.empty();
    }
}