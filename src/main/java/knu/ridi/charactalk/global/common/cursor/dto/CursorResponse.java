package knu.ridi.charactalk.global.common.cursor.dto;

import java.util.List;

public record CursorResponse<T>(
    List<T> content,
    boolean hasNext
) {

    public static <T> CursorResponse<T> of(List<T> items, int size) {
        boolean hasNext = items.size() > size;
        return new CursorResponse<>(
            hasNext ? items.subList(0, size) : items,
            hasNext
        );
    }
}
