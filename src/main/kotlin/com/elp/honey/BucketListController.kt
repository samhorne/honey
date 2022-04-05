package com.elp.honey

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service;

@RestController
class BucketListResource(val service: BucketListService) {
    @GetMapping
    fun index(): List<BucketListItem>{
        return service.findItems()
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED, reason = "OK")
    fun post(@RequestBody item: BucketListItem){
        service.post(item)
        return
    }

    @DeleteMapping
    fun delete(@RequestBody id:Int){
        return service.delete(id)
    }

    @PutMapping
    fun update(@RequestBody item: BucketListItem){
        return service.update(item)
    }

}
@Table("BUCKET_LIST_ITEM")
data class BucketListItem(
        @Id val id: String?,
        var name: String
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

    fun delete(id:Int) {
        db.deleteById(id.toString())
    }

    fun update(item: BucketListItem){
        var id  = item.id
        var newName = item.name
        println("Item to update: " + id.toString())
        var itemOptional = db.findById(id.toString())
        if (itemOptional.isPresent){
            var item = itemOptional.get()
            item.name = newName
            db.save(item)
        }
        println(item)
//        db.save(item)
    }

}