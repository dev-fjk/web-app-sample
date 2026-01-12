package fjk.app.web.sample.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import fjk.app.web.sample.components.BeanValidationErrorThrower;
import fjk.app.web.sample.models.presentation.query.UserListQuery;
import fjk.app.web.sample.models.presentation.response.users.UserListResponse;
import fjk.app.web.sample.models.presentation.response.users.UserResponse;
import fjk.app.web.sample.service.UserService;

@WebMvcTest(UserController.class)
@DisplayName("UserControllerのテスト")
class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private UserService userService;

  @MockitoBean private BeanValidationErrorThrower beanValidationErrorThrower;

  private UserResponse userResponse;
  private UserListResponse userListResponse;

  @BeforeEach
  void setUp() {
    final LocalDateTime now = LocalDateTime.now();
    userResponse =
        UserResponse.builder()
            .id(1L)
            .userName("山田太郎")
            .email("yamada@example.com")
            .phoneNumber("090-1234-5678")
            .createdAt(now)
            .updatedAt(now)
            .build();
  }

  @Test
  @DisplayName("ユーザー取得API - 正常系")
  void testFindUser_Success() throws Exception {
    // Given
    when(userService.findById(1L)).thenReturn(userResponse);

    // When & Then
    mockMvc
        .perform(get("/v1/users/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.userName").value("山田太郎"))
        .andExpect(jsonPath("$.email").value("yamada@example.com"))
        .andExpect(jsonPath("$.phoneNumber").value("090-1234-5678"));
  }

  @Test
  @DisplayName("ユーザー一覧検索API - 正常系")
  void testSearchUsers_Success() throws Exception {
    // Given
    userListResponse = UserListResponse.builder().data(List.of()).build();
    when(userService.searchUsers(any(UserListQuery.class))).thenReturn(userListResponse);

    // When & Then
    mockMvc
        .perform(get("/v1/users").param("page", "1").param("pageSize", "10"))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("ユーザー一覧検索API - クエリパラメータあり")
  void testSearchUsers_WithQueryParams() throws Exception {
    // Given
    userListResponse = UserListResponse.builder().data(List.of()).build();
    when(userService.searchUsers(any(UserListQuery.class))).thenReturn(userListResponse);

    // When & Then
    mockMvc
        .perform(
            get("/v1/users")
                .param("page", "1")
                .param("pageSize", "10")
                .param("userName", "山田")
                .param("email", "example"))
        .andExpect(status().isOk());
  }
}
