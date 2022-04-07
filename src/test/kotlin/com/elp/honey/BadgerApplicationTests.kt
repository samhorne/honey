package com.elp.honey

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.web.client.TestRestTemplate
import io.mockk.every
import com.ninjasquad.springmockk.MockkBean
import org.springframework.http.RequestEntity.get
import org.springframework.http.ResponseEntity.status
import org.springframework.test.web.client.match.MockRestRequestMatchers.content
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get


@WebMvcTest
class BadgerApplicationTests(@Autowired val mockMvc: MockMvc){

	@MockkBean
	lateinit var bucketListService: BucketListService

	val bucketListItem = BucketListItem("1", "Paris")

	@Test
	fun exampleTest() {
		every { bucketListService.findItem(1) } returns bucketListItem;
		mockMvc.get("/api/v1/bucketlist/")
				.andExpect { status { isOk() } }

	}

	@Test
	fun contextLoads() {
		var sum = 2 + 2
		var answer = 4
		assertEquals(sum, answer)
	}

//	@Test
//	fun testMyCode(){
//		mockMvc.get("/api/v1/bucketlist/")
//				.andExpect {
//					status {
//						isOk()
//					}
//				}
//	}

}
