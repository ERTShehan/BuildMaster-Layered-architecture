package com.assignment.buildmaster.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClientDto {
    private String id;
    private String name;
    private String address;
    private String phoneNo;
    private String email;
}
