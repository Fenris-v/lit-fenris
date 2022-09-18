package org.example.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.app.services.validator.NotNullFile;
import org.example.app.services.validator.ValidImage;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadFile {
    @NotNullFile
    @ValidImage(message = "File must be PNG or JPG")
    private MultipartFile file;
}
