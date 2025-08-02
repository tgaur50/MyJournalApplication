package com.journal.app.JournalApplication.Cache;

import com.journal.app.JournalApplication.Entity.ApiCacheEntity;
import com.journal.app.JournalApplication.Repository.ApiCacheRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ApiCache {
    @Autowired
    private ApiCacheRepository apiCacheRepository;

    public Map<String, String> apiConfig = null;

    @PostConstruct
    public void init(){
        apiConfig = new HashMap<>();
        List<ApiCacheEntity> listApiConfig = apiCacheRepository.findAll();
        apiConfig = listApiConfig.stream().
                collect(Collectors.toMap(ApiCacheEntity::getKey,
                        ApiCacheEntity::getValue));
    }
}
