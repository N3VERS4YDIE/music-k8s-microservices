package com.n3vers4ydie.deletesong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.n3vers4ydie.deletesong.model.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
}
