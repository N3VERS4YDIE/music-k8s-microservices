package com.n3vers4ydie.putsong.controller;

import com.n3vers4ydie.putsong.dto.SongDTO;
import com.n3vers4ydie.putsong.model.Song;
import com.n3vers4ydie.putsong.repository.SongRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class SongControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void updateSong_ShouldReturnOk() throws Exception {
        Song song = new Song(null, "Test Song", "Test Artist", LocalDate.now(), "Test Album", "Lyrics", List.of("url1"));
        song = songRepository.save(song);
        SongDTO songDTO = new SongDTO(song.getId(), "Updated Song", "Updated Artist", LocalDate.now(), "Updated Album", "Updated Lyrics", List.of("url2"));
        mockMvc.perform(put("/api/songs/" + song.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(songDTO)))
                .andExpect(status().isOk());
        assertThat(songRepository.findById(song.getId()).get().getName()).isEqualTo("Updated Song");
    }
}
