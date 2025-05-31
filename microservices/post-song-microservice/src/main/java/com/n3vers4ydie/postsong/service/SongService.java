package com.n3vers4ydie.postsong.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.n3vers4ydie.postsong.dto.SongDTO;
import com.n3vers4ydie.postsong.model.Song;
import com.n3vers4ydie.postsong.repository.SongRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;
    private final ModelMapper modelMapper;

    public SongDTO createSong(SongDTO songDTO) {
        Song song = modelMapper.map(songDTO, Song.class);
        Song savedSong = songRepository.save(song);
        return modelMapper.map(savedSong, SongDTO.class);
    }
}
