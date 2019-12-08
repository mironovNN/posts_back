package ru.rosbank.javaschool.crudapi.rest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.rosbank.javaschool.crudapi.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.crudapi.dto.PostResponseDto;
import ru.rosbank.javaschool.crudapi.service.PostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class RestPostController {
  private final PostService service;
  private final Logger logger = LoggerFactory.getLogger(RestPostController.class);

  @GetMapping(params = {"last","step"})
  public List<PostResponseDto> getSomePosts(@RequestParam("last") int lastPost, @RequestParam("step") int step) {
    logger.info(Thread.currentThread().getName());
    return service.getSomePosts(lastPost,step);
  }

  @GetMapping(params = {"first"})
  public int getCountOfNewPosts(@RequestParam("first") int firstPostId) {
    logger.info(Thread.currentThread().getName());
    return service.getCountOfNewPosts(firstPostId);
  }

  @GetMapping
  public int getFirstPostId() {
    logger.info(Thread.currentThread().getName());
    return service.getFirstId();
  }


  @GetMapping(params = "q")
  public List<PostResponseDto> searchByContent(@RequestParam String q) {
    return service.searchByContent(q);
  }

  @PostMapping
  public PostResponseDto save(@RequestBody PostSaveRequestDto dto) {
    return service.save(dto);
  }

  @DeleteMapping("/{id}")
  public void removeById(@PathVariable int id) {
    service.removeById(id);
  }

  @PostMapping("/{id}/likes")
  public PostResponseDto likeById(@PathVariable int id) {
    return service.likeById(id);
  }

  @DeleteMapping("/{id}/likes")
  public PostResponseDto dislikeById(@PathVariable int id) {
    return service.dislikeById(id);
  }
}