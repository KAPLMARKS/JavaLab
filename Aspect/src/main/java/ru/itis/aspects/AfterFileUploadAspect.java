package ru.itis.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.models.FileInfo;
import ru.itis.models.User;
import ru.itis.repositories.UsersRepository;
import ru.itis.services.EmailService;
import java.util.Optional;

@Component
@Aspect
public class AfterFileUploadAspect {
    @Autowired
    private EmailService emailService;

    @Autowired
    private UsersRepository usersRepository;

    private final String link = "localhost:8080/files/";

    @AfterReturning(pointcut = "execution(* ru.itis.services.FileServiceImpl.saveFile(..))", returning= "file")
    public void uploadAfter(FileInfo file){
        Optional<User> user = usersRepository.find(file.getUserId());
        emailService.sendMail("Был загружен файл",
                "Link: <a>" + link + file.getStorageFileName() + "</a>",
                user.get().getEmail());
    }
}