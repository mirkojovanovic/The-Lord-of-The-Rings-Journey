package com.mirkojovanovic.thelordoftheringsjourney.domain.use_case.characters

import com.mirkojovanovic.thelordoftheringsjourney.common.Resource
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.character.Character
import com.mirkojovanovic.thelordoftheringsjourney.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
) {

    suspend operator fun invoke(): Flow<Resource<List<Character>>> =
        characterRepository.getCharacters()
}