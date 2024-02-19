package com.hocheol.blind.data.model

import com.hocheol.blind.data.model.dto.ContentDto
import com.hocheol.blind.data.model.entity.ContentEntity
import com.hocheol.blind.domain.model.Content
import java.util.Date

object ContentMapper {

    fun Content.toRequest() = ContentDto(
        id = id,
        title = title,
        content = content,
        category = category,
        likeCount = likeCount,
        commentCount = commentCount,
        viewCount = viewCount,
        createdDate = createdDate
    )

    fun Content.toEntity() = ContentEntity(
        id = id ?: -1,
        title = title,
        content = content,
        category = category,
        likeCount = likeCount,
        commentCount = commentCount,
        viewCount = viewCount,
        createdDate = createdDate,
    )

    fun ContentDto.toContent() = Content(
        id = id ?: -1,
        title = title,
        content = content,
        category = category,
        likeCount = likeCount ?: 0,
        commentCount = commentCount ?: 0,
        viewCount = viewCount ?: 0,
        createdDate = createdDate ?: Date(),
    )

    fun ContentDto.toEntity() = ContentEntity(
        id = id ?: -1,
        title = title,
        content = content,
        category = category,
        likeCount = likeCount ?: 0,
        commentCount = commentCount ?: 0,
        viewCount = viewCount ?: 0,
        createdDate = createdDate ?: Date(),
    )

    fun ContentEntity.toContent() = Content(
        id = id,
        title = title,
        content = content,
        category = category,
        likeCount = likeCount,
        commentCount = commentCount,
        viewCount = viewCount,
        createdDate = createdDate,
    )
}