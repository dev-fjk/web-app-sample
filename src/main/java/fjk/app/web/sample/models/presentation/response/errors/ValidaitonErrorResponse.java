package fjk.app.web.sample.models.presentation.response.errors;

import java.util.List;
import org.springframework.http.HttpStatus;
import lombok.Builder;
import lombok.Data;

/**
 * バリデーションエラーのレスポンス
 *
 * <p>フィールドごとのエラー詳細を含むレスポンス形式
 */
@Data
@Builder
public class ValidaitonErrorResponse {

  @Builder.Default private Integer code = HttpStatus.BAD_REQUEST.value();

  @Builder.Default private String message = HttpStatus.BAD_REQUEST.getReasonPhrase();

  private List<ErrorField> details;

  /**
   * エラーフィールド情報
   *
   * <p>フィールド名とエラーメッセージのペア
   */
  @Data
  @Builder
  public static class ErrorField {
    private String field;
    private String message;
  }
}
