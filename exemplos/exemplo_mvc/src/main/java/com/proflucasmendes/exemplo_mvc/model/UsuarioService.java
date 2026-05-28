package com.proflucasmendes.exemplo_mvc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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
    Usuario novoUsuario = new Usuario(nome, email);
    long id = CONTADOR_ID.getAndIncrement();

    novoUsuario.setId(id);

    usuarios.add(novoUsuario);

    return novoUsuario;
  }

}
