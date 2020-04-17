package com.mtnz.service.system.notepad;


import com.github.pagehelper.PageInfo;
import com.mtnz.controller.app.notepad.model.Notepad;
import com.mtnz.controller.app.notepad.model.NotepadType;

import java.text.ParseException;
import java.util.List;

public interface NotepadService {
    /**
     * 添加记事本
     * @param notepad
     */
    void save(Notepad notepad);

    /**
     * 查询列表
     * @param notepad
     * @return
     */
    PageInfo<Notepad> selectlist(Notepad notepad) throws ParseException;

    /**
     * 查询详情
     * @param id
     * @return
     */
    Notepad selectone(Long id);

    /**
     * 查询分类列表
     * @param userId
     * @return
     */
    List<NotepadType> selecttype(Long userId);

    /**
     * 修改记事本
     * @param notepad
     * @return
     */
    Integer updateDetail(Notepad notepad);

    /**
     * 修改类型
     * @param notepadType
     * @return
     */
    Integer updateType(List<NotepadType> notepadType);

    /**
     * 查询类型列表
     * @param notepadType
     * @return
     */
    List<NotepadType> selecttypelist(NotepadType notepadType);

    List<Notepad> selectlistnopage(Notepad notepad) throws ParseException;
}
