package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.models.User;
import ru.itis.security.UserSecurityImpl;
import ru.itis.services.FileService;
import javax.servlet.http.HttpServletResponse;

@Controller
public class FilesController {
    @Autowired
    private FileService fileService;

    @GetMapping(value = "/files")
    public ModelAndView uploadFile() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("file_upload");
        return modelAndView;
    }

    @PostMapping(value = "/files")
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile multipartFile, Authentication authentication) {
        UserSecurityImpl userDetails = (UserSecurityImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        fileService.saveFile(multipartFile, user.getId());
        ModelAndView model = new ModelAndView();
        model.addObject("check", "file uploaded");
        model.setViewName("file_upload");
        return model;
    }

    // localhost:8080/files/123809183093qsdas09df8af.jpeg

    @GetMapping(value ="/files/{file-name:.+}")
    public void getFile(@PathVariable("file-name") String fileName, HttpServletResponse response) {
        fileService.writeFileToResponse(fileName, response);
    }
}