package ru.rosbank.javaschool.crudapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rosbank.javaschool.crudapi.dto.PostResponseDto;
import ru.rosbank.javaschool.crudapi.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.crudapi.entity.PostEntity;
import ru.rosbank.javaschool.crudapi.exception.BadRequestException;
import ru.rosbank.javaschool.crudapi.repository.PostRepository;
import ru.rosbank.javaschool.crudapi.service.PostService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ServiceTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAll() {

        PostRepository repoMock=mock(PostRepository.class);
        PostEntity post=new PostEntity(1,"content",null,false,0);
        List<PostEntity> list=new ArrayList<>();
        list.add(post);
        when(repoMock.findAll()).thenReturn(list);

        PostResponseDto dto=new PostResponseDto(1,"content",null,0);
        List<PostResponseDto> listDto=new ArrayList<>();
        listDto.add(dto);

        PostService service=new PostService(repoMock);
        List<PostResponseDto> actual=service.getAll();
        assertIterableEquals(actual,listDto);
    }

    @Test
    void getSomePosts() {

        PostRepository repoMock=mock(PostRepository.class);
        PostEntity post=new PostEntity(1,"content",null,false,0);
        List<PostEntity> list=new ArrayList<>();
        list.add(post);
        when(repoMock.findAll()).thenReturn(list);

        PostResponseDto dto=new PostResponseDto(1,"content",null,0);
        List<PostResponseDto> listDto=new ArrayList<>();
        listDto.add(dto);

        PostService service=new PostService(repoMock);
        List<PostResponseDto> actual=service.getSomePosts(0,1);
        assertIterableEquals(actual,listDto);
    }

    @Test
    void getSomePostsNotFound() {

        PostRepository repoMock=mock(PostRepository.class);
        PostEntity post=new PostEntity(1,"content",null,false,0);
        List<PostEntity> list=new ArrayList<>();
        list.add(post);
        when(repoMock.findAll()).thenReturn(list);

        PostService service=new PostService(repoMock);
        List<PostResponseDto> actual=service.getSomePosts(5,10);
        assertEquals(Collections.emptyList(),actual);
    }

    @Test
    void save() {

        PostRepository repoMock=mock(PostRepository.class);

        PostSaveRequestDto dto=new PostSaveRequestDto(0,"content","");
        PostEntity post=PostEntity.from(dto);
        when(repoMock.save(post)).thenReturn(post);

        PostResponseDto expected=PostResponseDto.from(post);

        PostService service=new PostService(repoMock);
        PostResponseDto actual=service.save(dto);
        assertEquals(actual,expected);
    }

    @Test
    void searchByContent() {

        PostRepository repoMock=mock(PostRepository.class);
        PostEntity post=new PostEntity(1,"content",null,false,0);
        List<PostEntity> list=new ArrayList<>();
        list.add(post);
        when(repoMock.findAllByContentLike("content")).thenReturn(list);

        PostResponseDto dto=new PostResponseDto(1,"content",null,0);
        List<PostResponseDto> listDto=new ArrayList<>();
        listDto.add(dto);

        PostService service=new PostService(repoMock);
        List<PostResponseDto> actual=service.searchByContent("content");
        assertIterableEquals(actual,listDto);
    }

    @Test
    void likeById() {

        PostRepository repoMock=mock(PostRepository.class);

        PostEntity post=new PostEntity(1,"content",null,false,0);

        when(repoMock.findById(1)).thenReturn(Optional.of(post));

        PostResponseDto expected= new PostResponseDto(1,"content",null,1);

        PostService service=new PostService(repoMock);
        PostResponseDto actual=service.likeById(1);
        assertEquals(actual,expected);
    }

    @Test
    void likeByIdThrowExcept() {

        PostRepository repoMock=mock(PostRepository.class);

        PostEntity post=new PostEntity(1,"content",null,false,0);

        when(repoMock.findById(1)).thenReturn(Optional.empty());

        PostService service=new PostService(repoMock);
        assertThrows(BadRequestException.class,()->service.likeById(1));
    }

    @Test
    void dislikeByIdZeroLikes() {

        PostRepository repoMock=mock(PostRepository.class);

        PostEntity post=new PostEntity(1,"content",null,false,0);

        when(repoMock.findById(1)).thenReturn(Optional.of(post));

        PostResponseDto expected= new PostResponseDto(1,"content",null,0);

        PostService service=new PostService(repoMock);
        PostResponseDto actual=service.dislikeById(1);
        assertEquals(actual,expected);
    }

    @Test
    void dislikeById() {

        PostRepository repoMock=mock(PostRepository.class);

        PostEntity post=new PostEntity(1,"content",null,false,1);

        when(repoMock.findById(1)).thenReturn(Optional.of(post));

        PostResponseDto expected= new PostResponseDto(1,"content",null,0);

        PostService service=new PostService(repoMock);
        PostResponseDto actual=service.dislikeById(1);
        assertEquals(actual,expected);
    }

    @Test
    void dislikeByIdThrowExcept() {

        PostRepository repoMock=mock(PostRepository.class);

        PostEntity post=new PostEntity(1,"content",null,false,0);

        when(repoMock.findById(1)).thenReturn(Optional.empty());

        PostService service=new PostService(repoMock);
        assertThrows(BadRequestException.class,()->service.dislikeById(1));
    }

    @Test
    void getCountOfNewPosts() {

        PostRepository repoMock=mock(PostRepository.class);
        PostEntity post1=new PostEntity(1,"content1",null,false,0);
        PostEntity post2=new PostEntity(2,"content2",null,false,0);
        List<PostEntity> list=new ArrayList<>();
        list.add(post2);
        list.add(post1);
        when(repoMock.findById(1)).thenReturn(Optional.of(post1));
        when(repoMock.findAll()).thenReturn(list);

        PostService service=new PostService(repoMock);
        int actual=service.getCountOfNewPosts(1);
        assertEquals(1,actual);

    }

    @Test
    void getFirstId() {
        PostRepository repoMock=mock(PostRepository.class);
        PostEntity post=new PostEntity(1,"content",null,false,0);
        List<PostEntity> list=new ArrayList<>();
        list.add(post);

        when(repoMock.findAll()).thenReturn(list);

        PostService service=new PostService(repoMock);
        int actual=service.getFirstId();
        assertEquals(1,actual);
    }
}
