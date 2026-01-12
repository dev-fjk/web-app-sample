package fjk.app.web.sample.components;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import fjk.app.web.sample.exceptions.custom.CustomValidationException.CustomValidationExceptionBuilder;

@Component
public class BeanValidationErrorThrower {

  /**
   * Bean Validation のエラーを CustomValidationException に変換して throw します。
   *
   * @param bindingResult
   */
  public void throwIfHasErrors(BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      final CustomValidationExceptionBuilder builder = new CustomValidationExceptionBuilder();
      bindingResult.getFieldErrors().stream()
          .forEach(error -> builder.addError(error.getField(), error.getDefaultMessage()));
      throw builder.build();
    }
  }
}
