package org.listriqu.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.listriqu.service.RoleMenuService;
import org.listriqu.dto.MenuDTO;
import org.listriqu.entity.MasterMenu;
import org.listriqu.response.ApiResponse;

import java.util.List;
import java.util.stream.Collectors;

@Path("/api/v1/role")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RoleMenuResource {

    @Inject
    RoleMenuService roleMenuService;

    @GET
    @Path("/{role_id}/menu")
        @RolesAllowed({"USER", "ADMIN"})
    public Response getRoleMenu(@PathParam("role_id") Integer roleId) {
      List<MenuDTO> menus = roleMenuService.getMenusByRole(roleId);
 return Response.ok(new ApiResponse("success", menus)).build();
    }
}
