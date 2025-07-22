package io.quarkus.workshop.superheroes.villain;

import io.quarkus.workshop.superheroes.comum.GeneralOperations;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;

import java.net.URI;
import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;


public interface VillainResourceOperation extends GeneralOperations {

    @Operation(summary = "Returns a random villain")
    @APIResponse(
        responseCode = "200",
        content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Villain.class, required = true))
    )
    RestResponse<Villain> randomVillain();

    @Operation(summary = "Returns all the villains from the database")
    @APIResponse(
        responseCode = "200",
        content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Villain.class, type = SchemaType.ARRAY))
    )
    RestResponse<List<Villain>> getAllVillains();

    @Operation(summary = "Returns a villain for a given identifier")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Villain.class)))
    @APIResponse(responseCode = "404", description = "The villain is not found for a given identifier")
    RestResponse<Villain> getVillain(@RestPath Long id);

    @Operation(summary = "Creates a valid villain")
    @APIResponse(
        responseCode = "201",
        description = "The URI of the created villain",
        content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = URI.class))
    )
    RestResponse<Void> createVillain(@Valid Villain villain, @Context UriInfo uriInfo);

    @Operation(summary = "Updates an exiting  villain")
    @APIResponse(
        responseCode = "200",
        description = "The updated villain",
        content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Villain.class))
    )
    RestResponse<Villain> updateVillain(@Valid Villain villain);

    @Operation(summary = "Deletes an exiting villain")
    @APIResponse(responseCode = "204")
    RestResponse<Void> deleteVillain(@RestPath Long id);

    String hello();
}
