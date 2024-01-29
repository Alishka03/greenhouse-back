package kz.iitu.filestore.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import kz.iitu.filestore.model.LoadFile;
import kz.iitu.filestore.model.dto.FileDto;
import kz.iitu.filestore.model.wrapper.CustomResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService implements IFileService {

    @Autowired
    private GridFsTemplate template;

    @Autowired
    private GridFsOperations operations;

    @Override
    public FileDto addFile(MultipartFile upload) throws IOException {

        DBObject metadata = new BasicDBObject();
        metadata.put("fileSize", upload.getSize());

        Object fileID = template.store(upload.getInputStream(), upload.getOriginalFilename(), upload.getContentType(), metadata);
        return new FileDto(fileID.toString(), upload.getOriginalFilename());
    }

    public FileDto updateFile(MultipartFile upload, String id) throws IOException {
        template.delete(new Query(Criteria.where("_id").is(id)));
        return addFile(upload);
    }

    @Override
    public List<FileDto> addFileList(MultipartFile[] upload) throws IOException {
        List<FileDto> fileDtoList = new ArrayList<>();
        System.out.println(upload[0].toString());
        for (MultipartFile file: upload) {
            fileDtoList.add(addFile(file));
        }
        for (FileDto var: fileDtoList) {

            System.out.println(var.getId());
        }
        return fileDtoList;
    }

    @Override
    public LoadFile downloadFile(String id) throws IOException {

        //search file
        GridFSFile gridFSFile = template.findOne(new Query(Criteria.where("_id").is(id)));


        //convert uri to byteArray
        //save data to LoadFile class
        LoadFile loadFile = new LoadFile();

        if (gridFSFile != null && gridFSFile.getMetadata() != null) {
            loadFile.setFilename(gridFSFile.getFilename());

            loadFile.setFileType(gridFSFile.getMetadata().get("_contentType").toString());

            loadFile.setFileSize(gridFSFile.getMetadata().get("fileSize").toString());

            loadFile.setFile(IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()));
        }

        return loadFile;
    }

    @Override
    public CustomResponse<?> deleteById(String id) {
        CustomResponse<String> response = new CustomResponse<>();
        try {
            template.delete(new Query(Criteria.where("_id").is(id)));
            response.setSuccess(true);
            return response;
        } catch (Exception e) {
            response.setSuccess(false);
            return response;
        }

    }
}
