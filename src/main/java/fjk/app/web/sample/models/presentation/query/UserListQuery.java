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

  @Schema(description = "ユーザーID")
  private Long id;

  @Schema(description = "ユーザー名(部分一致)")
  private String userName;

  @Schema(description = "メールアドレス(部分一致)")
  private String email;

  @Schema(description = "電話番号")
  private String phoneNumber;
}
