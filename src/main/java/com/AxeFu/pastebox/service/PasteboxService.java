package com.AxeFu.pastebox.service;

import com.AxeFu.pastebox.api.request.PasteboxRequest;
import com.AxeFu.pastebox.api.response.PasteboxResponse;
import com.AxeFu.pastebox.api.response.PasteboxUrlResponse;

import java.util.List;

public interface PasteboxService {
    PasteboxResponse getByHash(String hash);
    List<PasteboxResponse> getFirstPublicPasteboxes();
    PasteboxUrlResponse create(PasteboxRequest request);
}
