package com.mtnz.controller.app.community.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "community")
public class Community {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "content")
  private String content;

  @Column(name = "imges")
  private String imges;

  @Column(name = "praise")
  private Integer praise;

  @Column(name = "creat_time")
  private Date creatTime;

  @Column(name = "is_delete")
  private Integer isDelete;

  @Transient
  private Integer pageNumber=1;

  @Transient
  private Integer pageSize=15;

  /**
   * 发布人名称
   */
  @Transient
  private String releaseName;

  @Transient
  private List<CommunityComments> commentsList = new ArrayList<>();

  public String getReleaseName() {
    return releaseName;
  }

  public void setReleaseName(String releaseName) {
    this.releaseName = releaseName;
  }

  public List<CommunityComments> getCommentsList() {
    return commentsList;
  }

  public void setCommentsList(List<CommunityComments> commentsList) {
    this.commentsList = commentsList;
  }

  public Integer getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(Integer pageNumber) {
    this.pageNumber = pageNumber;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getImges() {
    return imges;
  }

  public void setImges(String imges) {
    this.imges = imges;
  }

  public Integer getPraise() {
    return praise;
  }

  public void setPraise(Integer praise) {
    this.praise = praise;
  }

  public Date getCreatTime() {
    return creatTime;
  }

  public void setCreatTime(Date creatTime) {
    this.creatTime = creatTime;
  }

  public Integer getIsDelete() {
    return isDelete;
  }

  public void setIsDelete(Integer isDelete) {
    this.isDelete = isDelete;
  }
}
