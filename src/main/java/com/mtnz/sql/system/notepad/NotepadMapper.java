package com.mtnz.sql.system.notepad;


import com.mtnz.controller.app.notepad.model.Notepad;
import com.mtnz.controller.app.notepad.model.NotepadType;
import com.mtnz.controller.base.MyMapper;

public interface NotepadMapper extends MyMapper<Notepad> {

    void updateNoteType(NotepadType notepadType);
}