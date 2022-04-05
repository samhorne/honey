package com.elp.honey

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@RestController
class BucketListResource(val service: BucketListService) {
    @GetMapping
    fun index(): List<BucketListItem>{
        println("GET CALLED")
        return service.findItems()
    }

    @PostMapping
    fun post(@RequestBody item: BucketListItem){
        println("POST CALLED")
        println(item);
        service.post(item)
    }
}
@Table("BUCKET_LIST_ITEM")
data class BucketListItem(
        @Id val id: String?,
        val name: String
        )

interface ItemRepository: CrudRepository<BucketListItem, String>{
    @Query("select * from BUCKET_LIST_ITEM")
    fun findItems(): List<BucketListItem>
}

@Service
class BucketListService(val db: ItemRepository) {
    fun findItems(): List<BucketListItem> = db.findItems()

    fun post(item: BucketListItem){
        db.save(item)
    }
}