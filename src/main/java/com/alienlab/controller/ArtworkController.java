package com.alienlab.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alienlab.db.ExecResult;
import com.alienlab.entity.Artwork;
import com.alienlab.service.ArtworkService;
import com.alienlab.service.PicService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
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
    @Autowired
    private ServletContext context;

    //添加artwork
    @RequestMapping(value = "/addArtwork", method = RequestMethod.POST)
    public String addArtwork(HttpServletRequest request) {
        String jsonBody = null;
        try {
            jsonBody = IOUtils.toString(request.getInputStream(), "UTF-8");
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
        artwork.setPrice(params.getFloatValue("price"));
        String picture = params.getString("picture");
        if (picture.equals("（待录入）")){
            String filename = picture;
            System.out.println(filename);
            artwork.setPicture(filename);
        }else {
            String path=context.getRealPath("/images");
            System.out.println(path);
            PicService service=new PicService();
            String filename=service.base64ToImage(picture,path);
            System.out.println(filename);
            artwork.setPicture(filename);
        }
        artwork.setStatus(params.getString("status"));
        try {
            Artwork result = artworkService.addArtwork(artwork);
            if (result == null) {
                return new ExecResult(false, "添加艺术品信息失败").toString();
            } else {
                ExecResult execResult = new ExecResult();
                execResult.setResult(true);
                execResult.setData((JSON) JSON.toJSON(result));
                return execResult.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ExecResult(false, "添加艺术品信息异常").toString();
        }
    }

    //获取所有艺术品
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public String getAllArtwork() {
        List<Artwork> artworks = artworkService.getAllArtwork();
        return JSON.toJSONString(artworks);
    }

    //分页获取艺术品
    @RequestMapping(value = "/getAllByPage/{index}-{size}", method = RequestMethod.GET)
    public Page<Artwork> getAllByPage(@PathVariable("index") String index, @PathVariable("size") String size) {
        Page<Artwork> artworks = artworkService.getAllByPage(Integer.parseInt(index), Integer.parseInt(size));
        return artworks;
    }

    //修改artwork
    @RequestMapping(value = "/updateArtwork", method = RequestMethod.POST)
    public String updateArtwork(HttpServletRequest request) {
        String jsonBody = null;
        try {
            jsonBody = IOUtils.toString(request.getInputStream(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject form = parseObject(jsonBody);
        JSONObject params = form.getJSONObject("params");
        System.out.println(params);
        Artwork artwork = artworkService.getArtworkById(params.getLong("id"));
        String oldPicture = artwork.getPicture();
        artwork.setName(params.getString("name"));
        artwork.setArtist(params.getString("artist"));
        artwork.setNumber(params.getInteger("number"));
        artwork.setPrice(params.getFloatValue("price"));
        String picture = params.getString("picture");
        if (picture.equals("（待录入）")||picture.equals(oldPicture)){
            String filename = picture;
            System.out.println(filename);
            artwork.setPicture(filename);
        }else {
            String path=context.getRealPath("/images");
            PicService service=new PicService();
            String filename=service.base64ToImage(picture,path);
            System.out.println(filename);
            artwork.setPicture(filename);
        }
        artwork.setStatus(params.getString("status"));
        try {
            Artwork result = artworkService.updateArtwork(artwork);
            if (result == null) {
                return new ExecResult(false, "修改艺术品信息失败").toString();
            } else {
                ExecResult execResult = new ExecResult();
                execResult.setResult(true);
                execResult.setData((JSON) JSON.toJSON(result));
                return execResult.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ExecResult(false, "修改艺术品信息异常").toString();
        }
    }

    //删除artwork
    @RequestMapping(value = "/deleteArtwork/{id}", method = RequestMethod.DELETE)
    public String deleteArtwork(@PathVariable("id") Long id) {
        System.out.println(id);
        try {
            if (artworkService.deleteArtwork(id)) {
                return new ExecResult(true, "艺术品删除成功").toString();
            } else {
                return new ExecResult(false, "艺术品删除失败").toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ExecResult(false, "艺术品删除异常").toString();
        }
    }

    //批量删除artwork
    @RequestMapping(value = "/batchDeleteArtwork/{ids}", method = RequestMethod.DELETE)
    public String batchDeleteArtwork(@PathVariable("ids") List<Long> ids) {
        System.out.println(ids);
        try {
            if (artworkService.batchDeleteArtwork(ids)) {
                return new ExecResult(true, "艺术品批量删除成功").toString();
            } else {
                return new ExecResult(false, "艺术品批量删除失败").toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ExecResult(false, "艺术品批量删除异常").toString();
        }
    }

    //根据name分页模糊查
    @RequestMapping(value = "/getArtworkByNameLikePage/{likeName}-{index}-{size}", method = RequestMethod.GET)
    public Page<Artwork> getArtworkByNameLikePage(@PathVariable("likeName") String likeName, @PathVariable("index") String index, @PathVariable("size") String size) {
        System.out.println(likeName+"-"+index+"-"+size);
        return artworkService.getArtworkByNameLikePage(likeName, Integer.parseInt(index), Integer.parseInt(size));
    }
}
