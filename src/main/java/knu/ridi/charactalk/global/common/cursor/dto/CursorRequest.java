package knu.ridi.charactalk.global.common.cursor.dto;

public record CursorRequest(Long cursor, int size) {
    public static CursorRequest of(Long cursor, int size) {
        return new CursorRequest(cursor, size);
    }

    public  boolean hasCursor() {
        return cursor != null;
    }
}
