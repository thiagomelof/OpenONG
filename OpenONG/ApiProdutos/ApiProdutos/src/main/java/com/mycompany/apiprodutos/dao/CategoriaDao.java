package com.mycompany.apiprodutos.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.apiprodutos.factory.Conexao;
import com.mycompany.apiprodutos.model.Categoria;

public class CategoriaDao extends Conexao{

	public CategoriaDao() {
		super();
	}

	public Integer cadastrar(Categoria c) {
		try {
            PreparedStatement preparedStatement = conexao.prepareStatement("INSERT INTO categorias(nome) values(?)");
            preparedStatement.setString(1, c.getNome());
            if (preparedStatement.executeUpdate() > 0) {
            	ResultSet res;
                res = preparedStatement.getGeneratedKeys();
                res.next();
                return res.getInt(1);
            } else {
            	return null;
            }

         } catch (SQLException ex) {
        	 ex.printStackTrace();
        	 return null;
         }
	}
	
	public Boolean alterar(Categoria c) {
		try {
            PreparedStatement preparedStatement = conexao.prepareStatement("UPDATE categorias SET nome = ? WHERE id = ?");
            preparedStatement.setString(1, c.getNome());
            preparedStatement.setInt(2, c.getId());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            } else {
            	return false;
            }

         } catch (SQLException ex) {
        	 ex.printStackTrace();
        	 return false;
         }		
	}
	
	public Boolean deletar(Integer id) {
		try {
            PreparedStatement preparedStatement = conexao.prepareStatement("DELETE FROM categorias WHERE id = ?");
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            } else {
            	return false;
            }

         } catch (SQLException ex) {
        	 ex.printStackTrace();
        	 return false;
         }		
	}
	
	
	public List<Categoria> listar() {
		String sql = "SELECT id, nome FROM categorias ORDER BY id DESC";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet res = stmt.executeQuery();
            List<Categoria> list = new ArrayList<>();
            while(res.next()){
                Categoria c = new Categoria();
                c.setId(res.getInt("id"));
                c.setNome(res.getString("nome"));
                list.add(c);
            }
            return list;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
	}
	
	public Categoria lerPorId(Integer id) {
		String sql = "SELECT id, nome FROM categorias WHERE id = ?";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();
            if(res.next()){
                Categoria c = new Categoria();
                c.setId(res.getInt("id"));
                c.setNome(res.getString("nome"));
                return c;
            } else {
            	return null;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
	}
}