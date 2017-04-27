package com.alienlab.service;

import com.alienlab.Repository.ExhibitionRepository;
import com.alienlab.entity.Exhibition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 鸠小浅 on 2017/4/16.
 */
@Service
public class ExhibitionService {
    @Autowired
    private ExhibitionRepository exhibitionRepository;

    //添加展览
    public Exhibition addExhibition(Exhibition exhibition) {
        return exhibitionRepository.save(exhibition);
    }

    //删除
    public boolean deleteExhibition(Long id) {
        try {
            exhibitionRepository.delete(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //修改
    public Exhibition updateExhibition(Exhibition exhibition) {
        return exhibitionRepository.save(exhibition);
    }

    //根据id查
    public Exhibition getExhibitionById(Long id) {
        return exhibitionRepository.getOne(id);
    }

    //查所有
    public List<Exhibition> findAll() {
        return exhibitionRepository.findAll();
    }

    //分页查
    public Page<Exhibition> getExhibitionByPage(Integer index, Integer size) {
        return exhibitionRepository.findAll(new PageRequest(index, size));
    }
}
