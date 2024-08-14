package com.fls.animecommunity.animesanctuary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fls.animecommunity.animesanctuary.model.Scrap;
import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    List<Scrap> findByUserId(Long userId);
}
