package com.alienlab.Repository;

import com.alienlab.entity.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 鸠小浅 on 2017/4/16.
 */
@Repository
public interface ArtworkRepository extends JpaRepository<Artwork,Long>{
}
