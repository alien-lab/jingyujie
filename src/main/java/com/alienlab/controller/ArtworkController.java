package com.alienlab.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alienlab.db.ExecResult;
import com.alienlab.entity.Artwork;
import com.alienlab.service.ArtworkService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * Created by 鸠小浅 on 2017/4/25.
 */
@RestController
@RequestMapping("/artwork")
public class ArtworkController {
    @Autowired
    private ArtworkService artworkService;
    @RequestMapping(value = "/addArtwork",method = RequestMethod.POST)
    public String addArtwork(HttpServletRequest request){
        String jsonBody = null;
        try {
            jsonBody = IOUtils.toString(request.getInputStream(),"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject form = parseObject(jsonBody);
        JSONObject params = form.getJSONObject("params");
        System.out.println(params);
        Artwork artwork = new Artwork();
        artwork.setName(params.getString("name"));
        artwork.setArtist(params.getString("artist"));
        artwork.setNumber(params.getInteger("number"));
        artwork.setDate(Timestamp.valueOf(params.getString("date")));
        try{
            Artwork result = artworkService.addArtwork(artwork);
            if (result == null){
                return new ExecResult(false,"添加艺术品信息失败").toString();
            }else {
                ExecResult execResult = new ExecResult();
                execResult.setResult(true);
                execResult.setData((JSON) JSON.toJSON(result));
                return execResult.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ExecResult(false,"添加艺术品信息异常").toString();
        }
    }
    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public String getAllArtwork(){
        List<Artwork> artworks = artworkService.getAllArtwork();
        return JSON.toJSONString(artworks);
    }
    @RequestMapping(value = "/getAllByPage/{index}-{size}",method = RequestMethod.GET)
    public Page<Artwork> getAllByPage(@PathVariable("index") String index, @PathVariable("size") String size){
        System.out.println(index+size);
        Page<Artwork> artworks = artworkService.getAllByPage(Integer.parseInt(index),Integer.parseInt(size));
        return artworks;
    }
}
