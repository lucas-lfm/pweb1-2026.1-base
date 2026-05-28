package com.proflucasmendes.exemplo_mvc.model;

/**
 * CAMADA MODEL — Padrão MVC
 *
 * No padrão MVC (Model-View-Controller), o Model representa os dados
 * e as regras de negócio da aplicação.
 *
 * Esta classe é um JavaBean (ou POJO — Plain Old Java Object) que modela
 * a entidade "Usuário" no domínio da aplicação. Ela encapsula apenas
 * os dados, sem lógica de negócio ou de apresentação.
 *
 * Em páginas JSP, os atributos desta classe são acessados via
 * Expression Language (EL), por exemplo:
 *   ${usuario.nome}  →  chama getNome() automaticamente
 *
 * O Controller (Servlet) cria instâncias desta classe, popula seus dados
 * e as repassa para a View (JSP) como atributos de requisição (request attributes).
 */
public class Usuario {

  // Atributos privados — encapsulamento garante que o acesso
  // seja sempre feito pelos getters/setters (padrão JavaBean).
  private long id;
  private String nome;
  private String email;

  public Usuario() {
  }

  public Usuario(String nome, String email) {
    this.nome = nome;
    this.email = email;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
