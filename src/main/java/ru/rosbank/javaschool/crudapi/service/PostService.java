package ru.rosbank.javaschool.crudapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rosbank.javaschool.crudapi.dto.PostResponseDto;
import ru.rosbank.javaschool.crudapi.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.crudapi.entity.PostEntity;
import ru.rosbank.javaschool.crudapi.exception.BadRequestException;
import ru.rosbank.javaschool.crudapi.repository.PostRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
  private final PostRepository repository;

  public List<PostResponseDto> getAll() {
    return repository.findAll().stream()
            .map(PostResponseDto::from)
            .collect(Collectors.toList());
  }

  public List<PostResponseDto> getSomePosts(int lastPost,int step) {
    return repository.findAll().stream()
            .sorted((o1, o2) -> -(o1.getId() - o2.getId()))
            .skip(lastPost)
            .limit(step)
            .map(PostResponseDto::from)
            .collect(Collectors.toList());
  }

  public PostResponseDto save(PostSaveRequestDto dto) {
    return PostResponseDto.from(repository.save(PostEntity.from(dto)));
  }

  public void removeById(int id) {
    repository.deleteById(id);
  }

  public List<PostResponseDto> searchByContent(String q) {
    return repository.findAllByContentLike(q).stream()
            .map(PostResponseDto::from)
            .collect(Collectors.toList());
  }

  public PostResponseDto likeById(int id) {
    final PostEntity entity = repository.findById(id)
            .orElseThrow(BadRequestException::new);
    entity.setLikes(entity.getLikes() + 1);
    return PostResponseDto.from(entity);
  }

  public PostResponseDto dislikeById(int id) {
    final PostEntity entity = repository.findById(id)
            .orElseThrow(BadRequestException::new);
    if(entity.getLikes()>0){
      entity.setLikes(entity.getLikes() - 1);
    }
    return PostResponseDto.from(entity);
  }

  public int getCountOfNewPosts(int firstPostId) {
    Optional<PostEntity> firstPost = repository.findById(firstPostId);;
    List<Optional<PostEntity>> collect = repository.findAll().stream()
            .sorted((o1, o2) -> -(o1.getId() - o2.getId()))
            .map(Optional::of)
            .collect(Collectors.toList());
    return collect.indexOf(firstPost);
  }

  public int getFirstId() {
    List<PostResponseDto> collect = repository.findAll().stream()
            .sorted((o1, o2) -> -(o1.getId() - o2.getId()))
            .limit(1)
            .map(PostResponseDto::from)
            .collect(Collectors.toList());
    return collect.get(0).getId();
  }
}