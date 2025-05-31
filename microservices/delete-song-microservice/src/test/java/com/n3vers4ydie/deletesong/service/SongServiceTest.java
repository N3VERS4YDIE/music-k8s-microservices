package com.n3vers4ydie.deletesong.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.n3vers4ydie.deletesong.repository.SongRepository;

@ExtendWith(MockitoExtension.class)
class SongServiceTest {
    @Mock
    private SongRepository songRepository;
    @InjectMocks
    private SongService songService;

    @Test
    void deleteSong_WhenSongExists_ShouldReturnTrue() {
        Long id = 1L;
        when(songRepository.existsById(id)).thenReturn(true);
        doNothing().when(songRepository).deleteById(id);
        boolean result = songService.deleteSong(id);
        assertThat(result).isTrue();
        verify(songRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteSong_WhenSongNotExists_ShouldReturnFalse() {
        Long id = 1L;
        when(songRepository.existsById(id)).thenReturn(false);
        boolean result = songService.deleteSong(id);
        assertThat(result).isFalse();
        verify(songRepository, never()).deleteById(id);
    }
}
