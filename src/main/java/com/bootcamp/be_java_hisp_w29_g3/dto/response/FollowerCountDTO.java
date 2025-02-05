package com.bootcamp.be_java_hisp_w29_g3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowerCountDTO {
    private Integer userId;
    private String userName;
    private Long followersCount;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FollowerCountDTO that = (FollowerCountDTO) o;
        return Objects.equals(userId, that.userId) && Objects.equals(userName, that.userName) && Objects.equals(followersCount, that.followersCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, followersCount);
    }
}
