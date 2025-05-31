package com.n3vers4ydie.putsong.service;

import com.n3vers4ydie.putsong.dto.SongDTO;
import com.n3vers4ydie.putsong.model.Song;
import com.n3vers4ydie.putsong.repository.SongRepository;
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
    void updateSong_WhenSongExists_ShouldReturnSongDTO() {
        Long id = 1L;
        SongDTO songDTO = new SongDTO();
        Song song = new Song();
        song.setId(id);
        when(songRepository.findById(id)).thenReturn(Optional.of(song));
        when(modelMapper.map(songDTO, song.getClass())).thenReturn(song);
        when(songRepository.save(song)).thenReturn(song);
        when(modelMapper.map(song, SongDTO.class)).thenReturn(songDTO);
        SongDTO result = songService.updateSong(id, songDTO);
        assertThat(result).isNotNull();
        verify(songRepository, times(1)).save(song);
    }
}
