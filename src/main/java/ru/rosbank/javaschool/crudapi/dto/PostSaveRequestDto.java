package ru.rosbank.javaschool.crudapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rosbank.javaschool.crudapi.model.PostModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveRequestDto {
  private int id;
  private String content;
  private String media;
}
