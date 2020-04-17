package com.mtnz.controller.app.notepad.model;


import javax.persistence.*;
import java.util.Date;

@Table(name = "notepad_type")
public class NotepadType {

  /**
   * 主键
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "name")
  private String name;

  @Column(name = "great_time")
  private Date greatTime;

  @Column(name = "is_delete")
  private Integer isDelete;


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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getGreatTime() {
    return greatTime;
  }

  public void setGreatTime(Date greatTime) {
    this.greatTime = greatTime;
  }

  public Integer getIsDelete() {
    return isDelete;
  }

  public void setIsDelete(Integer isDelete) {
    this.isDelete = isDelete;
  }
}
