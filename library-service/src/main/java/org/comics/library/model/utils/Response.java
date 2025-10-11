package org.comics.library.model.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record Response<Object> (Object obj, String msg) {}

