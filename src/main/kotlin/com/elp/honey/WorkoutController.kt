package com.elp.honey

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class WorkoutController {

    @GetMapping("/workout")
    fun all(): String {
        return "Hello world"
    }
}