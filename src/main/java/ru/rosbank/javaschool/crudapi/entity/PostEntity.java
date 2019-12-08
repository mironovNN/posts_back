package ru.rosbank.javaschool.crudapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import ru.rosbank.javaschool.crudapi.dto.PostSaveRequestDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {
  private int id;
  private String content;
  private String media;
  private boolean removed;
  private int likes;

  public static PostEntity from(PostSaveRequestDto dto) {
    return new PostEntity(dto.getId(), dto.getContent(), dto.getMedia(), false, 0);
  }
}
