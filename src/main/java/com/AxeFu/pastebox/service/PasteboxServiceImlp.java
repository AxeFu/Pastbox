package com.AxeFu.pastebox.service;

import com.AxeFu.pastebox.api.request.PasteboxRequest;
import com.AxeFu.pastebox.api.request.PublicStatus;
import com.AxeFu.pastebox.api.response.PasteboxResponse;
import com.AxeFu.pastebox.api.response.PasteboxUrlResponse;
import com.AxeFu.pastebox.model.PasteboxEntity;
import com.AxeFu.pastebox.repository.PasteboxRepository;
import com.AxeFu.pastebox.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PasteboxServiceImlp implements PasteboxService {

    @Value("spring.application.name")
    private String host;
    @Value("${app.public.list.size}")
    private int publicListSize;

    private final PasteboxRepository repository;

    @Override
    public PasteboxResponse getByHash(String hash) {
        PasteboxEntity pasteboxEntity = repository.getByHash(hash);
        return new PasteboxResponse(pasteboxEntity.getData(), pasteboxEntity.isPublic());
    }

    @Override
    public List<PasteboxResponse> getFirstPublicPasteboxes() {
        List<PasteboxEntity> list = repository.getListOfPublicAndAlive(publicListSize);
        return list.stream().map(pasteboxEntity ->
                new PasteboxResponse(pasteboxEntity.getHash(), pasteboxEntity.isPublic()))
                .collect(Collectors.toList());
    }

    @Override
    public PasteboxUrlResponse create(PasteboxRequest request) {
        PasteboxEntity pasteboxEntity = new PasteboxEntity();
        pasteboxEntity.setData(request.getData());
        pasteboxEntity.setHash(generateToken());
        pasteboxEntity.setPublic(request.getPublicStatus() == PublicStatus.PUBLIC);
        pasteboxEntity.setTtl(LocalDateTime.now().plusSeconds(request.getTtl()));
        repository.add(pasteboxEntity);

        return new PasteboxUrlResponse(host + "/" + pasteboxEntity.getHash());
    }

    private String generateToken() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        StringBuilder hexString = new StringBuilder();
        byte[] data = md.digest(StringUtils.random(10).getBytes());
        for (byte datum : data) {
            hexString.append(Integer.toHexString((datum >> 4) & 0x0F));
            hexString.append(Integer.toHexString(datum & 0x0F));
        }
        return hexString.toString();
    }

}
