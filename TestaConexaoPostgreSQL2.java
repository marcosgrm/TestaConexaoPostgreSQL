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

	static final String DRIVER = "org.postgresql.Driver"; // Classe do driver para comunicação com o BD
	static final String URL = "jdbc:postgresql://localhost:5432/marcos_lojamvc"; // Caminho para o BD
	static final String USER = "postgres"; // Dados para autenticação no BD (padrão)
	static final String PASS = "postgres"; // Dados para autenticação no BD (padrão)

	public static void main(String[] args) {
		Connection conn = null; // Instância para conexão
		PreparedStatement pstm = null; // Instância para passagem de declaração SQL para o BD
		ResultSet rs = null; // Instância para obter resultado da declaração SQL

		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USER, PASS);

			Categoria categoriaTeste = new Categoria("categoria de teste"); //Criação de uma categoria de teste
			
			// Criação de um novo produto
			Produto produtoI = new Produto("Funko Pop Harry Potter", "Boneco Funko Pop Harry Potter", 120.0, categoriaTeste);
			// Inserindo o novo produto no BD
			String insertSQL = "insert into produto(nome,descricao,preco,categoria) values(?,?,?,?)"; //Comando para inserção SQL
			PreparedStatement pstmInsert = conn.prepareStatement(insertSQL);
			pstmInsert.setString(1, produtoI.getNome()); // Valor do 1º parâmetro
			pstmInsert.setString(2, produtoI.getDescricao()); // Valor do 2º Parâmetro
			pstmInsert.setDouble(3, produtoI.getPreco()); // Valor do 3º Parâmetro
			pstmInsert.setString(4, produtoI.getCategoria().getNome()); // Valor do 4º Parâmetro
			
			pstmInsert.executeUpdate(); // Execução da inserção do produto

			
			
			
			// Consulta de categoria
			String sql = "select nome, descricao, preco, categoria from produto"; //Comando para inserção SQL

			pstm = conn.prepareStatement(sql); // Preparação da consulta
			rs = pstm.executeQuery(); // Executa a consulta e armazena uma representação da consulta no rs

			List<Produto> produtos = new ArrayList<>(); //Criação de uma lista de produtos para listagem

			while (rs.next()) { // Varredura linha a linha, enquanto ainda houverem linhas na consulta do BD
				Produto produto = new Produto(rs.getString("nome"), rs.getString("descricao"), rs.getDouble("preco"), categoriaTeste);
				produtos.add(produto); //Adiciona a linha da consulta do BD na lista de produtos
			}

			System.out.println("PRODUTOS " + produtos); //Listagem dos produtos armazenados na lista (provenientes do BD)

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// Fechamento das conexões com o BD
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
