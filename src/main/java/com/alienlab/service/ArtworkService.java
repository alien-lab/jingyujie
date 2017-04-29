package com.alienlab.service;

import com.alienlab.Repository.ArtworkRepository;
import com.alienlab.Repository.DetailRepository;
import com.alienlab.entity.Artwork;
import com.alienlab.entity.Detail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;

/**
 * Created by 鸠小浅 on 2017/4/16.
 */
@Service
public class ArtworkService {
    @Autowired
    private ArtworkRepository artworkRepository;
    @Autowired
    private DetailRepository detailRepository;

    //添加艺术品
    public Artwork addArtwork(Artwork artwork) {
        try {
            return artworkRepository.save(artwork);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //删除艺术品
    public boolean deleteArtwork(Long id) {
        try {
            Artwork artwork = artworkRepository.getOne(id);
            List<Detail> details = detailRepository.getDetailByArtwork(artwork);
            for (Detail detail : details) {
                detailRepository.delete(detail);
            }
            artworkRepository.delete(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //批量删除艺术品
    public boolean batchDeleteArtwork(List<Long> ids) {
        try {
            for (Long id : ids) {
                Artwork artwork = artworkRepository.getOne(id);
                List<Detail> details = detailRepository.getDetailByArtwork(artwork);
                for (Detail detail : details) {
                    detailRepository.delete(detail);
                }
                artworkRepository.delete(id);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //修改
    public Artwork updateArtwork(Artwork artwork) {
        try {
            return artworkRepository.save(artwork);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //根据id查
    public Artwork getArtworkById(Long id) {
        try {
            return artworkRepository.getOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //根据name模糊查
    public Page<Artwork> getArtworkByNameLikePage(String likeName, Integer index, Integer size) {
        try {
            return artworkRepository.findByNameContaining("%"+likeName+"%", new PageRequest(index, size));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //查所有
    public List<Artwork> getAllArtwork() {
        try {
            return artworkRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //分页查
    public Page<Artwork> getAllByPage(Integer index, Integer size) {
        try {
            return artworkRepository.findAll(new PageRequest(index, size));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
