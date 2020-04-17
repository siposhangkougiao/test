package com.mtnz.controller.app.notepad.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "notepad")
public class Notepad {

  /**
   * 主键
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "type_id")
  private Long typeId;

  @Column(name = "type_name")
  private String typeName;

  @Column(name = "great_time")
  private Date greatTime;

  @Column(name = "note")
  private String note;

  @Column(name = "use_time")
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date useTime;

  @Column(name = "is_delete")
  private Integer isDelete;

  @Transient
  private NotepadType notepadType;

  @Transient
  private Integer pageNumber;

  @Transient
  private Integer pageSize;

  @Transient
  private String startTime;

  @Transient
  private String endTime;

  @Transient
  private String viewTimeOne;

  @Transient
  private String viewTimeTwo;

  public Integer getIsDelete() {
    return isDelete;
  }

  public void setIsDelete(Integer isDelete) {
    this.isDelete = isDelete;
  }

  public String getViewTimeOne() {
    return viewTimeOne;
  }

  public void setViewTimeOne(String viewTimeOne) {
    this.viewTimeOne = viewTimeOne;
  }

  public String getViewTimeTwo() {
    return viewTimeTwo;
  }

  public void setViewTimeTwo(String viewTimeTwo) {
    this.viewTimeTwo = viewTimeTwo;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
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

  public NotepadType getNotepadType() {
    return notepadType;
  }

  public void setNotepadType(NotepadType notepadType) {
    this.notepadType = notepadType;
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

  public Long getTypeId() {
    return typeId;
  }

  public void setTypeId(Long typeId) {
    this.typeId = typeId;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public Date getGreatTime() {
    return greatTime;
  }

  public void setGreatTime(Date greatTime) {
    this.greatTime = greatTime;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
  public Date getUseTime() {
    return useTime;
  }

  public void setUseTime(Date useTime) {
    this.useTime = useTime;
  }
}
