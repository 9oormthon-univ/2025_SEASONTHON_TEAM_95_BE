package org.seasonthon.fakecheck.s3;

import static java.util.Objects.requireNonNull;

import java.util.List;
import org.seasonthon.fakecheck.global.exception.BadRequestException;
import org.seasonthon.fakecheck.global.exception.NotFoundException;
import org.seasonthon.fakecheck.global.exception.dto.ErrorStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageValidator {

    private static final List<String> WHITE_LIST = List.of("jpg", "jpeg", "png", "webp", "heic");

    public void validateFile(MultipartFile file) {
        validateFileNameNotBlank(file);
        validateFileExtension(file);
    }

    private void validateFileNameNotBlank(MultipartFile file) {
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new NotFoundException(ErrorStatus.NOT_FOUND_FILE);
        }
    }

    private void validateFileExtension(MultipartFile file) {
        String filename = file.getOriginalFilename();

        int extensionIndex = requireNonNull(filename).lastIndexOf(".");
        String extension = filename.substring(extensionIndex + 1);

        if (extensionIndex == -1 || filename.endsWith(".") || !WHITE_LIST.contains(extension.toLowerCase())) {
            throw new BadRequestException(ErrorStatus.INVALID_FILE_EXTENSION);
        }
    }

}