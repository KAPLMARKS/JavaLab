package ru.itis.services;

import freemarker.template.TemplateException;
import java.io.IOException;

public interface MessageService {
    String fromEmailToFtl(String email) throws IOException, TemplateException;
}