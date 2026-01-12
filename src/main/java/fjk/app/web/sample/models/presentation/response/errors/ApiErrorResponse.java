package fjk.app.web.sample.models.presentation.response.errors;

import lombok.Builder;
import lombok.Data;

/**
 * APIエラーレスポンス
 *
 * <p>一般的なエラー情報を含むレスポンス形式
 */
@Data
@Builder
public class ApiErrorResponse {

  private Integer code;

  private String message;

  private String detail;
}
