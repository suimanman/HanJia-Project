package com.example.hanjiaprojectbackend.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "用户实体类")
public class User {
    @Schema(description = "ID")
    public Integer id;
    @Schema(description = "用户名")
    public String username;
    @Schema(description = "邮箱")
    public String email;
    @Schema(description = "密码")
    public String password;
}
