package com.mirkojovanovic.thelordoftheringsjourney.data.repository

import com.mirkojovanovic.thelordoftheringsjourney.R
import com.mirkojovanovic.thelordoftheringsjourney.common.Resource
import com.mirkojovanovic.thelordoftheringsjourney.common.util.UiText
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.character.toCharacter
import com.mirkojovanovic.thelordoftheringsjourney.data.remote.TheOneApi
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.character.Character
import com.mirkojovanovic.thelordoftheringsjourney.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val theOneApi: TheOneApi,
) : CharacterRepository {

    override suspend fun getCharacter(id: String): Flow<Resource<Character>> = flow {
        try {
            emit(Resource.Loading())
            val characterPageDto = theOneApi.getCharacter(id)
            emit(Resource.Success(characterPageDto.docs[0].toCharacter()))
        } catch (e: HttpException) {
            emit(Resource.Error(UiText.StringResource(R.string.error_couldnt_load_the_character)))
        } catch (e: IOException) {
            emit(Resource.Error(UiText.unknownError()))
        }
    }

    override suspend fun getCharacters(): Flow<Resource<List<Character>>> = flow {
        try {
            emit(Resource.Loading())
            val characterPageDto = theOneApi.getCharacters()
            emit(Resource.Success(characterPageDto.docs.map { it.toCharacter() }))
        } catch (e: HttpException) {
            emit(Resource.Error(UiText.StringResource(R.string.error_couldnt_load_characters)))
        } catch (e: IOException) {
            emit(Resource.Error(UiText.unknownError()))
        }
    }

}