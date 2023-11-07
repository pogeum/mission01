package com.korea.test.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비번 확인 필수.")
    private String password2;

    @NotEmpty(message = "이메일 입력 필수.")
    @Email//해당 속성의 값이 이메일형식과 일치하는지를 검증한다.
    private String email;
}
