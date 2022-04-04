package com.example.codingbat.controller;

import com.example.codingbat.entity.Problem;
import com.example.codingbat.payload.ProblemDto;
import com.example.codingbat.payload.SolveDto;
import com.example.codingbat.payload.Status;
import com.example.codingbat.service.ProblemService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.example.codingbat.controller.MainSectionController.handleValidationExceptions;

@RestController
@RequestMapping(value = "api/problem")
public record ProblemController (ProblemService service) {

    @GetMapping(value = "/all")
    public HttpEntity<List<Problem>> all(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(value = "/{id}")
    public HttpEntity<?> getById(@PathVariable Long id) {
        Problem byId = service.getById(id);
        return byId != null ? ResponseEntity.ok(byId):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Status.builder()
                                .status(false)
                                .message("Member not found")
                                .build()
                );
    }

    @PostMapping(value = "/add")
    public HttpEntity<Status> add(@Valid @RequestBody ProblemDto dto){
        Status add = service.add(dto);
        return add.isStatus() ? ResponseEntity.ok(add):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(add);
    }

    @PutMapping(value = "/{id}")
    public HttpEntity<Status> edit(@PathVariable Long id,@Valid @RequestBody ProblemDto dto){
        Status edit = service.edit(id, dto);
        return edit.isStatus() ? ResponseEntity.ok(edit):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(edit);
    }

    @DeleteMapping(value = "{id}")
    public HttpEntity<Status> delete(@PathVariable Long id){
        Status delete = service.delete(id);
        return delete.isStatus() ? ResponseEntity.ok(delete):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(delete);
    }
    @PostMapping(value = "/solve/{id}")
    public HttpEntity<Status> solve(@PathVariable Long id,@Valid @RequestBody SolveDto dto){
        Status solve = service.solve(id, dto);
        return solve.isStatus() ? ResponseEntity.ok(solve):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(solve);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> getException(
            MethodArgumentNotValidException ex) {
        return handleValidationExceptions(ex);
    }
}
