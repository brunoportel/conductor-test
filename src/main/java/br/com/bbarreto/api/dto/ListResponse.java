package br.com.bbarreto.api.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(name = "ListResponse")
@Data
@AllArgsConstructor
public class ListResponse<T> {
	private List<T> list;
}
