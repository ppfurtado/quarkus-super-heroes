package io.quarkus.workshop.superheroes.comum;


import org.eclipse.microprofile.openapi.annotations.media.Schema;

public record InformacaoDoErro(
        @Schema(description = "Status Http", example = "500")
        Integer httpStatus,

        @Schema(description = "Recurso que Gerou o erro", example = "/recursoGenerico")
        String url,

        @Schema(description = "Mensagem de erro capturada pela Exception", example = "Erro Interno no Servidor")
        String mensagemDeErro) {
}
