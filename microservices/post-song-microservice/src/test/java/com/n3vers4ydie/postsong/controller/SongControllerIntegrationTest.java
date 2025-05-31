package com.n3vers4ydie.postsong.controller;

import com.n3vers4ydie.postsong.dto.SongDTO;
import com.n3vers4ydie.postsong.model.Song;
import com.n3vers4ydie.postsong.repository.SongRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void createSong_ShouldReturnCreated() throws Exception {
        SongDTO songDTO = new SongDTO(null, "Test Song", "Test Artist", LocalDate.now(), "Test Album", "Lyrics", List.of("url1"));
        mockMvc.perform(post("/api/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(songDTO)))
                .andExpect(status().isCreated());
        assertThat(songRepository.findAll()).isNotEmpty();
    }
}
