package io.quarkus.workshop.superheroes.villain;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.TEXT_PLAIN;


@Path("/api/villains")
public class VillainResource {

    @Inject
    Logger logger;
    @Inject
    VillainService villainService;


    @GET
    @Path("/random")
//    @Produces(TEXT_PLAIN)
    public RestResponse<Villain> randomVillain() {
        Villain randomVillain = villainService.findRandomVillain();
        logger.infof("Random villain found: %s", randomVillain);
        return RestResponse.ok(randomVillain);
    }


    @GET
    public RestResponse<List<Villain>> getAllVillains() {
        List<Villain> allVillains = villainService.findAllVillains();
        logger.debug("Total number of villains " + allVillains.size());
        return RestResponse.ok(allVillains);
    }


    @GET
    @Path("/{id}")
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
    public RestResponse<Void> createVillain(@Valid Villain villain, @Context UriInfo uriInfo) {
        villain = villainService.persistVillain(villain);
        UriBuilder builder =  uriInfo.getAbsolutePathBuilder().path(Long.toString(villain.id));
        logger.debug("New villain created with URI " + builder.build().toString());
        return RestResponse.created(builder.build());
    }

    @PUT
    public RestResponse<Villain> updateVillain(@Valid Villain villain) {
        villain = villainService.updateVillain(villain);
        logger.debug("Villain updated with new valued " + villain);
        return RestResponse.ok(villain);
    }

    @DELETE
    @Path("/{id}")
    public RestResponse<Void> deleteVillain(@RestPath Long id) {
        villainService.deleteVillain(id);
        logger.debug("Villain deleted with " + id);
        return RestResponse.noContent();
    }

    @GET
    @Path("/hello")
    @Produces(TEXT_PLAIN)
    public String hello() {
        return "Hello Villain Resource";
    }
}
