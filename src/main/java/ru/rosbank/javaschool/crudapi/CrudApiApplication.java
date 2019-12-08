package ru.rosbank.javaschool.crudapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.rosbank.javaschool.crudapi.entity.PostEntity;
import ru.rosbank.javaschool.crudapi.repository.PostRepository;
import java.util.List;

@SpringBootApplication
public class CrudApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(CrudApiApplication.class, args);
  }

  @Bean
  public CommandLineRunner runner(PostRepository repository) {
    return args -> repository.saveAll(List.of(
            new PostEntity(0, "First", null, false, 0),
            new PostEntity(0, "Second", null, false, 0),
            new PostEntity(0, "Third", null, false, 0),
            new PostEntity(0, "Four", null, false, 0),
            new PostEntity(0, "Five", null, false, 0)
    ));
  }

}
