package org.listriqu.resource;

import org.listriqu.dto.MasterUnitRequest;
import org.listriqu.response.ErrorResponse;
import org.listriqu.service.MasterUnitService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/master/units")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({  "SUPERADMIN","USER", "ADMIN" })
public class UnitResource {
 
    @Inject
    MasterUnitService unitService;

    @POST
    @SuppressWarnings("CallToPrintStackTrace")
    public Response createUnit(MasterUnitRequest request) {
        try{
        unitService.create(request);
        return Response.ok("Unit created successfully").build();
         } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Internal server error"))
                    .build();
        }
    }

    @GET
    @SuppressWarnings("CallToPrintStackTrace")
    public Response getUnits() {
           try{
        return Response.ok(unitService.getAllUnits()).build();
         } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Internal server error"))
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateUnit(@PathParam("id") Integer id, MasterUnitRequest request) {
           try{
        unitService.updateUnit(id, request);
        return Response.ok("Unit berhasil diupdate").build();
         } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Internal server error"))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteUnit(@PathParam("id") Integer id) {
           try{
        unitService.deleteUnit(id);
        return Response.ok("Unit berhasil dihapus").build();
         } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Internal server error"))
                    .build();
        }
    }
}
