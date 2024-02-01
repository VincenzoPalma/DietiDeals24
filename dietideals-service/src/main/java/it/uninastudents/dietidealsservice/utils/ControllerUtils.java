package it.uninastudents.dietidealsservice.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ControllerUtils {

    public static Pageable pageableBuilder(int page, int size, Sort sort) {
        return PageRequest.of(page, size, sort);
    }
}
