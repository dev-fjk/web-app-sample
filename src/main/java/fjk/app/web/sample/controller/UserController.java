package fjk.app.web.sample.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController {

  @GetMapping
  public ResponseEntity<String> test() {
    return ResponseEntity.ok("test");
  }
}
