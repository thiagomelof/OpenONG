package com.mycompany.apiprodutos.server;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mycompany.apiprodutos.dao.CategoriaDao;
import com.google.gson.Gson;
import com.mycompany.apiprodutos.model.Categoria;

@Path("/categoria")
public class CategoriaServer {

	 @GET
     @Produces(MediaType.APPLICATION_JSON)
     public List<Categoria> getCategoria(){
		 CategoriaDao categoriaDao = new CategoriaDao();
		 return categoriaDao.listar();
     }
	 
	 @GET
     @Produces(MediaType.APPLICATION_JSON)
	 @Path("{id}")
     public Categoria getCategoria(@PathParam("id") Integer id){
		 CategoriaDao categoriaDao = new CategoriaDao();
		 Categoria categoria = categoriaDao.lerPorId(id);
		 categoriaDao.closeConnection();
		 return categoria;
     }
	 
	 @POST
	 @Produces(MediaType.APPLICATION_JSON)
	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 public Integer cadastrar(@FormParam("dado") String dadosJSON ) {
		 Gson gson = new Gson();
	     Categoria categoria = gson.fromJson(dadosJSON, Categoria.class);
	     CategoriaDao categoriaDao = new CategoriaDao();
	     Integer lastId = categoriaDao.cadastrar(categoria);
	     categoriaDao.closeConnection();
	     return lastId;
	 }
	 
	 @PUT
	 @Produces(MediaType.APPLICATION_JSON)
	 @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	 @Path("{id}")
	 public Boolean alterar(@PathParam("id") Integer id, @FormParam("dado") String dadosJSON ) {
		 Gson gson = new Gson();
	     Categoria categoria = gson.fromJson(dadosJSON, Categoria.class);
	     
	     if(id == categoria.getId() || categoria.getId() == null) {
	    	 categoria.setId(id);	 
	     } else {
	    	 return false;
	     }
	     
	     CategoriaDao categoriaDao = new CategoriaDao();
	     Boolean res = categoriaDao.alterar(categoria);
	     categoriaDao.closeConnection();
	     return res;
	 }
	 
	 @DELETE
     @Produces(MediaType.APPLICATION_JSON)
	 @Path("{id}")
     public Boolean deletar(@PathParam("id") Integer id){
	     CategoriaDao categoriaDao = new CategoriaDao();
	     Boolean res = categoriaDao.deletar(id);
	     categoriaDao.closeConnection();
	     return res;
	 }
	
}