package kz.iitu.filestore.controller;


import kz.iitu.filestore.model.LoadFile;
import kz.iitu.filestore.model.wrapper.CustomResponse;
import kz.iitu.filestore.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin("*")
@RequestMapping("/file")
public class FileController {

    @Autowired
    private IFileService fileService;

    @PostMapping(value = "/upload", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> upload(@RequestPart("file") MultipartFile file,
                                    @RequestParam(value = "id", required = false) String id) throws IOException {
        if (file == null) {
            return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND);
        }
//        HashMap<String, FileDto> map = new HashMap<>();
//        map.put("id", );
        return ResponseEntity.ok(id == null ? fileService.addFile(file) : fileService.updateFile(file, id));
    }

    @PostMapping(value = "/upload-list", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> uploadList(@RequestPart("file") MultipartFile[] file) throws IOException {
        if (file == null) {
            return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(fileService.addFileList(file));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String id) throws IOException {
        LoadFile loadFile = fileService.downloadFile(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(loadFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + loadFile.getFilename() + "\"")
                .body(new ByteArrayResource(loadFile.getFile()));
    }

    @GetMapping("/excel/{id}")
    public ResponseEntity<ByteArrayResource> downloadExcel(@PathVariable String id) throws IOException {
        LoadFile loadFile = fileService.downloadFile(id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + loadFile.getFilename() + ".xlsx\"")
                .body(new ByteArrayResource(loadFile.getFile()));
    }

    @DeleteMapping("/delete/{id}")
    private CustomResponse<?> delete(@PathVariable String id) throws IOException {
        return fileService.deleteById(id);
    }
}
