package br.com.bbarreto.api.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "IdRequest")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdDTO implements Serializable {

	private static final long serialVersionUID = -6984770067599267677L;
	private Long id;
}
