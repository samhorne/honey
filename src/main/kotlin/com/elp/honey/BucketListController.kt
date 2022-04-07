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
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/bucketlist")
class BucketListResource(val service: BucketListService) {
    @GetMapping("/")
    fun index(): List<BucketListItem>{
        return service.findItems()
    }
    @GetMapping("/{id}")
    fun getItem(@PathVariable id: Int): BucketListItem{
        println("Return specific item: $id")
        return service.findItem(id)
    }

    @PostMapping()
//    @ResponseStatus(code = HttpStatus.CREATED, reason = "OK")
    fun post(@RequestBody item: BucketListItem): BucketListItem {
        return service.post(item)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): BucketListItem{
        return service.delete(id)
    }

    @PutMapping()
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

    fun findItem(id: Int): BucketListItem{
        var itemOptional = db.findById(id.toString())
        if (itemOptional.isPresent){
            var item = itemOptional.get()
            return item
        }
        else{
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist")
        }
    }

    fun post(item: BucketListItem): BucketListItem{
        db.save(item)
        return item
    }

    fun delete(id:Int): BucketListItem{
        var itemOptional = db.findById(id.toString())
        if (itemOptional.isPresent){
            var item = itemOptional.get()
            db.deleteById(id.toString())
            return item
        }
        else{
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "This item does not exist")
        }
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
        else{
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "This item does not exist")
        }
        println(item)
//        db.save(item)
    }

}