package com.fls.animecommunity.animesanctuary.service;

import org.springframework.stereotype.Service;
import com.fls.animecommunity.animesanctuary.model.Scrap;
import com.fls.animecommunity.animesanctuary.repository.ScrapRepository;
import java.util.List;

@Service
public class ScrapService {

    private final ScrapRepository scrapRepository;

    public ScrapService(ScrapRepository scrapRepository) {
        this.scrapRepository = scrapRepository;
    }

    public List<Scrap> getScraps(Long memberId) {
        return scrapRepository.findByMemberId(memberId);
    }
}

