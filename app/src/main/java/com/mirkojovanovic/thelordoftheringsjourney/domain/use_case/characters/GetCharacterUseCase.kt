package com.mirkojovanovic.thelordoftheringsjourney.domain.use_case.characters

import com.mirkojovanovic.thelordoftheringsjourney.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
) {

    suspend operator fun invoke(id: String) = characterRepository.getCharacter(id)
}