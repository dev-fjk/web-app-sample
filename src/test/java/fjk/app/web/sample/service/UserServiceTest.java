package fjk.app.web.sample.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import fjk.app.web.sample.exceptions.custom.ResourceNotFoundException;
import fjk.app.web.sample.mapper.UserMapper;
import fjk.app.web.sample.models.domain.dto.PagingDto;
import fjk.app.web.sample.models.domain.result.UserListResult;
import fjk.app.web.sample.models.infra.dto.UserDbSearchDto;
import fjk.app.web.sample.models.infra.entity.User;
import fjk.app.web.sample.models.presentation.query.UserListQuery;
import fjk.app.web.sample.models.presentation.response.users.UserListResponse;
import fjk.app.web.sample.models.presentation.response.users.UserResponse;
import fjk.app.web.sample.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserServiceのテスト")
class UserServiceTest {

  @Mock private UserRepository userRepository;

  @Mock private UserMapper userMapper;

  @InjectMocks private UserService userService;

  private User user;
  private LocalDateTime now;

  @BeforeEach
  void setUp() {
    now = LocalDateTime.now();
    user = new User();
    user.setId(1L);
    user.setName("山田太郎");
    user.setEmail("yamada@example.com");
    user.setPhoneNumber("090-1234-5678");
    user.setCreatedAt(now);
    user.setUpdatedAt(now);
  }

  @Test
  @DisplayName("ユーザー取得 - 正常系")
  void testFindById_Success() {
    // Given
    when(userMapper.findById(1L)).thenReturn(user);

    // When
    final UserResponse result = userService.findById(1L);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(1L);
    assertThat(result.getUserName()).isEqualTo("山田太郎");
    assertThat(result.getEmail()).isEqualTo("yamada@example.com");
    assertThat(result.getPhoneNumber()).isEqualTo("090-1234-5678");
  }

  @Test
  @DisplayName("ユーザー取得 - ユーザーが見つからない場合")
  void testFindById_NotFound() {
    // Given
    when(userMapper.findById(999L)).thenReturn(null);

    // When & Then
    assertThatThrownBy(() -> userService.findById(999L))
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessage("User Not Found.");
  }

  @Test
  @DisplayName("ユーザー一覧検索 - 正常系")
  void testSearchUsers_Success() {
    // Given
    final UserListQuery query = new UserListQuery();
    query.setPage(1);
    query.setPageSize(10);

    final UserListResult userListResult =
        UserListResult.builder().totalCount(1).users(List.of(user)).build();

    when(userRepository.search(any(PagingDto.class), any(UserDbSearchDto.class)))
        .thenReturn(userListResult);

    // When
    final UserListResponse result = userService.searchUsers(query);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getData()).hasSize(1);
    assertThat(result.getPagination()).isNotNull();
    assertThat(result.getPagination().getTotalCount()).isEqualTo(1);
    assertThat(result.getPagination().getPage()).isEqualTo(1);
    assertThat(result.getPagination().getPageSize()).isEqualTo(10);
  }

  @Test
  @DisplayName("ユーザー一覧検索 - 検索結果が空の場合")
  void testSearchUsers_Empty() {
    // Given
    final UserListQuery query = new UserListQuery();
    query.setPage(1);
    query.setPageSize(10);

    final UserListResult userListResult = UserListResult.empty();

    when(userRepository.search(any(PagingDto.class), any(UserDbSearchDto.class)))
        .thenReturn(userListResult);

    // When
    final UserListResponse result = userService.searchUsers(query);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getData()).isEmpty();
    assertThat(result.getPagination().getTotalCount()).isEqualTo(0);
  }

  @Test
  @DisplayName("ユーザー一覧検索 - 検索条件あり")
  void testSearchUsers_WithSearchConditions() {
    // Given
    final UserListQuery query = new UserListQuery();
    query.setPage(1);
    query.setPageSize(10);
    query.setUserName("山田");
    query.setEmail("example");

    final UserListResult userListResult =
        UserListResult.builder().totalCount(1).users(List.of(user)).build();

    when(userRepository.search(any(PagingDto.class), any(UserDbSearchDto.class)))
        .thenReturn(userListResult);

    // When
    final UserListResponse result = userService.searchUsers(query);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getData()).hasSize(1);
  }
}
