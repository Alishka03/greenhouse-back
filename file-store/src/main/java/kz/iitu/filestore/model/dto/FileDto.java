package kz.iitu.filestore.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
@AllArgsConstructor
public class FileDto {
    private String id;
    private String fileName;
    public FileDto(String id) {
        this.id = id;
    }
}
