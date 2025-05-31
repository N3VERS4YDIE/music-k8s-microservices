package com.n3vers4ydie.getsong.controller;

import com.n3vers4ydie.getsong.dto.SongDTO;
import com.n3vers4ydie.getsong.model.Song;
import com.n3vers4ydie.getsong.repository.SongRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void getAllSongs_ShouldReturnOk() throws Exception {
        Song song = new Song(null, "Test Song", "Test Artist", LocalDate.now(), "Test Album", "Lyrics", List.of("url1"));
        songRepository.save(song);
        mockMvc.perform(get("/api/songs"))
                .andExpect(status().isOk());
        assertThat(songRepository.findAll()).isNotEmpty();
    }
}
