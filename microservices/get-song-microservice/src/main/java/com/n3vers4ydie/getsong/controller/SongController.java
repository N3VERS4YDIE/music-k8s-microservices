package com.n3vers4ydie.getsong.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n3vers4ydie.getsong.dto.SongDTO;
import com.n3vers4ydie.getsong.service.SongService;

@RestController
@RequestMapping("/api/songs/get")
public class SongController {
    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongDTO> getSongById(@PathVariable Long id) {
        SongDTO songDTO = songService.getSongById(id);
        if (songDTO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(songDTO);
    }

    @GetMapping
    public ResponseEntity<List<SongDTO>> getAllSongs() {
        List<SongDTO> songs = songService.getAllSongs();
        return ResponseEntity.ok(songs);
    }
}
