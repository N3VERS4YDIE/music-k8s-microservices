package com.n3vers4ydie.getsong.service;

import com.n3vers4ydie.getsong.dto.SongDTO;
import com.n3vers4ydie.getsong.model.Song;
import com.n3vers4ydie.getsong.repository.SongRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class SongServiceTest {
    @Mock
    private SongRepository songRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private SongService songService;

    @Test
    void getSongById_WhenSongExists_ShouldReturnSongDTO() {
        Long id = 1L;
        Song song = new Song();
        song.setId(id);
        when(songRepository.findById(id)).thenReturn(Optional.of(song));
        when(modelMapper.map(song, SongDTO.class)).thenReturn(new SongDTO());
        SongDTO result = songService.getSongById(id);
        assertThat(result).isNotNull();
        verify(songRepository, times(1)).findById(id);
    }
}
