package com.n3vers4ydie.deletesong.service;

import org.springframework.stereotype.Service;
import com.n3vers4ydie.deletesong.repository.SongRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;

    public boolean deleteSong(Long id) {
        if (!songRepository.existsById(id)) {
            return false;
        }
        songRepository.deleteById(id);
        return true;
    }
}
