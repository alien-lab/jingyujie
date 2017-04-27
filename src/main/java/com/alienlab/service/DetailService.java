package com.alienlab.service;

import com.alienlab.Repository.DetailRepository;
import com.alienlab.entity.Artwork;
import com.alienlab.entity.Detail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 鸠小浅 on 2017/4/16.
 */
@Service
public class DetailService {
    @Autowired
    private DetailRepository detailRepository;

    //添加详情
    public Detail addDetail(Detail detail) {
        try{
            return detailRepository.save(detail);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //删除
    public boolean deleteDetail(Long id) {
        try {
            detailRepository.delete(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //修改
    public Detail updateDetail(Detail detail) {
        try{
            return detailRepository.save(detail);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //根据id查
    public Detail findDetailById(Long id) {
        try{
            return detailRepository.getOne(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //查所有
    public List<Detail> findAll() {
        try{
            return detailRepository.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //根据Artwork查
    public List<Detail> findDetailByArtwork(Artwork artwork) {
        try{
            return detailRepository.getDetailByArtwork(artwork);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
