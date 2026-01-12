package fjk.app.web.sample.models.presentation.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * ページングリクエスト
 *
 * <p>ページング機能を持つAPIのリクエストパラメータの基底クラス
 */
@Data
public abstract class PagingRequest {

  @Schema(description = "ページ番号（1から開始）", example = "1", defaultValue = "1")
  @Min(value = 1, message = "ページ番号は1以上である必要があります")
  private Integer page = 1;

  @Schema(description = "ページサイズ", example = "10", defaultValue = "10")
  @Min(value = 1, message = "ページサイズは1以上である必要があります")
  @Max(value = 100, message = "ページサイズは100以下である必要があります")
  private Integer pageSize = 10;
}
