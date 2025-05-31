package com.n3vers4ydie.deletesong.controller;

import com.n3vers4ydie.deletesong.model.Song;
import com.n3vers4ydie.deletesong.repository.SongRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class SongControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SongRepository songRepository;

    @Test
    void deleteSong_ShouldReturnNoContent() throws Exception {
        Song song = new Song(null, "Test Song", "Test Artist", LocalDate.now(), "Test Album", "Lyrics", List.of("url1"));
        song = songRepository.save(song);
        mockMvc.perform(delete("/api/songs/" + song.getId()))
                .andExpect(status().isNoContent());
        assertThat(songRepository.existsById(song.getId())).isFalse();
    }
}
