package com.alienlab.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by 鸠小浅 on 2017/4/15.
 */
@Entity
@Table(name = "tb_detail")
public class Detail {
    private Long id;
    private String label;
    private String content;//内容
    private String type;//（图片、文字、视频）
    private String link;
    private Timestamp date;//上传日期
    private Artwork artwork;//所属艺术品
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Basic
    @Column(name = "label")
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @Basic
    @Column(name = "link")
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
    @ManyToOne
    @JoinColumn(name = "artwork")
    public Artwork getArtwork() {
        return artwork;
    }

    public void setArtwork(Artwork artwork) {
        this.artwork = artwork;
    }
}
