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
    fun index(): List<BucketListItem> = service.findItems()

    @PostMapping
    fun post(@RequestBody item: BucketListItem){
        service.post(item)
    }
}
@Table("bucket_list_item")
data class BucketListItem(
        @Id val id: String?,
        val name: String
        )

interface ItemRepository: CrudRepository<BucketListItem, String>{
    @Query("select * from bucket_list_item")
    fun findItems(): List<BucketListItem>
}

@Service
class BucketListService(val db: ItemRepository) {
    fun findItems(): List<BucketListItem> = db.findItems()

    fun post(item: BucketListItem){
        db.save(item)
    }
}