package com.mtnz.controller.app.community.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "community_comments")
public class CommunityComments {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "community_id")
  private Long communityId;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "content")
  private String content;

  @Column(name = "praise")
  private Integer praise;

  @Column(name = "creat_time")
  private Date creatTime;

  @Column(name = "is_delete")
  private Integer isDelete;

  @Transient
  private String commentsName;

  public String getCommentsName() {
    return commentsName;
  }

  public void setCommentsName(String commentsName) {
    this.commentsName = commentsName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCommunityId() {
    return communityId;
  }

  public void setCommunityId(Long communityId) {
    this.communityId = communityId;
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
