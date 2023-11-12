package com.shimada.linksv4.requests.link;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkRequest {

    private String url;
    private String name;
}
