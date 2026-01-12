package fjk.app.web.sample.models.infra.dto;

import lombok.Builder;
import lombok.Data;

/**
 * ユーザー検索用DTO
 *
 * <p>DB検索時のパラメータを保持
 */
@Data
@Builder
public class UserDbSearchDto {

  /** ユーザーID */
  private Long id;

  /** ユーザー名（部分一致） */
  private String userName;

  /** メールアドレス（部分一致） */
  private String email;

  /** 電話番号（部分一致） */
  private String phoneNumber;

  /** リミット */
  private Integer limit;

  /** オフセット */
  private Integer offset;
}
