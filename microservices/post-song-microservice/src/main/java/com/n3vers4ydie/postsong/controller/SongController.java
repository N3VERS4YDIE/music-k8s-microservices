package com.n3vers4ydie.postsong.controller;

import com.n3vers4ydie.postsong.dto.SongDTO;
import com.n3vers4ydie.postsong.service.SongService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/songs")
public class SongController {
    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @PostMapping
    public ResponseEntity<SongDTO> createSong(@Valid @RequestBody SongDTO songDTO) {
        SongDTO createdSong = songService.createSong(songDTO);
        return new ResponseEntity<>(createdSong, HttpStatus.CREATED);
    }
}
