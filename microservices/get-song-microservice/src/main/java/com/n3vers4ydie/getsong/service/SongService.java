package com.n3vers4ydie.getsong.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.n3vers4ydie.getsong.dto.SongDTO;
import com.n3vers4ydie.getsong.model.Song;
import com.n3vers4ydie.getsong.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;
    private final ModelMapper modelMapper;

    public SongDTO getSongById(Long id) {
        Song song = songRepository.findById(id).orElse(null);
        return song != null ? modelMapper.map(song, SongDTO.class) : null;
    }

    public List<SongDTO> getAllSongs() {
        return songRepository.findAll().stream()
                .map(song -> modelMapper.map(song, SongDTO.class))
                .collect(Collectors.toList());
    }
}
