package fjk.app.web.sample.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import fjk.app.web.sample.models.domain.dto.PagingDto;
import fjk.app.web.sample.models.domain.result.UserListResult;
import fjk.app.web.sample.models.infra.dto.UserDbSearchDto;
import fjk.app.web.sample.models.presentation.query.UserListQuery;
import fjk.app.web.sample.models.presentation.response.common.Pagination;
import fjk.app.web.sample.models.presentation.response.users.UserListResponse;
import fjk.app.web.sample.models.presentation.response.users.UserListResponse.UserView;
import fjk.app.web.sample.repository.UserRepository;
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

  private final UserRepository userRepository;

  /**
   * ユーザー一覧を検索
   *
   * @param query 検索条件
   * @return ユーザーリストレスポンス
   */
  public UserListResponse searchUsers(final UserListQuery query) {
    final PagingDto pagingDto = PagingDto.of(query.getPage(), query.getPageSize());
    final UserDbSearchDto dto = UserDbSearchDto.create(query);

    final UserListResult userListResult = userRepository.search(pagingDto, dto);

    final Pagination pagination =
        Pagination.of(
            pagingDto.getPage(),
            pagingDto.getPageSize(),
            userListResult.getTotalCount(),
            userListResult.getSize());

    final List<UserView> data =
        userListResult.getUsers().stream().map(UserView::fromEntity).toList();

    return UserListResponse.builder().pagination(pagination).data(data).build();
  }
}
