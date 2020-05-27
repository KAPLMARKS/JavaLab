package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.BacketDto;
import ru.itis.dto.HoneyDto;
import ru.itis.security.UserSecurityImpl;
import ru.itis.services.HoneyService;

import java.util.List;

@Controller
public class HoneyController {
    @Autowired
    private HoneyService honeyService;

    @GetMapping(value = "/honey/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView getAddPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addHoney");
        return modelAndView;
    }

    @PostMapping(value = "/honey/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView addHoney(HoneyDto honeyDto) {
        honeyService.addHoney(honeyDto);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("check", "Honey added!");
        modelAndView.setViewName("addHoney");
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/honey")
    public String getProducts(Model model) {
        List<HoneyDto> honeys = honeyService.getAllProducts();
        model.addAttribute("honey", honeys);
        return "honey";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/honey/buy/{id}")
    public ModelAndView buyHoney(@PathVariable(value = "id") Long id, Authentication authentication) {
        UserSecurityImpl userDetails = (UserSecurityImpl) authentication.getPrincipal();
        honeyService.addBacket(BacketDto.builder()
                .owner(userDetails.getUser())
                .honeyId(id)
                .build());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("check", "Honey, give me my money: " + id);
        modelAndView.setViewName("honey");
        return modelAndView;
    }
}