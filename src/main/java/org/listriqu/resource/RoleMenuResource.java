package org.listriqu.resource;

import java.util.List;

import org.listriqu.dto.AssignRoleMenuRequest;
import org.listriqu.dto.MenuDTO;
import org.listriqu.response.ApiResponse;
import org.listriqu.response.ErrorResponse;
import org.listriqu.response.SuccesResponse;
import org.listriqu.service.RoleMenuService;
import org.listriqu.service.UserAccessService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class RoleMenuResource {

    @Inject
    RoleMenuService roleMenuService;
    @Inject
    UserAccessService userAccessService;

    @GET
    @Path("/role/{role_id}/menu")
    @RolesAllowed({ "SUPERADMIN", "USER", "ADMIN" })
    @SuppressWarnings("CallToPrintStackTrace")
    public Response getRoleMenu(@PathParam("role_id") Integer roleId) {
        try {
            List<MenuDTO> menus = roleMenuService.getMenusByRole(roleId);
            return Response.ok(new ApiResponse("success", menus)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Internal server error"))
                    .build();
        }
    }

    @POST
    @Path("/role-menu")
    @RolesAllowed({ "SUPERADMIN", "ADMIN" })
    @Transactional
    @SuppressWarnings("CallToPrintStackTrace")
    public Response assignRoleMenu(AssignRoleMenuRequest request) {
        try {
            roleMenuService.assignRoleMenu(request.role_id, request.menu_ids);
            return Response.ok().entity(
                    new SuccesResponse("Success", "Role menu assigned successfully")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Internal server error"))
                    .build();
        }
    }

    @GET
    @Path("/role/{user_id}")
    @RolesAllowed({ "SUPERADMIN", "ADMIN" })
    public Response getUserAccess(@PathParam("user_id") Integer userId) {
        var data = userAccessService.getUserAccess(userId);
        return Response.ok(new ApiResponse("success", data)).build();
    }

    @GET
    @Path("/role")
    @RolesAllowed({ "SUPERADMIN", "ADMIN" })
    public Response getAllRolesWithUsers() {
        var data = userAccessService.getAllRolesWithUsers();
        return Response.ok(new ApiResponse("success", data)).build();
    }
}
