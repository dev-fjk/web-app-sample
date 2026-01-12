package fjk.app.web.sample.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import fjk.app.web.sample.mapper.UserMapper;
import fjk.app.web.sample.models.infra.dto.UserDbSearchDto;
import fjk.app.web.sample.models.infra.entity.User;
import fjk.app.web.sample.models.presentation.query.UserListQuery;
import fjk.app.web.sample.models.presentation.response.common.PaginationView;
import fjk.app.web.sample.models.presentation.response.users.UserListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * ユーザーサービス
 *
 * <p>ユーザーに関するビジネスロジックを提供
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

  private final UserMapper userMapper;

  /**
   * ユーザー一覧を検索
   *
   * @param query 検索条件
   * @return ユーザーリストレスポンス
   */
  public UserListResponse searchUsers(final UserListQuery query) {
    log.info("Searching users with query: {}", query);

    // Presentation層のQueryをInfra層のDTOに変換
    final UserDbSearchDto dto = convertToDbSearchDto(query);

    // ユーザー一覧を取得
    final List<User> users = userMapper.findAll(dto);
    log.debug("Found {} users", users.size());

    // 総件数を取得
    final int totalCount = userMapper.count(dto);
    log.debug("Total count: {}", totalCount);

    // ページング情報を作成
    final PaginationView pagination =
        PaginationView.of(query.getPage(), query.getPageSize(), totalCount, users.size());

    // エンティティをビューモデルに変換
    final List<UserListResponse.UserView> userViews =
        users.stream().map(this::convertToUserView).collect(Collectors.toList());

    return UserListResponse.builder().pagination(pagination).data(userViews).build();
  }

  /**
   * Presentation層のQueryをInfra層のDTOに変換
   *
   * @param query Presentation層のクエリ
   * @return Infra層のDTO
   */
  private UserDbSearchDto convertToDbSearchDto(final UserListQuery query) {
    return UserDbSearchDto.builder()
        .id(query.getId())
        .userName(query.getUserName())
        .email(query.getEmail())
        .phoneNumber(query.getPhoneNumber())
        .limit(query.getLimit())
        .offset(query.getOffset())
        .build();
  }

  /**
   * ドメインモデルをビューモデルに変換
   *
   * @param user ユーザーエンティティ
   * @return ユーザービュー
   */
  private UserListResponse.UserView convertToUserView(final User user) {
    return UserListResponse.UserView.builder()
        .id(user.getId())
        .userName(user.getName())
        .email(user.getEmail())
        .phoneNumber(user.getPhoneNumber())
        .createdAt(user.getCreatedAt())
        .updatedAt(user.getUpdatedAt())
        .build();
  }
}
