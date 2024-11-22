package br.com.fiap.resource;

import br.com.fiap.dao.ProductDAO;
import br.com.fiap.exception.IdNotFoundException;
import br.com.fiap.model.Product;
import br.com.fiap.util.ConnectionFactory;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    private final ProductDAO productDAO;

    public ProductResource() throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionFactory.getConnection();
        productDAO = new ProductDAO(conn);
    }

    @POST
    public Response create(Product product) throws SQLException {
        productDAO.create(product);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public List<Product> findAll() throws SQLException {
        return productDAO.findAll();
    }

    @GET
    @Path("/{id}")
    public Product findById(@PathParam("id") int id) throws SQLException, IdNotFoundException {
        return productDAO.findById(id);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Product product) throws SQLException, IdNotFoundException {
        product.setId(id);
        productDAO.update(product);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) throws SQLException, IdNotFoundException {
        productDAO.delete(id);
        return Response.noContent().build();
    }
}
