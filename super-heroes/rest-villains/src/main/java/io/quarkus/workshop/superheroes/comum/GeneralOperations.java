package io.quarkus.workshop.superheroes.comum;


import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Recuperado com sucesso"),
        @APIResponse(responseCode = "400", description = "Bad Request - Requisição Mal Formada", content = {
                @Content(schema = @Schema(implementation = InformacaoDoErro.class), mediaType = "application/json",
                        examples = {
                                @ExampleObject(
                                        value = "{\"dateTime\":\"19/09/2023 20:28:01\",\"httpStatus\":400,\"mensagemDeErro\":\"Mensagem de erro Generica\",\"url\":\"/recursoComErro\"}"
                                )
                        })
        }),
        @APIResponse(responseCode = "500", description = "Internal Server Error - Erro interno no servidor ao tentar processar requisicao", content = {
                @Content(schema = @Schema(implementation = InformacaoDoErro.class), mediaType = "application/json",
                        examples = {
                                @ExampleObject(
                                        value = "{\"dateTime\":\"19/09/2023 20:28:01\",\"httpStatus\":500,\"mensagemDeErro\":\"Erro ao Realizar a Requisição\",\"url\":\"/recursoComErro\"}"
                                )
                        })
        })
})
public interface GeneralOperations {
}
