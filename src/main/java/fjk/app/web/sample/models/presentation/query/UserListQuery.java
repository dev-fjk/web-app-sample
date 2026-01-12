package fjk.app.web.sample.models.presentation.query;

import org.springdoc.core.annotations.ParameterObject;
import fjk.app.web.sample.models.presentation.common.PagingRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ユーザー一覧検索クエリパラメータ
 *
 * <p>ユーザー一覧取得APIのクエリパラメータ
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "ユーザー一覧検索パラメータ")
@ParameterObject
public class UserListQuery extends PagingRequest {

  @Schema(description = "ユーザーID", example = "1")
  private Long id;

  @Schema(description = "ユーザー名(部分一致)", example = "山田")
  private String userName;

  @Schema(description = "メールアドレス(部分一致)", example = "yamada@example.com")
  private String email;

  @Schema(description = "電話番号", example = "090")
  private String phoneNumber;
}
