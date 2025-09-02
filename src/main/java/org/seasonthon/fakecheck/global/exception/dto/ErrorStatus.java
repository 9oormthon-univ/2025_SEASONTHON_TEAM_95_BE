package org.seasonthon.fakecheck.global.exception.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus {

    // COMMON
    BAD_REQUEST      (400, "ERROR - 잘못된 요청입니다."),
    NOT_FOUND        (404, "ERROR - 찾을 수 없습니다."),
    DUPLICATE        (409, "ERROR - 중복 되었습니다."),
    FORBIDDEN_MODIFY (403, "ERROR - 수정 권한이 없습니다."),
    FORBIDDEN_DELETE (403, "ERROR - 삭제 권한이 없습니다."),

    // AWS
    INVALID_FILE_EXTENSION (400, "ERROR - 지원하지 않는 파일 확장자입니다."),
    NOT_FOUND_FILE         (404, "ERROR - 파일이 존재하지 않습니다."),
    AWS_S3_ERROR           (500, "ERROR - AWS S3 내부 에러"),
    FAILED_TO_UPLOAD_FILE  (500, "ERROR - 파일 업로드에 실패하였습니다."),

    FORBIDDEN_ERROR        (403, "ERROR - 접근 권한이 없습니다."),
    INTERNAL_SERVER_ERROR  (500, "ERROR - 서버 내부 에러"),
    ;

    private final int statusCode;
    private final String message;

}