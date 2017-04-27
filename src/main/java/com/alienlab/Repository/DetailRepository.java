package com.alienlab.Repository;

import com.alienlab.entity.Artwork;
import com.alienlab.entity.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 鸠小浅 on 2017/4/16.
 */
@Repository
public interface DetailRepository extends JpaRepository<Detail,Long>{
    public List<Detail> getDetailByArtwork(Artwork artwork);
}
