package com.n3vers4ydie.putsong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.n3vers4ydie.putsong.model.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
}
