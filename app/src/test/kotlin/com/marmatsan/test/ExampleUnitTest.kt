package com.marmatsan.test

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.mockk
import org.junit.jupiter.api.Test

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertThat(2 + 2).isEqualTo(4)
    }
}