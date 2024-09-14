package com.fls.animecommunity.animesanctuary.service.interfaces;

import com.fls.animecommunity.animesanctuary.dto.NoteLikeDto.NoteLikeRequestDto;
import com.fls.animecommunity.animesanctuary.dto.NoteLikeDto.NoteLikeResponseDto;

import jakarta.validation.Valid;

public interface NoteLikeService {

	NoteLikeResponseDto addLike(@Valid NoteLikeRequestDto noteLikeRequestDto);

	NoteLikeResponseDto removeLike(@Valid NoteLikeRequestDto noteLikeRequestDto);

	NoteLikeResponseDto getLikeStatus(Long noteId, Long memberId);

	NoteLikeResponseDto getLikeCount(Long noteId);



}
