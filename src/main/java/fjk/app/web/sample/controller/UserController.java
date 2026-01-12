package fjk.app.web.sample.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fjk.app.web.sample.components.BeanValidationErrorThrower;
import fjk.app.web.sample.models.presentation.query.UserListQuery;
import fjk.app.web.sample.models.presentation.response.users.UserListResponse;
import fjk.app.web.sample.models.presentation.response.users.UserResponse;
import fjk.app.web.sample.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * ユーザーコントローラー
 *
 * <p>ユーザーに関するAPIエンドポイントを提供
 */
@Tag(name = "Users", description = "ユーザー管理API")
@Validated
@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final BeanValidationErrorThrower beanValidationErrorThrower;

  /**
   * ユーザー取得
   *
   * @param id
   * @return ユーザ情報
   */
  @GetMapping("/{id}")
  @Operation(summary = "ユーザー取得", description = "ユーザーを取得します")
  public ResponseEntity<UserResponse> find(@PathVariable("id") Long id) {
    return ResponseEntity.ok(userService.findById(id));
  }

  /**
   * ユーザー一覧検索
   *
   * @param query 検索条件
   * @return ユーザーリスト
   */
  @GetMapping
  @Operation(summary = "ユーザーリスト検索", description = "条件に一致するユーザーを検索します")
  public ResponseEntity<UserListResponse> search(
      @ModelAttribute @Validated final UserListQuery query, BindingResult bindingResult) {
    beanValidationErrorThrower.throwIfHasErrors(bindingResult);

    final UserListResponse response = userService.searchUsers(query);
    return ResponseEntity.ok(response);
  }
}
