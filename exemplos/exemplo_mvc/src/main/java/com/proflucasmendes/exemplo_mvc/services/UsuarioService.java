package com.proflucasmendes.exemplo_mvc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.proflucasmendes.exemplo_mvc.model.Usuario;

/**
 * CAMADA MODEL — Serviço / Lógica de Negócio
 *
 * Boas práticas de arquitetura recomendam separar a lógica de negócio
 * da classe de entidade (Usuario) em uma classe de serviço dedicada.
 *
 * O UsuarioService centraliza as operações sobre os usuários, como
 * cadastrar, buscar e remover. Isso mantém o Controller (Servlet) focado
 * apenas em receber requisições HTTP e delegar o trabalho para o serviço.
 *
 * Esse padrão também facilita a substituição futura do armazenamento
 * (ex.: trocar a lista em memória por um banco de dados) sem alterar
 * o Controller nem a View.
 */
public class UsuarioService {

  // Armazenamento em memória (sem banco de dados) para fins didáticos.
  // "static" garante que a lista seja compartilhada por todas as
  // requisições, já que o Servlet é um singleton no container.
  private static final List<Usuario> usuarios = new ArrayList<>();

  // Gerador de IDs sequenciais. AtomicLong garante que o incremento
  // seja thread-safe mesmo em ambiente com múltiplas requisições simultâneas.
  private static final AtomicLong CONTADOR_ID = new AtomicLong(1);

  /**
   * Cria um novo usuário, atribui um ID único e o armazena na lista.
   * Retorna o objeto criado para que o Controller possa repassá-lo
   * à View (JSP) como atributo de requisição.
   */
  public Usuario cadastrar(String nome, String email) {

    // Validação simples: nome e email não podem ser vazios.
    if (nome == null || nome.trim().isEmpty() || email == null || email.trim().isEmpty()) {
      throw new IllegalArgumentException("O nome e o email do usuário são obrigatórios.");
    }

    // Validação: email deve ser único.
    if (existeUsuarioComEmail(email)) {
      throw new IllegalArgumentException("O email informado já está em uso.");
    }

    Usuario novoUsuario = new Usuario(nome, email);
    long id = CONTADOR_ID.getAndIncrement();

    novoUsuario.setId(id);

    usuarios.add(novoUsuario);

    return novoUsuario;
  }

  public List<Usuario> listarTodos() {
    System.out.println("Listando todos os usuários. Total: " + usuarios.size());
    return new ArrayList<>(usuarios); // Retorna uma cópia para evitar modificações externas
  }

  private boolean existeUsuarioComEmail(String email) {
    return usuarios.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
  }

}
