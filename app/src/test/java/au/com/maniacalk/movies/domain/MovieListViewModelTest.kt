package au.com.maniacalk.movies.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import assertk.assertThat
import assertk.assertions.isEqualTo
import au.com.maniacalk.movies.repository.OmdbRepository
import au.com.maniacalk.movies.repository.model.MovieDetail
import au.com.maniacalk.movies.repository.model.SearchResults
import au.com.maniacalk.movies.support.CoroutineTestRule
import au.com.maniacalk.movies.view.DetailViewState
import au.com.maniacalk.movies.view.ListViewState
import au.com.maniacalk.movies.view.Status
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.times
import java.lang.RuntimeException


@ExperimentalCoroutinesApi
open class MovieListViewModelTest {

    @get:Rule
    open var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutineTestRule()

    private val observerList = mockk<Observer<ListViewState>>()

    private val observerDetail = mockk<Observer<DetailViewState>>()

    @MockK
    private lateinit var repo: OmdbRepository
    lateinit var vm: MovieContentViewModel

    @Before
    fun before() {
        MockKAnnotations.init(this)
        vm = MovieContentViewModel(repo)
    }

    @After
    fun after() {
        unmockkAll()
    }

    @Test
    fun `searchMovies Success`() {
        testCoroutineRule.runBlockingTest {
            coEvery { repo.searchMovies(any()) } returns SearchResults(
                response = "True",
                movies = listOf(
                    SearchResults.Movie(
                        imdbID = "ABC123",
                        title = "The Matrix",
                        type = "movie",
                        year = "2000"
                    )
                )
            )
            vm.searchMovies("The matrix").observeForever(observerList)
            val captor = mutableListOf<ListViewState>()

            coVerify(exactly = 2) { observerList.onChanged(capture(captor)) }
            assertThat(captor.size).isEqualTo(2)
            assertThat(captor[0].status).isEqualTo(Status.LOADING)
            assertThat(captor[1].status).isEqualTo(Status.SUCCESS)
        }
    }

    @Test
    fun `searchMovies Error`() {
        testCoroutineRule.runBlockingTest {
            coEvery { repo.searchMovies(any()) } throws RuntimeException("Some Error")

            vm.searchMovies().observeForever(observerList)
            vm.searchMovies("aaasa")
            val captor = mutableListOf<ListViewState>()

            coVerify(exactly = 2) { observerList.onChanged(capture(captor)) }
            assertThat(captor[0].status).isEqualTo(Status.LOADING)
            assertThat(captor[1].status).isEqualTo(Status.ERROR)
        }
    }

    @Test
    fun `getDetail Success`() {
        testCoroutineRule.runBlockingTest {
            coEvery { repo.getDetail(any()) } returns MovieDetail(
                response = "True",
                title = "The Matrix",
                type = "movie",
                year = "2000"
            )
            vm.getDetail("The Matrix").observeForever(observerDetail)
            val captor = mutableListOf<DetailViewState>()

            coVerify(exactly = 2) { observerDetail.onChanged(capture(captor)) }
            assertThat(captor[0].status).isEqualTo(Status.LOADING)
            assertThat(captor[1].status).isEqualTo(Status.SUCCESS)
        }
    }

    @Test
    fun `getDetail Error`() {
        testCoroutineRule.runBlockingTest {
            coEvery { repo.getDetail(any()) } throws RuntimeException("Some Error")

            vm.getDetail("ABC123").observeForever(observerDetail)
            val captor = mutableListOf<DetailViewState>()

            coVerify(exactly = 2) { observerDetail.onChanged(capture(captor)) }
            assertThat(captor[0].status).isEqualTo(Status.LOADING)
            assertThat(captor[1].status).isEqualTo(Status.ERROR)
        }
    }
}