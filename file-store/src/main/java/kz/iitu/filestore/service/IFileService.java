package kz.iitu.filestore.service;

import kz.iitu.filestore.model.LoadFile;
import kz.iitu.filestore.model.dto.FileDto;
import kz.iitu.filestore.model.wrapper.CustomResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IFileService {
    FileDto addFile(MultipartFile upload) throws IOException;

    FileDto updateFile(MultipartFile upload, String id) throws IOException;

    List<FileDto> addFileList(MultipartFile[] upload) throws IOException;

    LoadFile downloadFile(String id) throws IOException;

    CustomResponse<?> deleteById(String id);
}
