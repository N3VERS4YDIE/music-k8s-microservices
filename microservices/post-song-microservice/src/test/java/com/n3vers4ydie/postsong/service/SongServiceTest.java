package com.n3vers4ydie.postsong.service;

import com.n3vers4ydie.postsong.dto.SongDTO;
import com.n3vers4ydie.postsong.model.Song;
import com.n3vers4ydie.postsong.repository.SongRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

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
    void createSong_ShouldReturnSongDTO() {
        SongDTO songDTO = new SongDTO();
        Song song = new Song();
        when(modelMapper.map(songDTO, Song.class)).thenReturn(song);
        when(songRepository.save(song)).thenReturn(song);
        when(modelMapper.map(song, SongDTO.class)).thenReturn(songDTO);
        SongDTO result = songService.createSong(songDTO);
        assertThat(result).isNotNull();
        verify(songRepository, times(1)).save(song);
    }
}
