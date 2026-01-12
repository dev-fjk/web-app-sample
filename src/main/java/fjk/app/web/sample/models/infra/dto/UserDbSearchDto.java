package fjk.app.web.sample.models.infra.dto;

import fjk.app.web.sample.models.presentation.query.UserListQuery;
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

  /**
   * queryからUserDbSearchDtoを作成する
   *
   * @param query {@link UserListQuery}
   * @return UserDbSearchDto
   */
  public static UserDbSearchDto create(UserListQuery query) {
    return UserDbSearchDto.builder()
        .id(query.getId())
        .userName(query.getUserName())
        .email(query.getEmail())
        .phoneNumber(query.getPhoneNumber())
        .build();
  }
}
