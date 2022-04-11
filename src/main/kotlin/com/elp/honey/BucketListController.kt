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
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/bucketlist")
class BucketListResource(val service: BucketListService) {
    @GetMapping("/")
    fun index(): MutableIterable<BucketListItem> {
        return service.findItems()
    }
    @GetMapping("/{id}")
    fun getItem(@PathVariable id: Int): BucketListItem{
        println("Return specific item: $id")
        return service.findItem(id)
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    fun post(@RequestBody item: BucketListItem): BucketListItem {
        return service.post(item)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): BucketListItem{
        return service.delete(id)
    }

    @PutMapping()
    fun update(@RequestBody item: BucketListItem): BucketListItem{
        println(item)
        return service.update(item)
    }

}
@Table("BUCKET_LIST_ITEM")
data class BucketListItem(
        @Id val id: String?,
        var name: String
        )

@Service
class BucketListService(val db: CrudRepository<BucketListItem, String>) {
    fun findItems(): MutableIterable<BucketListItem> = db.findAll()

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
        if (item.name == ""){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        }
        return db.save(item)
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

    fun update(item: BucketListItem): BucketListItem{
        var id  = item.id
        var newName = item.name
        println("Item to update: " + id.toString())
        var itemOptional = db.findById(id.toString())
        if (itemOptional.isPresent){
            var item = itemOptional.get()
            item.name = newName
            return db.save(item)
        }
        else{
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "This item does not exist")
        }
    }

}
//TODO: Seperate file for repository and interface
//TODO: Accept id in the path for PUT. Anything dealing with a specific id should reside in the path
//TODO: Mock out db functions instead of service
