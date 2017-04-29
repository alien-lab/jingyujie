package com.alienlab.controller;

/**
 * Created by 鸠小浅 on 2017/4/29.
 */

import com.alibaba.fastjson.JSON;
import com.alienlab.entity.Artwork;
import com.alienlab.entity.Detail;
import com.alienlab.service.ArtworkService;
import com.alienlab.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/detail")
public class DetailController {
    @Autowired
    private ArtworkService artworkService;
    @Autowired
    private DetailService detailService;
    @RequestMapping(value = "/getDetailByArtwork/{artworkId}",method = RequestMethod.GET)
    public String findDetailByArtwork(@PathVariable("artworkId") Long artworkId){
        Artwork artwork = artworkService.getArtworkById(artworkId);
        List<Detail> details = detailService.findDetailByArtwork(artwork);
        return JSON.toJSONString(details);
    }
}
