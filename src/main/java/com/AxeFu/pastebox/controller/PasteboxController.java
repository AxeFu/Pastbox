package com.AxeFu.pastebox.controller;

import com.AxeFu.pastebox.api.request.PasteboxRequest;
import com.AxeFu.pastebox.api.response.PasteboxResponse;
import com.AxeFu.pastebox.api.response.PasteboxUrlResponse;
import com.AxeFu.pastebox.service.PasteboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PasteboxController {
    private final PasteboxService service;

    @GetMapping("/")
    public List<PasteboxResponse> getPublicBaseList() {
        return service.getFirstPublicPasteboxes();
    }

    @GetMapping("/{hash}")
    public PasteboxResponse getByHash(@PathVariable String hash) {
        return service.getByHash(hash);
    }

    @PostMapping
    public PasteboxUrlResponse add(@RequestBody PasteboxRequest request) {
        return service.create(request);
    }

}
