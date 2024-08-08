package com.AxeFu.pastebox.api.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PasteboxUrlResponse {
    private final String url;
}
