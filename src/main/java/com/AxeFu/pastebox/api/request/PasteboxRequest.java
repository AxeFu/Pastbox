package com.AxeFu.pastebox.api.request;

import lombok.Data;

@Data
public class PasteboxRequest {
    private String data;
    private long ttl;
    private PublicStatus publicStatus;
}
