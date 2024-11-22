package br.com.fiap.resource;

import br.com.fiap.dao.UserDAO;
import br.com.fiap.exception.IdNotFoundException;
import br.com.fiap.model.User;
import br.com.fiap.util.ConnectionFactory;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserDAO userDAO;

    public UserResource() throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionFactory.getConnection();
        userDAO = new UserDAO(conn);
    }

    @POST
    public Response create(User user) throws SQLException {
        userDAO.create(user);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/login")
    public Response login(User user) throws SQLException {
        User loggedUser = userDAO.login(user);
        return Response.ok().entity(loggedUser).build();
    }

    @GET
    public List<User> findAll() throws SQLException {
        return userDAO.findAll();
    }

    @GET
    @Path("/{id}")
    public User findById(@PathParam("id") int id) throws SQLException, IdNotFoundException {
        return userDAO.findById(id);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, User user) throws SQLException, IdNotFoundException {
        user.setId(id);
        userDAO.update(user);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) throws SQLException, IdNotFoundException {
        userDAO.delete(id);
        return Response.noContent().build();
    }
}
