package com.mirkojovanovic.thelordoftheringsjourney.data.dto.character

import com.mirkojovanovic.thelordoftheringsjourney.domain.model.character.Character

data class CharacterDocDto(
    val _id: String,
    val birth: String,
    val death: String,
    val gender: String,
    val hair: String,
    val height: String,
    val name: String,
    val race: String,
    val realm: String,
    val spouse: String,
    val wikiUrl: String,
)

fun CharacterDocDto.toCharacter() =
    Character(
        _id = _id,
        name = name
    )