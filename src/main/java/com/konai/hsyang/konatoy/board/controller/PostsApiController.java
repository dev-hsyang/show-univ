package com.konai.hsyang.konatoy.board.controller;

import com.konai.hsyang.konatoy.board.dto.PostsResponseDto;
import com.konai.hsyang.konatoy.board.dto.PostsSaveRequestDto;
import com.konai.hsyang.konatoy.board.dto.PostsUpdateRequestDto;
import com.konai.hsyang.konatoy.board.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    // C
    @PostMapping("/api/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    // R
    @GetMapping("/api/posts/{id}")
    public PostsResponseDto findById (@PathVariable Long id){
        return postsService.findById(id);
    }

    // U
    @PutMapping("/api/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    // D
    @DeleteMapping("/api/posts/{id}")
    public Long delete(@PathVariable Long id){
        postsService.delete(id);
        return id;
    }

}
