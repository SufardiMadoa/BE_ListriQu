package org.listriqu.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.listriqu.dto.MenuRequest;
import org.listriqu.service.MenuService;

@Path("/api/v1/menu")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({ "USER", "ADMIN" })
public class MenuResource {

    @Inject
    MenuService menuService;

    @GET

    public Response getAll() {
        return Response.ok(menuService.getAllMenus()).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(menuService.getMenuById(id)).build();
    }

    @POST
    public Response create(MenuRequest request) {
        menuService.createMenu(request);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, MenuRequest request) {
        menuService.updateMenu(id, request);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        menuService.deleteMenu(id);
        return Response.ok().build();
    }
}
