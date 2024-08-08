package com.AxeFu.pastebox.repository;

import com.AxeFu.pastebox.model.PasteboxEntity;

import java.util.List;

public interface PasteboxRepository {
    PasteboxEntity getByHash(String hash);
    List<PasteboxEntity> getListOfPublicAndAlive(int amount);
    void add(PasteboxEntity pastboxEntity);
}
