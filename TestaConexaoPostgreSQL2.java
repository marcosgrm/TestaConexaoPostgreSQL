package br.edu.ifpe.loja.model.repository.testdrive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.loja.model.entity.Categoria;
import br.edu.ifpe.loja.model.entity.Produto;

public class TestaConexaoPostgreSQL2 {

	static final String DRIVER = "org.postgresql.Driver"; // Classe do driver para comunica��o com o BD
	static final String URL = "jdbc:postgresql://localhost:5432/marcos_lojamvc"; // Caminho para o BD
	static final String USER = "postgres"; // Dados para autentica��o no BD (padr�o)
	static final String PASS = "postgres"; // Dados para autentica��o no BD (padr�o)

	public static void main(String[] args) {
		Connection conn = null; // Inst�ncia para conex�o
		PreparedStatement pstm = null; // Inst�ncia para passagem de declara��o SQL para o BD
		ResultSet rs = null; // Inst�ncia para obter resultado da declara��o SQL

		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USER, PASS);

			Categoria categoriaTeste = new Categoria("categoria de teste"); //Cria��o de uma categoria de teste
			
			// Cria��o de um novo produto
			Produto produtoI = new Produto("Funko Pop Harry Potter", "Boneco Funko Pop Harry Potter", 120.0, categoriaTeste);
			// Inserindo o novo produto no BD
			String insertSQL = "insert into produto(nome,descricao,preco,categoria) values(?,?,?,?)"; //Comando para inser��o SQL
			PreparedStatement pstmInsert = conn.prepareStatement(insertSQL);
			pstmInsert.setString(1, produtoI.getNome()); // Valor do 1� par�metro
			pstmInsert.setString(2, produtoI.getDescricao()); // Valor do 2� Par�metro
			pstmInsert.setDouble(3, produtoI.getPreco()); // Valor do 3� Par�metro
			pstmInsert.setString(4, produtoI.getCategoria().getNome()); // Valor do 4� Par�metro
			
			pstmInsert.executeUpdate(); // Execu��o da inser��o do produto

			
			
			
			// Consulta de categoria
			String sql = "select nome, descricao, preco, categoria from produto"; //Comando para inser��o SQL

			pstm = conn.prepareStatement(sql); // Prepara��o da consulta
			rs = pstm.executeQuery(); // Executa a consulta e armazena uma representa��o da consulta no rs

			List<Produto> produtos = new ArrayList<>(); //Cria��o de uma lista de produtos para listagem

			while (rs.next()) { // Varredura linha a linha, enquanto ainda houverem linhas na consulta do BD
				Produto produto = new Produto(rs.getString("nome"), rs.getString("descricao"), rs.getDouble("preco"), categoriaTeste);
				produtos.add(produto); //Adiciona a linha da consulta do BD na lista de produtos
			}

			System.out.println("PRODUTOS " + produtos); //Listagem dos produtos armazenados na lista (provenientes do BD)

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// Fechamento das conex�es com o BD
				conn.close();
				pstm.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	
}
