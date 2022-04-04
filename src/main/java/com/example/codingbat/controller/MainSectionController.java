package com.example.codingbat.controller;

import com.example.codingbat.entity.MainSection;
import com.example.codingbat.payload.Status;
import com.example.codingbat.service.MainSectionService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/main-section")
public record MainSectionController(MainSectionService service){

    @GetMapping(value = "/all")
    public HttpEntity<List<MainSection>> all(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(value = "/{id}")
    public HttpEntity<?> getById(@PathVariable Long id) {
        MainSection byId = service.getById(id);
        return byId != null ? ResponseEntity.ok(byId):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Status.builder()
                                .status(false)
                                .message("Main section not found")
                                .build()
                );
    }

    @PostMapping(value = "/add")
    public HttpEntity<Status> add(@Valid @RequestBody MainSection mainSection) {
        Status add = service.add(mainSection);
        return add.isStatus() ? ResponseEntity.ok(add):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(add);
    }

    @PutMapping(value = "/{id}")
    public HttpEntity<Status> edit(@PathVariable Long id,@Valid @RequestBody MainSection mainSection) {
        Status edit = service.edit(id, mainSection);
        return edit.isStatus() ? ResponseEntity.ok(edit):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(edit);
    }

    @DeleteMapping(value = "{id}")
    public HttpEntity<Status> delete(@PathVariable Long id){
        Status delete = service.delete(id);
        return delete.isStatus() ? ResponseEntity.ok(delete):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(delete);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public static Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((org.springframework.validation.FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
