package com.upravad.cookbot;

import static com.upravad.cookbot.database.enums.Category.BREAKFAST;

import com.upravad.cookbot.database.dto.DishDto;
import com.upravad.cookbot.database.enums.Category;
import com.upravad.cookbot.service.DishesServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CookBotApplication {

  public static void main(String[] args) {
    SpringApplication.run(CookBotApplication.class, args);
  }

}
