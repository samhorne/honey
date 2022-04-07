package com.elp.honey

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.web.client.TestRestTemplate
import io.mockk.every
import com.ninjasquad.springmockk.MockkBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity.get
import org.springframework.http.ResponseEntity.status
import org.springframework.test.web.client.match.MockRestRequestMatchers.content
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.web.server.ResponseStatusException


@WebMvcTest
class BadgerApplicationTests(@Autowired val mockMvc: MockMvc){

	@MockkBean
	lateinit var bucketListService: BucketListService

	val bucketListItems = mutableListOf(BucketListItem("1", "Paris"))
	val bucketListItem = BucketListItem("2", "Yosemite")

	@Test
	fun `get collection with one valid item present`() {
		//Positive expectation
//		TODO: Check correct number of results and status code
		every { bucketListService.findItems() } returns bucketListItems;

		mockMvc.get("/api/v1/bucketlist/")
				.andExpect { status { isOk() } }
				.andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
	}

	@Test
	fun `get collection with no items present`(){
		//Negative expectation

	}

	@Test
	fun `get resource where resource exists`(){
		//Positive expectation

	}

	@Test
	fun `get resource where resource does not exist`(){
		//Negative expectation
		every { bucketListService.findItem(3) } throws ResponseStatusException(HttpStatus.NOT_FOUND);

		mockMvc.get("/api/v1/bucketlist/3")
				.andExpect { status { isNotFound() } }
//				.andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
	}

	@Test
	fun `post resource with valid nane`(){
		//Positive expectation

	}

	@Test
	fun `post resource with blank name`(){
		//Negative expectation

	}

	@Test
	fun `put (edit) resource with valid id`(){
		//Positive expectation

	}

	@Test
	fun `put (edit) resource with invalid id`(){
		//Negative expectation

	}

	@Test
	fun `delete resource with valid id`(){
		//Positive expectation

	}

	@Test
	fun `delete resource with invalid id`(){
		//Negative expectation

	}

}
