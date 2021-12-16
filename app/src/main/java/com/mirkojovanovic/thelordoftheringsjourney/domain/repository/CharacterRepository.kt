package com.mirkojovanovic.thelordoftheringsjourney.domain.repository

import com.mirkojovanovic.thelordoftheringsjourney.common.Resource
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.character.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    suspend fun getCharacter(id: String): Flow<Resource<Character>>
}