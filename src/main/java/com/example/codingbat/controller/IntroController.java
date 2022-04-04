package com.example.codingbat.controller;

import com.example.codingbat.entity.Intro;
import com.example.codingbat.payload.IntroDto;
import com.example.codingbat.payload.Status;
import com.example.codingbat.service.AttachmentService;
import com.example.codingbat.service.IntroService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.example.codingbat.controller.MainSectionController.handleValidationExceptions;

@RestController
@RequestMapping(value = "api/intro")
public record IntroController (IntroService service,
                               AttachmentService attachmentService) {

    @GetMapping(value = "/all")
    public HttpEntity<List<Intro>> all(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(value = "/{id}")
    public HttpEntity<?> getById(@PathVariable Long id) {
        Intro byId = service.getById(id);
        return byId != null ? ResponseEntity.ok(byId):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Status.builder()
                                .status(false)
                                .message("Member not found")
                                .build()
                );
    }

    @PostMapping(value = "/add")
    public HttpEntity<Status> add(@Valid @RequestBody IntroDto dto){
        Status add = service.add(dto);
        return add.isStatus() ? ResponseEntity.ok(add):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(add);
    }

    @PutMapping(value = "/{id}")
    public HttpEntity<Status> edit(@PathVariable Long id,@Valid @RequestBody IntroDto dto,
                                   MultipartHttpServletRequest request){
        Status edit = service.edit(id, dto, request);
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
    public Map<String, String> getException(
            MethodArgumentNotValidException ex) {
        return handleValidationExceptions(ex);
    }
    @PostMapping(value = "/upload/{introId}")
    public HttpEntity<Status> upload(@PathVariable Long introId, MultipartHttpServletRequest request){
        Status upload = service.upload(introId, request);
        return upload.isStatus() ? ResponseEntity.ok(upload):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(upload);
    }

    @GetMapping(value = "/get/{IntroId}")
    public void getAttachment(@PathVariable String IntroId, HttpServletResponse response) {
        attachmentService.getAttachment(IntroId, response);
    }
}
