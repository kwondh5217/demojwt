package com.example.demojwt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    // @JsonProperty 직렬화, 역직렬화 과정에서 필드 또는 메서드를 지정하는 어노테이션
    // access = JsonProperty.Access.WRITE_ONLY 로 되어있는 이유는 사용자의 패스워드는 중요한 정보기 때문에
    // 요청 본문에서 dto 클래스로 바인딩(deserialize)은 허용하지만 응답 본문에선 포함하지 않게 하기 위함(serialize)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    private String nickname;
}
