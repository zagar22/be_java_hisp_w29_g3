package com.bootcamp.be_java_hisp_w29_g3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowerCountDTO {
    private Integer user_id;
    private String user_name;
    private Long followers_count;


}
