package com.androidapp.cakelistapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.androidapp.cakelistapp.app.viewmodel.MainViewModel
import com.androidapp.cakelistapp.app.viewmodel.Status
import com.androidapp.cakelistapp.data.model.CakeModel
import com.androidapp.cakelistapp.data.repository.ApiResponse
import com.androidapp.cakelistapp.data.repository.CakeListRepositoryImpl
import junit.framework.TestCase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit

/**
 * Created by Nur Uysal on 06/12/2021.
 */
@ExperimentalCoroutinesApi
class ViewModelTest {

    private lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var repository: CakeListRepositoryImpl

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
        viewModel = MainViewModel(repository)
    }

    @Test
    fun `check data load succeeds when the repository return successful data list`() {
        runBlocking {
            Mockito.`when`(repository.getCakeList()).thenReturn(ApiResponse.Success(dummyCakeList))

            assertEquals(Status.LOADING, viewModel.data.value?.status)
            assertNull(viewModel.data.value?.data)

            viewModel.data.getOrAwaitValue()
            viewModel.getCakeList()
            assertEquals(Status.SUCCESS, viewModel.data.value?.status)
            assertNotNull(viewModel.data.value?.data)
        }
    }

    @Test
    fun `check data load succeeds, duplicates removed and sorting applied to list before presenting`() {
        runBlocking {
            Mockito.`when`(repository.getCakeList()).thenReturn(ApiResponse.Success(dummyCakeList))

            assertEquals(Status.LOADING, viewModel.data.value?.status)
            assertNull(viewModel.data.value?.data)

            viewModel.data.getOrAwaitValue()
            viewModel.getCakeList()
            assertEquals(Status.SUCCESS, viewModel.data.value?.status)
            val filtered = viewModel.getFilteredCakeList(dummyCakeList.toMutableList())
            assertEquals(filtered, viewModel.data.value?.data)
        }
    }


    @Test
    fun `check getting error when repository return error`() {
        runBlocking {
            Mockito.`when`(repository.getCakeList())
                .thenReturn(ApiResponse.Error("Repository Error!"))
            assertEquals(Status.LOADING, viewModel.data.value?.status)
            assertNull(viewModel.data.value?.data)

            viewModel.getCakeList()
            viewModel.data.getOrAwaitValue()

            assertEquals(Status.ERROR, viewModel.data.value?.status)
            assertNull(viewModel.data.value?.data)
            assertEquals("Repository Error!", viewModel.data.value?.errorMessage)

        }
    }

    @Test(expected = Exception::class)
    fun `check getting error if repository get exception`() {
        runBlocking {
            Mockito.`when`(repository.getCakeList()).thenThrow(Exception("Exception Error Message"))

            assertEquals(Status.LOADING, viewModel.data.value?.status)
            assertNull(viewModel.data.value?.data)

            try {
                viewModel.getCakeList()
            } catch (e: Exception) {
                assertEquals(Status.ERROR, viewModel.data.value?.status)
                assertNull(viewModel.data.value?.data)
                assertEquals("Exception Error Message", viewModel.data.value?.errorMessage)
            }
        }
    }

}