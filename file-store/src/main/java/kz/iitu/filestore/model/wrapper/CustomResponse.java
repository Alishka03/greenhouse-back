package kz.iitu.filestore.model.wrapper;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CustomResponse<T> {
    private Boolean success;
    private T data;
    private String desc;
}
