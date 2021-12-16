package com.mirkojovanovic.thelordoftheringsjourney.data.dto.character

import com.mirkojovanovic.thelordoftheringsjourney.domain.model.character.Character

data class CharacterPageDto(
    val docs: List<CharacterDocDto>,
    val limit: Int,
    val offset: Int,
    val page: Int,
    val pages: Int,
    val total: Int,
)

fun CharacterPageDto.toCharacter(): Character =
    Character(
        _id = docs[0]._id,
        name = docs[0].name
    )