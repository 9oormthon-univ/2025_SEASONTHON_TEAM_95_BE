package org.seasonthon.fakecheck.s3;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.seasonthon.fakecheck.global.exception.InternalServerException;
import org.seasonthon.fakecheck.global.exception.dto.ErrorStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Provider {

    private final S3Client s3Client;
    private final ImageValidator imageValidator;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.dir}")
    private String dir;

    public List<String> uploadImages(List<MultipartFile> files) {
        List<String> images = new ArrayList<>();
        for (MultipartFile file : files) {
            imageValidator.validateFile(file);
            String image = uploadImage(file);
            images.add(image);
        }

        return images;
    }

    public String uploadImage(MultipartFile file) {
        imageValidator.validateFile(file);

        String fileName = createFileName(file);
        uploadImageToS3(file, fileName, s3Client);

        return getFileUrl(fileName);
    }

    public void deleteImages(List<String> imageUrls) {
        imageUrls.forEach(this::deleteImage);
    }

    public void deleteImage(String imageUrl) {
        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(imageUrl)
                    .build();
            s3Client.deleteObject(request);
        } catch (S3Exception e) {
            throw new InternalServerException(ErrorStatus.AWS_S3_ERROR);
        } catch (Exception ex) {
            throw new InternalServerException(ErrorStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void uploadImageToS3(MultipartFile file, String fileName, S3Client s3Client) {
        try {
            InputStream is = file.getInputStream();
            RequestBody requestBody = RequestBody.fromInputStream(is, file.getSize());

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();
            s3Client.putObject(putObjectRequest, requestBody);

            is.close();
        } catch (IOException | S3Exception e) {
            throw new InternalServerException(ErrorStatus.FAILED_TO_UPLOAD_FILE);
        }
    }

    private String createFileName(MultipartFile file) {
        return dir + UUID.randomUUID() + "-" + file.getOriginalFilename();
    }

    private String getFileUrl(String filename) {
        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                .bucket(bucket)
                .key(filename)
                .build();

        return String.valueOf(s3Client.utilities().getUrl(getUrlRequest));
    }

}