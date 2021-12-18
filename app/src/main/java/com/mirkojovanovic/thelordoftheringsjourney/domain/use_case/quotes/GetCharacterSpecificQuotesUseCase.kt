package com.mirkojovanovic.thelordoftheringsjourney.domain.use_case.quotes

import com.mirkojovanovic.thelordoftheringsjourney.domain.model.character.Character
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.quote.QuoteDoc
import kotlinx.coroutines.ExperimentalCoroutinesApi

class GetCharacterSpecificQuotesUseCase {

    @ExperimentalCoroutinesApi
    operator fun invoke(
        query: String,
        characters: List<Character>,
        quotes: List<QuoteDoc>,
    ): List<QuoteDoc> = quotes.filter { quote ->
        quote.character in characters.filter { it.name.startsWith(query) }.map { it._id }
    }
}
