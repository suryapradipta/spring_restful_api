package com.ksatria.spring_restful_api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// becase only attributes: errors and data, and the attributes value is dynamic.
// we can set as generic type
public class WebResponse<T> {

    private T data;

    private String errors;

}
