package io.quarkus.workshop.superheroes.villain;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.MediaType.TEXT_PLAIN;


@Path("/api/villains")
@Tag( name = "Villains")
public class VillainResource implements VillainResourceOperation {

    @Inject
    Logger logger;
    @Inject
    VillainService villainService;


    @Operation(summary = "Returns a random villain")
    @GET
    @Path("/random")
    @APIResponse(
        responseCode = "200",
        content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Villain.class, required = true))
    )
    @Override
    public RestResponse<Villain> randomVillain() {
        Villain randomVillain = villainService.findRandomVillain();
        logger.infof("Random villain found: %s", randomVillain);
        return RestResponse.ok(randomVillain);
    }


    @GET
    @Override
    public RestResponse<List<Villain>> getAllVillains() {
        List<Villain> allVillains = villainService.findAllVillains();
        logger.debug("Total number of villains " + allVillains.size());
        return RestResponse.ok(allVillains);
    }


    @Operation(summary = "Returns a villain for a given identifier")
    @GET
    @Path("/{id}")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Villain.class)))
    @APIResponse(responseCode = "204", description = "The villain is not found for a given identifier")
    public RestResponse<Villain> getVillain(@RestPath Long id) {
        Villain villain = villainService.findVillainById(id);
        if (villain != null) {
            logger.debug("Found villain " + villain);
            return RestResponse.ok(villain);
        }else {
            logger.debug("No villain found with id " + id);
            return  RestResponse.notFound();
        }
    }


    @POST
    @Override
    public RestResponse<Void> createVillain(@Valid Villain villain, @Context UriInfo uriInfo) {
        villain = villainService.persistVillain(villain);
        UriBuilder builder =  uriInfo.getAbsolutePathBuilder().path(Long.toString(villain.id));
        logger.debug("New villain created with URI " + builder.build().toString());
        return RestResponse.created(builder.build());
    }

    @PUT
    @Override
    public RestResponse<Villain> updateVillain(@Valid Villain villain) {
        villain = villainService.updateVillain(villain);
        logger.debug("Villain updated with new valued " + villain);
        return RestResponse.ok(villain);
    }

    @DELETE
    @Path("/{id}")
    @Override
    public RestResponse<Void> deleteVillain(@RestPath Long id) {
        villainService.deleteVillain(id);
        logger.debug("Villain deleted with " + id);
        return RestResponse.noContent();
    }

    @GET
    @Path("/hello")
    @Produces(TEXT_PLAIN)
    @Override
    public String hello() {
        return "Hello Villain Resource VillainResource";
    }
}
