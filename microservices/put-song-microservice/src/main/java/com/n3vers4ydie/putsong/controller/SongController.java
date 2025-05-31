package com.n3vers4ydie.putsong.controller;

import com.n3vers4ydie.putsong.dto.SongDTO;
import com.n3vers4ydie.putsong.service.SongService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/songs")
public class SongController {
    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<SongDTO> updateSong(@PathVariable Long id, @Valid @RequestBody SongDTO songDTO) {
        SongDTO updatedSong = songService.updateSong(id, songDTO);
        if (updatedSong == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updatedSong);
    }
}
