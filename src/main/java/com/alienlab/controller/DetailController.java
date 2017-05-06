package com.alienlab.controller;

/**
 * Created by 鸠小浅 on 2017/4/29.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alienlab.db.ExecResult;
import com.alienlab.entity.Artwork;
import com.alienlab.entity.Detail;
import com.alienlab.service.ArtworkService;
import com.alienlab.service.DetailService;
import com.alienlab.service.PicService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/detail")
public class DetailController {
    @Autowired
    private ArtworkService artworkService;
    @Autowired
    private DetailService detailService;
    @Autowired
    private ServletContext context;

    @RequestMapping(value = "/getDetailByArtwork/{artworkId}",method = RequestMethod.GET)
    public String findDetailByArtwork(@PathVariable("artworkId") Long artworkId){
        Artwork artwork = artworkService.getArtworkById(artworkId);
        List<Detail> details = detailService.findDetailByArtwork(artwork);
        return JSON.toJSONString(details);
    }
    //添加Detail
    @RequestMapping(value = "/addArtworkDetail", method = RequestMethod.POST)
    public String addArtworkDetail(HttpServletRequest request) {
        String jsonBody = null;
        try {
            jsonBody = IOUtils.toString(request.getInputStream(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject form = parseObject(jsonBody);
        JSONObject params = form.getJSONObject("params");
        System.out.println(params);
        Detail detail = new Detail();
        detail.setLabel(params.getString("label"));
        detail.setLink(params.getString("link"));
        detail.setDate(Timestamp.valueOf(params.getString("date")));
        detail.setStatus(params.getString("status"));
        detail.setType(params.getString("type"));
        Long artworkId = params.getLong("artworkId");
        Artwork artwork = artworkService.getArtworkById(artworkId);
        detail.setArtwork(artwork);
        if (detail.getType().equals("图片")){
            String picture = params.getString("content");
            String path=context.getRealPath("/images");
            System.out.println(path);
            PicService service=new PicService();
            String filename=service.base64ToImage(picture,path);
            System.out.println(filename);
            detail.setContent(filename);
        }else {
            String text = params.getString("content");
            detail.setContent(text);
        }
        try {
            Detail result = detailService.addDetail(detail);
            if (result == null) {
                return new ExecResult(false, "添加艺术品详情失败").toString();
            } else {
                ExecResult execResult = new ExecResult();
                execResult.setResult(true);
                execResult.setData((JSON) JSON.toJSON(result));
                return execResult.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ExecResult(false, "添加艺术品详情异常").toString();
        }
    }
    //删除detail
    @RequestMapping(value = "/deleteDetail/{id}", method = RequestMethod.DELETE)
    public String deleteDetail(@PathVariable("id") Long id) {
        System.out.println(id);
        try {
            if (detailService.deleteDetail(id)) {
                return new ExecResult(true, "详情删除成功").toString();
            } else {
                return new ExecResult(false, "详情删除失败").toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ExecResult(false, "详情删除异常").toString();
        }
    }
    //修改detail发布状态
    @RequestMapping(value = "/updateDetail", method = RequestMethod.POST)
    public String updateDetail(HttpServletRequest request) {
        String jsonBody = null;
        try {
            jsonBody = IOUtils.toString(request.getInputStream(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject form = parseObject(jsonBody);
        JSONObject params = form.getJSONObject("params");
        System.out.println(params);
        Detail detail = detailService.findDetailById(params.getLong("id"));
        detail.setStatus(params.getString("status"));
        try {
            Detail result = detailService.updateDetail(detail);
            if (result == null) {
                return new ExecResult(false, "修改详情失败").toString();
            } else {
                ExecResult execResult = new ExecResult();
                execResult.setResult(true);
                execResult.setData((JSON) JSON.toJSON(result));
                return execResult.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ExecResult(false, "修改详情异常").toString();
        }
    }
}
