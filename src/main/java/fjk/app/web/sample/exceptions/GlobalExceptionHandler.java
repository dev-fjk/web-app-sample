package fjk.app.web.sample.exceptions;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import fjk.app.web.sample.exceptions.custom.CustomValidationException;
import fjk.app.web.sample.exceptions.custom.ResourceNotFoundException;
import fjk.app.web.sample.models.presentation.response.errors.ApiErrorResponse;
import fjk.app.web.sample.models.presentation.response.errors.ValidaitonErrorResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * グローバル例外ハンドラー
 *
 * <p>アプリケーション全体で発生する例外を一元的にハンドリングし、 適切なHTTPレスポンスに変換します。
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * @Validアノテーションによるバリデーションエラーのハンドリング
   *
   * @param ex MethodArgumentNotValidException
   * @return バリデーションエラーの詳細を含むResponseEntity
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidaitonErrorResponse> handleValidationException(
      final MethodArgumentNotValidException ex) {
    log.warn("Validation error occurred: {}", ex.getMessage());

    final List<ValidaitonErrorResponse.ErrorField> errors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(
                fieldError ->
                    ValidaitonErrorResponse.ErrorField.builder()
                        .field(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .build())
            .collect(Collectors.toList());

    final ValidaitonErrorResponse response =
        ValidaitonErrorResponse.builder().details(errors).build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  /**
   * カスタムバリデーション例外のハンドリング
   *
   * @param ex CustomValidationException
   * @return バリデーションエラーの詳細を含むResponseEntity
   */
  @ExceptionHandler(CustomValidationException.class)
  public ResponseEntity<ValidaitonErrorResponse> handleCustomValidationException(
      final CustomValidationException ex) {
    log.warn("Custom validation error occurred: {}", ex.getMessage());

    final List<ValidaitonErrorResponse.ErrorField> errors =
        ex.getErrors().stream()
            .map(
                error ->
                    ValidaitonErrorResponse.ErrorField.builder()
                        .field(error.getField())
                        .message(error.getMessage())
                        .build())
            .collect(Collectors.toList());

    final ValidaitonErrorResponse response =
        ValidaitonErrorResponse.builder().details(errors).build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  /**
   * メソッド引数の型不一致エラーのハンドリング（例: 無効なパス変数の型）
   *
   * @param ex MethodArgumentTypeMismatchException
   * @return エラーの詳細を含むResponseEntity
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ValidaitonErrorResponse> handleTypeMismatchException(
      final MethodArgumentTypeMismatchException ex) {
    log.warn("Type mismatch error occurred: {}", ex.getMessage());

    final String fieldName = ex.getName();
    final String message =
        String.format(
            "パラメータ '%s' の値が不正です。期待される型: %s", fieldName, ex.getRequiredType().getSimpleName());

    final List<ValidaitonErrorResponse.ErrorField> errors =
        List.of(
            ValidaitonErrorResponse.ErrorField.builder().field(fieldName).message(message).build());

    final ValidaitonErrorResponse response =
        ValidaitonErrorResponse.builder().details(errors).build();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  /**
   * リソース未検出例外のハンドリング
   *
   * @param ex ResourceNotFoundException
   * @return エラーの詳細を含むResponseEntity
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(
      final ResourceNotFoundException ex) {
    log.warn("Resource not found: {}", ex.getMessage());

    final ApiErrorResponse response =
        ApiErrorResponse.builder()
            .code(HttpStatus.NOT_FOUND.value())
            .message(HttpStatus.NOT_FOUND.getReasonPhrase())
            .detail(ex.getMessage())
            .build();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  /**
   * その他すべての例外のハンドリング
   *
   * @param ex Exception
   * @return エラーの詳細を含むResponseEntity
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleGeneralException(final Exception ex) {
    log.error("Unexpected error occurred", ex);

    final ApiErrorResponse response =
        ApiErrorResponse.builder()
            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .detail("予期しないエラーが発生しました。サポートにお問い合わせください。")
            .build();

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}
