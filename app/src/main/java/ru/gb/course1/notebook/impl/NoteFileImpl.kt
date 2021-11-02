package ru.gb.course1.notebook.impl

import android.content.Context
import ru.gb.course1.notebook.domain.NoteFileRepo
import com.fasterxml.jackson.databind.ObjectMapper
import org.json.JSONObject
import org.json.JSONException
import ru.gb.course1.notebook.domain.Note
import java.io.*
import java.util.ArrayList

class NoteFileImpl : NoteFileRepo {
    override fun saveNoteToFile(context: Context, note: Note) {
        val om = ObjectMapper()
        try {
            BufferedWriter(OutputStreamWriter(
                    context.openFileOutput(String.format("%s.txt", note.idNote), Context.MODE_PRIVATE))).use { bw -> bw.write(om.writeValueAsString(note)) }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun updateNoteInFile(context: Context, note: Note) {
        saveNoteToFile(context, note)
    }

    override fun deleteNoteFile(context: Context, idNote: Int) {
        val filesNotes = context.fileList()
        for (fileNote in filesNotes) {
            if (fileNote == String.format("%s.txt", idNote)) {
                context.deleteFile(fileNote)
            }
        }
    }

    override fun getNotesFromFiles(context: Context): List<Note> {
        val notes: MutableList<Note> = ArrayList()
        val filesNotes = context.fileList()
        for (fileName in filesNotes) {
            try {
                BufferedReader(InputStreamReader(
                        context.openFileInput(fileName))).use { br ->
                    var str: String?
                    while (br.readLine().also { str = it } != null) {
                        val jsonRequest = JSONObject(str)
                        val note = Note(jsonRequest.getInt("idNote"), jsonRequest.getString("title"),
                                jsonRequest.getString("description"), jsonRequest.getString("timeCreate"))
                        notes.add(note)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return notes
    }
}