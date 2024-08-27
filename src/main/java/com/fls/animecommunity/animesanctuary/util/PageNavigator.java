package com.fls.animecommunity.animesanctuary.util;

import lombok.Getter;

@Getter
public class PageNavigator {
    // 페이지 관련 정보
    private final int countPerPage;      // 페이지당 글목록 수
    private final int pagePerGroup;      // 그룹당 페이지 수
    private final int currentPage;       // 현재 페이지 (최근 글이 1부터 시작)
    private final int totalRecordsCount; // 전체 글 수
    private final int totalPageCount;    // 전체 페이지 수
    private final int currentGroup;      // 현재 그룹 (최근 그룹이 0부터 시작)
    private final int startPageGroup;    // 현재 그룹의 첫 페이지
    private final int endPageGroup;      // 현재 그룹의 마지막 페이지
    private final int startRecord;       // 전체 레코드 중 현재 페이지 첫 글의 위치 (0부터 시작)
    private final int totalPageGroup;    // 전체 그룹 수

    public PageNavigator(int countPerPage, int pagePerGroup, int currentPage, int totalRecordsCount) {
        this.countPerPage = countPerPage;
        this.pagePerGroup = pagePerGroup;
        this.totalRecordsCount = totalRecordsCount;

        // 전체 페이지 수 계산
        this.totalPageCount = (int) Math.ceil((double) totalRecordsCount / countPerPage);

        // 현재 페이지 보정
        this.currentPage = validateCurrentPage(currentPage);

        // 현재 그룹 계산
        this.currentGroup = (this.currentPage - 1) / pagePerGroup;

        // 현재 그룹의 첫 페이지 계산
        this.startPageGroup = Math.max(currentGroup * pagePerGroup + 1, 1); // 첫 페이지가 1보다 작으면 1로 설정

        // 현재 그룹의 마지막 페이지 계산
        this.endPageGroup = Math.min(startPageGroup + pagePerGroup - 1, totalPageCount);

        // 전체 레코드 중 현재 페이지 첫 글의 위치 계산
        this.startRecord = (this.currentPage - 1) * countPerPage;
        
        // 전체 그룹 수 계산
        this.totalPageGroup = (int) Math.ceil((double) totalPageCount / pagePerGroup);
    }

    private int validateCurrentPage(int currentPage) {
        if (currentPage < 1) {
            return 1;
        } else if (currentPage > totalPageCount) {
            return totalPageCount;
        } else {
            return currentPage;
        }
    }
}
