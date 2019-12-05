package ru.rosbank.javaschool.crudapi.rest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.rosbank.javaschool.crudapi.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.crudapi.dto.PostResponseDto;
import ru.rosbank.javaschool.crudapi.service.PostService;

import java.util.List;

@RestController // ко всем методам будет дописано @ResponseBody
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class RestPostController {
  private final PostService service;
  private final Logger logger = LoggerFactory.getLogger(RestPostController.class);

  // @ResponseBody
  // AutoConfiguration -> Jackson
  // HttpMessageConverters -> RequestResponse...
  @GetMapping // @RequestMapping(method = GET) -> GET /api/posts
  public List<PostResponseDto> getAll() {
    logger.info(Thread.currentThread().getName());
    return service.getAll();
  }

  // ТТП
  @GetMapping(params = "q") // фильтрация по наличию параметра
  public List<PostResponseDto> searchByContent(@RequestParam String q) {
    return service.searchByContent(q);
  }

  // -> x-www-urlencoded...
  // -> multipart/form-data
  // Content-Type: MIME тип
  // POST -> create/update
  @PostMapping // DataBinding
  public PostResponseDto save(@RequestBody PostSaveRequestDto dto) {
    return service.save(dto);
  }

  // DELETE /api/posts/:id -> ?itemId=10 -> req.getParameter()
  @DeleteMapping("/{id}")
// public void removeById(@PathVariable("id") int id)
// if param name = path variable name, то дополнительно ничего не нужно
  public void removeById(@PathVariable int id) {
//    throw new BadRequestException("bad.request");
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
