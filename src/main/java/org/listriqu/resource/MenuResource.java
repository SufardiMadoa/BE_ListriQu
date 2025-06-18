package org.listriqu.resource;

import org.listriqu.dto.MenuRequest;
import org.listriqu.response.ApiResponse;
import org.listriqu.response.ErrorResponse;
import org.listriqu.response.SuccesResponse;
import org.listriqu.service.MenuService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
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

@Path("/api/v1/menu")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({  "SUPERADMIN", "USER", "ADMIN" })
public class MenuResource {

    @Inject
    MenuService menuService;

    @GET
    @SuppressWarnings("CallToPrintStackTrace")
    public Response getAll() {
        try {
            Object menu = menuService.getAllMenus();
            return Response.ok(new ApiResponse("success", menu)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Internal server error"))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    @SuppressWarnings("CallToPrintStackTrace")
    public Response getById(@PathParam("id") Integer id) {
         try {
        Object menu = menuService.getMenuById(id);
        return Response.ok(new ApiResponse("success", menu)).build();
         } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Internal server error"))
                    .build();
        }
    }

    @POST
    @SuppressWarnings("CallToPrintStackTrace")
    public Response create(MenuRequest request) {
         try {
        menuService.createMenu(request);
        return Response.ok(new SuccesResponse("Success", "Role menu assigned successfully")).build();
      } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Internal server error"))
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @SuppressWarnings("CallToPrintStackTrace")
    public Response update(@PathParam("id") Integer id, MenuRequest request) {
         try {
        menuService.updateMenu(id, request);
        return Response.ok().build();
  } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Internal server error"))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @SuppressWarnings("CallToPrintStackTrace")
    public Response delete(@PathParam("id") Integer id) {
         try {
        menuService.deleteMenu(id);
        return Response.ok(new SuccesResponse("Success", "Menu deleted successfully")).build();
      } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Internal server error"))
                    .build();
        }
    }
}
