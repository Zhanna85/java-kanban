package ru.yandex.practicum.tasktracker.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import ru.yandex.practicum.tasktracker.model.Task;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {

    @Override
    public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
        if(!localDateTime.equals("null")) {
            jsonWriter.value(localDateTime.format(Task.FORMATTER));
        }
    }

    @Override
    public LocalDateTime read(JsonReader jsonReader) throws IOException {
        if(!jsonReader.equals("null")) {
            return LocalDateTime.parse(jsonReader.nextString());
        }
        return null;
    }
}
