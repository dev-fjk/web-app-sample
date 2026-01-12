package fjk.app.web.sample.components;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import fjk.app.web.sample.exceptions.custom.CustomValidationException;

@DisplayName("BeanValidationErrorThrowerのテスト")
class BeanValidationErrorThrowerTest {

  private BeanValidationErrorThrower beanValidationErrorThrower;
  private BindingResult bindingResult;

  @BeforeEach
  void setUp() {
    beanValidationErrorThrower = new BeanValidationErrorThrower();
    bindingResult = mock(BindingResult.class);
  }

  @Test
  @DisplayName("バリデーションエラーがない場合 - 例外がスローされない")
  void testThrowIfHasErrors_NoErrors() {
    // Given
    when(bindingResult.hasErrors()).thenReturn(false);

    // When & Then
    beanValidationErrorThrower.throwIfHasErrors(bindingResult);
    // 例外がスローされないことを確認
  }

  @Test
  @DisplayName("バリデーションエラーがある場合 - CustomValidationExceptionがスローされる")
  void testThrowIfHasErrors_WithErrors() {
    // Given
    when(bindingResult.hasErrors()).thenReturn(true);
    final FieldError fieldError = new FieldError("userListQuery", "page", "ページ番号は1以上である必要があります");
    when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

    // When & Then
    assertThatThrownBy(() -> beanValidationErrorThrower.throwIfHasErrors(bindingResult))
        .isInstanceOf(CustomValidationException.class);
  }

  @Test
  @DisplayName("バリデーションエラーが複数ある場合")
  void testThrowIfHasErrors_MultipleErrors() {
    // Given
    when(bindingResult.hasErrors()).thenReturn(true);
    final FieldError fieldError1 = new FieldError("userListQuery", "page", "ページ番号は1以上である必要があります");
    final FieldError fieldError2 =
        new FieldError("userListQuery", "pageSize", "ページサイズは1以上100以下である必要があります");
    when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

    // When & Then
    assertThatThrownBy(() -> beanValidationErrorThrower.throwIfHasErrors(bindingResult))
        .isInstanceOf(CustomValidationException.class);
  }
}
