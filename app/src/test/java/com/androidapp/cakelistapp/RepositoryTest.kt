package com.androidapp.cakelistapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.androidapp.cakelistapp.data.model.CakeModel
import com.androidapp.cakelistapp.data.repository.ApiResponse
import com.androidapp.cakelistapp.data.repository.CakeListRepositoryImpl
import com.androidapp.cakelistapp.domain.api.ApiService
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit
import retrofit2.Response
/**
 * Created by Nur Uysal on 06/12/2021.
 */
class RepositoryTest {
    @Mock
    private lateinit var repository: CakeListRepositoryImpl

    @Mock
    lateinit var service: ApiService

    @Spy
    private val dummyCakeList: List<CakeModel> = listOf(
        CakeModel(
            "victoria sponge",
            "A victoria made of lemon",
            "https://upload.wikimedia.org/wikipedia/commons/0/05/111rfyh.jpg\""
        ),
        CakeModel(
            "Banana cake",
            "title",
            "https://upload.wikimedia.org/wikipedia/commons/0/05/111rfyh.jpg\""
        ),
        CakeModel(
            "Lemon cheesecake",
            "A kemon made of lemon",
            "https://upload.wikimedia.org/wikipedia/commons/0/05/111rfyh.jpg\""
        ),
        CakeModel(
            "Birthday cake",
            "A cake made of lemon",
            "https://upload.wikimedia.org/wikipedia/commons/0/05/111rfyh.jpg\""
        ),
        CakeModel(
            "Birthday cake",
            "A cake made of lemon",
            "https://upload.wikimedia.org/wikipedia/commons/0/05/111rfyh.jpg\""
        ),
        CakeModel(
            "Lemon cheesecake",
            "A kemon made of lemon",
            "https://upload.wikimedia.org/wikipedia/commons/0/05/111rfyh.jpg\""
        ),
        CakeModel(
            "victoria sponge",
            "A victoria made of lemon",
            "https://upload.wikimedia.org/wikipedia/commons/0/05/111rfyh.jpg\""
        )
    )

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    var rule = MockitoJUnit.rule()

    @Before
    fun setup() {
        repository = CakeListRepositoryImpl(service)
    }


    @Test
    fun `check happy path return success when service response has cake list`() {
        runBlocking {
            Mockito.`when`(service.getCakeList()).thenReturn(Response.success(dummyCakeList))
            val response = repository.getCakeList()
            MatcherAssert.assertThat(
                response,
                CoreMatchers.`is`(ApiResponse.Success(dummyCakeList))
            )

        }
    }

    @Test
    fun `check getting error when serice response is null`() {
        runBlocking {
            Mockito.`when`(service.getCakeList()).thenReturn(Response.success(null))
            val response = repository.getCakeList()
            MatcherAssert.assertThat(
                response,
                CoreMatchers.`is`(ApiResponse.Error("Response is null!"))
            )

        }
    }

    @Test
    fun `check getting error when service get exception`() {
        runBlocking {
            Mockito.`when`(service.getCakeList()).then { throw Exception() }
            val response = repository.getCakeList()
            MatcherAssert.assertThat(response, CoreMatchers.`is`(ApiResponse.Error("Exception!")))
        }
    }


}


