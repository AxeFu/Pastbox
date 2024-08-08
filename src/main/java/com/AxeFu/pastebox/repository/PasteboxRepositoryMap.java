package com.AxeFu.pastebox.repository;

import com.AxeFu.pastebox.exception.NotFoundEntityException;
import com.AxeFu.pastebox.model.PasteboxEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class PasteboxRepositoryMap implements PasteboxRepository {

    private final Map<String, PasteboxEntity> vault = new ConcurrentHashMap<>();
    private AtomicInteger idGenerator = new AtomicInteger(0);

    @Override
    public PasteboxEntity getByHash(String hash) {
        PasteboxEntity pastboxEntity = vault.get(hash);
        if (pastboxEntity == null) {
            throw new NotFoundEntityException("Pastebox not found with hash=" + hash);
        }
        return pastboxEntity;
    }

    @Override
    public List<PasteboxEntity> getListOfPublicAndAlive(int amount) {
        LocalDateTime now = LocalDateTime.now();

        return vault.values().stream()
                .filter(PasteboxEntity::isPublic)
                .filter(pastboxEntity -> pastboxEntity.getTtl().isAfter(now))
                .sorted(Comparator.comparing(PasteboxEntity::getId).reversed())
                .limit(amount)
                .collect(Collectors.toList());
    }

    @Override
    public void add(PasteboxEntity pastboxEntity) {
        pastboxEntity.setId(idGenerator.getAndIncrement());
        vault.put(pastboxEntity.getHash(), pastboxEntity);
    }
}
