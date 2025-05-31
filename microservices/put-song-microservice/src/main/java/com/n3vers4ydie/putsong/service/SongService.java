package com.n3vers4ydie.putsong.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.n3vers4ydie.putsong.dto.SongDTO;
import com.n3vers4ydie.putsong.model.Song;
import com.n3vers4ydie.putsong.repository.SongRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;
    private final ModelMapper modelMapper;

    public SongDTO updateSong(Long id, SongDTO songDTO) {
        Song existingSong = songRepository.findById(id).orElse(null);
        if (existingSong == null) return null;
        modelMapper.map(songDTO, existingSong);
        existingSong.setId(id);
        Song updatedSong = songRepository.save(existingSong);
        return modelMapper.map(updatedSong, SongDTO.class);
    }
}
