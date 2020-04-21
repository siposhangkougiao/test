package com.mtnz.service.system.notepad.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mtnz.controller.app.notepad.model.Notepad;
import com.mtnz.controller.app.notepad.model.NotepadType;
import com.mtnz.service.system.notepad.NotepadService;
import com.mtnz.sql.system.notepad.NotepadMapper;
import com.mtnz.sql.system.notepad.NotepadTypeMapper;
import com.mtnz.util.MyTimesUtil;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NotepadServiceImpl implements NotepadService {
    @Resource
    private NotepadMapper notepadMapper;

    @Resource
    private NotepadTypeMapper notepadTypeMapper;

    /**
     * 添加记事本
     * @param notepad
     */
    @Override
    public void save(Notepad notepad) {
        NotepadType notepadType = new NotepadType();
        notepadType.setIsDelete(0);
        notepadType.setName(notepad.getNotepadType().getName());
        notepadType.setUserId(notepad.getUserId());
        Integer count = notepadTypeMapper.selectCount(notepadType);
        if(count<1){
            notepadTypeMapper.insertSelective(notepad.getNotepadType());
            notepad.setTypeId(notepad.getNotepadType().getId());
        }else {
            NotepadType type = notepadTypeMapper.selectOne(notepadType);
            notepad.setTypeId(type.getId());
        }
        notepadMapper.insertSelective(notepad);
    }

    /**
     * 查询列表(有分页)
     * @param notepad
     * @return
     */
    @Override
    public PageInfo<Notepad> selectlist(Notepad notepad) throws ParseException {
        PageHelper.startPage(notepad.getPageNumber(),notepad.getPageSize());
        Example example = new Example(Notepad.class);
        example.and().andEqualTo("userId",notepad.getUserId());
        example.and().andEqualTo("isDelete",0);
        if(notepad.getTypeName()!=null){
            example.and().andEqualTo("typeName",notepad.getTypeName());
        }
        if(notepad.getStartTime()!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Date start = sdf.parse(notepad.getStartTime());
            Date end = sdf.parse(notepad.getEndTime());
            example.and().andBetween("useTime",start,end);
        }
        if(notepad.getUseTime()!=null){
            Date start = MyTimesUtil.getDayStartTime(notepad.getUseTime());
            Date end = MyTimesUtil.getDayEndTime(notepad.getUseTime());
            example.and().andBetween("useTime",start,end);
        }
        example.orderBy("id").desc();
        List<Notepad> list = notepadMapper.selectByExample(example);
        SimpleDateFormat sdf1 = new SimpleDateFormat("d日");
        SimpleDateFormat sdf2 = new SimpleDateFormat("M月d日");
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat sdf4 = new SimpleDateFormat("HH:mm");
        for (int i = 0; i <list.size() ; i++) {
            if(MyTimesUtil.isThisYear(list.get(i).getUseTime())){
                if(MyTimesUtil.isThisMonth(list.get(i).getUseTime())){
                    list.get(i).setViewTimeOne(sdf1.format(list.get(i).getUseTime()));
                }else {
                    list.get(i).setViewTimeOne(sdf2.format(list.get(i).getUseTime()));
                }
            }else {
                list.get(i).setViewTimeOne(sdf3.format(list.get(i).getUseTime()));
            }
            list.get(i).setViewTimeTwo(sdf4.format(list.get(i).getUseTime()));
        }
        PageInfo<Notepad> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    /**
     * 插叙详情
     * @param id
     * @return
     */
    @Override
    public Notepad selectone(Long id) {
        Notepad notepad = notepadMapper.selectByPrimaryKey(id);
        return notepad;
    }

    /**
     * 查询分类列表
     * @param userId
     * @return
     */
    @Override
    public List<NotepadType> selecttype(Long userId) {
        NotepadType notepadType = new NotepadType();
        notepadType.setUserId(userId);
        notepadType.setIsDelete(0);
        List<NotepadType> list = notepadTypeMapper.select(notepadType);
        return list;
    }

    /**
     * 修改记事本
     * @param notepad
     * @return
     */
    @Override
    public Integer updateDetail(Notepad notepad) {
        if(notepad.getIsDelete()==null){//删除
            Notepad bean= notepadMapper.selectByPrimaryKey(notepad.getId());
            NotepadType notepadType = new NotepadType();
            notepadType.setIsDelete(0);
            notepadType.setName(notepad.getTypeName());
            notepadType.setUserId(bean.getUserId());
            Integer count = notepadTypeMapper.selectCount(notepadType);
            if(count<1){
                notepadTypeMapper.insertSelective(notepad.getNotepadType());
                notepad.setTypeId(notepad.getNotepadType().getId());
            }else {
                NotepadType type = notepadTypeMapper.selectOne(notepadType);
                notepad.setTypeId(type.getId());
            }
        }
        return notepadMapper.updateByPrimaryKeySelective(notepad);
    }

    /**
     * 修改类型
     * @param notepadTypes
     * @return
     */
    @Override
    public Integer updateType(List<NotepadType> notepadTypes) {
        for (int i = 0; i <notepadTypes.size() ; i++) {
            NotepadType notepadType = notepadTypes.get(i);
            if(notepadType.getName()!=null){
                Notepad notepad = new Notepad();
                notepad.setTypeName(notepadType.getName());
                Example example = new Example(Notepad.class);
                example.and().andEqualTo("typeId",notepadType.getId());
                notepadMapper.updateByExampleSelective(notepad,example);
            }
            notepadTypeMapper.updateByPrimaryKeySelective(notepadType);
            if(notepadTypes.get(i).getIsDelete()==1){
                notepadMapper.updateNoteType(notepadType);
            }
        }

        return 1;
    }

    /**
     * 查询类型列表
     * @param notepadType
     * @return
     */
    @Override
    public List<NotepadType> selecttypelist(NotepadType notepadType) {

        return notepadTypeMapper.select(notepadType);
    }

    @Override
    public List<Notepad> selectlistnopage(Notepad notepad) throws ParseException {
        Example example = new Example(Notepad.class);
        example.and().andEqualTo("userId",notepad.getUserId());
        example.and().andEqualTo("isDelete",0);
        if(notepad.getTypeName()!=null){
            example.and().andEqualTo("typeName",notepad.getTypeName());
        }
        if(notepad.getStartTime()!=null){
            String [] startTime = notepad.getStartTime().split("-");
            Date start = MyTimesUtil.getBeginTime(Integer.valueOf(startTime[0]),Integer.valueOf(startTime[1]));
            Date end = MyTimesUtil.getEndTime(Integer.valueOf(startTime[0]),Integer.valueOf(startTime[1]));
            example.and().andBetween("useTime",start,end);
        }
        if(notepad.getUseTime()!=null){
            Date start = MyTimesUtil.getDayStartTime(notepad.getUseTime());
            Date end = MyTimesUtil.getDayEndTime(notepad.getUseTime());
            example.and().andBetween("useTime",start,end);
        }
        example.orderBy("id").desc();
        List<Notepad> list = notepadMapper.selectByExample(example);
        return list;
    }
}
