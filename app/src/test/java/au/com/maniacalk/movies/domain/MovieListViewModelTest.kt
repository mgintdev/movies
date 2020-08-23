package au.com.maniacalk.movies.domain

import io.kotest.core.spec.style.FunSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.mockk.unmockkAll

class MovieListViewModelTest : FunSpec(){

    lateinit var vm: MovieContentViewModel

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)

        vm = MovieContentViewModel()
    }

    override fun afterTest(testCase: TestCase, result: TestResult) {
        super.afterTest(testCase, result)

        unmockkAll()
    }

    init {

        test("DefaultList") {
            val result = vm.getDefaultMovies()
        }

    }

}