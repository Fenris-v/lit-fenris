package org.example.app.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final Logger logger = LogManager.getRootLogger();

    public boolean authenticate(LoginForm loginForm) {
        logger.info("try auth with user-form: " + loginForm);
        return loginForm.getUsername().equals("root") && loginForm.getPassword().equals("123");
    }
}
