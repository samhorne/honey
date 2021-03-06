package com.elp.honey

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import io.mockk.every
import io.mockk.mockk
import com.ninjasquad.springmockk.MockkBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import org.springframework.web.server.ResponseStatusException

class Name(val name: String)

@WebMvcTest
class BadgerApplicationTests(@Autowired val mockMvc: MockMvc){

	@MockkBean
	lateinit var bucketListService: BucketListService
	val mapper = jacksonObjectMapper()
	val BLI = mockk<BucketListItem>()

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
		every { bucketListService.findItems() } returns emptyList<BucketListItem>();
		//Negative expectation
		mockMvc.get("/api/v1/bucketlist/"){
		}.andExpect {
			status{
				isOk()
			}
			content { json("[]") }
		}

	}

	@Test
	fun `get resource where resource exists`(){
		//Positive expectation
		every { bucketListService.findItem(2)} returns bucketListItem

		mockMvc.get("/api/v1/bucketlist/2")
				.andExpect { status { isOk() } }
				.andExpect { content { contentType(MediaType.APPLICATION_JSON) } }
//		TODO: Expect the resource created to be returned.

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
	fun `post resource with valid name`(){
		//Positive expectation
		every { bucketListService.post(any())} returns bucketListItem
		mockMvc.post("/api/v1/bucketlist"){
			contentType = MediaType.APPLICATION_JSON
			content = mapper.writeValueAsString(BucketListItem("2", "Yosemite"))
			accept = MediaType.APPLICATION_JSON
		}.andExpect {
//			status { isCreated() }
			content { contentType((MediaType.APPLICATION_JSON))}
			content { json("{\"id\": \"null\",\"name\": \"Yosemite\"}") }
		}
//		TODO: Fix the mock. bucketListService.post() does not return the newly created id.
//		TODO: Return correct status code.
//		TODO: ID should not be required in the content of the post (auto-incremented).

	}

	@Test
	fun `post resource with blank name`(){
		//Negative expectation
		//Expect 406 response

		every { bucketListService.post(any())} throws ResponseStatusException(HttpStatus.NOT_ACCEPTABLE)
		mockMvc.post("/api/v1/bucketlist"){
			content = MediaType.APPLICATION_JSON
			content = mapper.writeValueAsString(Name(""))
			accept = MediaType.APPLICATION_JSON
		}.andExpect {
			status { isNotAcceptable()}
		}

//		TODO: Investigate why status is different in text context vs PostMan for the same content

	}

	@Test
	fun `put (edit) resource with valid id`(){
		//Positive expectation
		every { bucketListService.update(any())} returns bucketListItem
		mockMvc.put("/api/v1/bucketlist/"){
			content = MediaType.APPLICATION_JSON
			content = mapper.writeValueAsString(BucketListItem("2", "Japan"))
			accept = MediaType.APPLICATION_JSON
		}.andExpect {
			status{
				isOk()
			}
			content { mapper.writeValueAsString(bucketListItem) }
		}
//		TODO: Why is the status returning 415?
	}

	@Test
	fun `put (edit) resource with invalid id`(){
		//Negative expectation
		throw NotImplementedError()

	}

	@Test
	fun `delete resource with valid id`(){
		//Positive expectation
		every { bucketListService.delete(2)} returns bucketListItem
		mockMvc.delete("/api/v1/bucketlist/2"){
		}.andExpect {
			status{
				isOk()
			}
			content { json("{\"id\": \"2\",\"name\": \"Yosemite\"}") }
//			TODO: Refactor to utilize mapper.writeValueAsString(bucketListItem)
		}

	}

	@Test
	fun `delete resource with invalid id`(){
		every { bucketListService.delete(2)} throws ResponseStatusException(HttpStatus.NOT_FOUND)
		mockMvc.delete("/api/v1/bucketlist/2"){
		}.andExpect {
			status{
				isNotFound()
			}
		}

	}

}
