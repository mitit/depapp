package group.depapp.service;

import org.springframework.transaction.annotation.Transactional;

public interface SynchronizeService {
    @Transactional
    void synchronize();
}
