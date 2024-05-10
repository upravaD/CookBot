package com.upravad.cookbot.config;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import org.springframework.boot.test.context.SpringBootTest;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(TYPE)
@Retention(RUNTIME)
@SpringBootTest(classes = {
    ContainerConfiguration.class
})
public @interface CookBotTest {

}
