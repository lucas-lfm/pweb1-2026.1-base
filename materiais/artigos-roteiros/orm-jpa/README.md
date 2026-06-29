# Série de Artigos: Fundamentos de ORM, JPA e Hibernate

## ✨ Contexto 

Nas práticas anteriores utilizamos JDBC e o padrão DAO para acessar e manipular dados em um banco PostgreSQL. Embora essa abordagem seja extremamente importante para compreender os fundamentos da persistência de dados, ela exige uma quantidade significativa de código para conversão entre registros do banco de dados e objetos Java.

Nesta série de quatro artigos e roteiros práticos iremos estudar _**Mapeamento Objeto-Relacional (Object-Relational Mapping - ORM)**_ e algumas das tecnologias mais importantes do ecossistema Java corporativo: a **JPA (Jakarta Persistence API)**, sua implementação mais popular, o **Hibernate** e, por fim, Spring Data JPA em uma aplicação Spring.

---

## ✅ Sugestão de Leitura e Estudo

Os artigos e roteiros desta série foram elaborados de forma a permitir que você estude e pratique os conceitos de forma gradual. A sugestão é que você siga a ordem dos artigos e roteiros, realizando as atividades propostas em cada um deles.

O conteúdo teórico presente em cada artigo é fundamental para a compreensão das atividades práticas propostas nos roteiros. Portanto, é altamente recomendável que você leia com atenção a parte teórica dos artigos antes de iniciar os roteiros práticos.

---

## 📚 Material de Apoio

- [Slides da disciplina sobre JDBC, ORM e JPA](https://github.com/lucas-lfm/pweb1-2026.1-base/tree/main/materiais/slides)
- Livro _**"JPA eficaz: as melhores práticas de persistência de dados em java"**_: [Disponível na BVU](https://plataforma.bvirtual.com.br/Acervo/Publicacao/212897)
- Documentação da Jakarta Persistence:
  - [https://jakarta.ee/specifications/persistence/](https://jakarta.ee/specifications/persistence/)
- Documentação do Hibernate:
  - [https://hibernate.org/orm/](https://hibernate.org/orm/)
- Baeldung:
  - [https://www.baeldung.com/jpa-hibernate](https://www.baeldung.com/jpa-hibernate)

---

## 📌 Pré-Requisitos

Para acompanhar esta sequência de roteiros práticos você precisará ter instalado:

- JDK 17 ou superior *(irei utilizar o JDK 21)*;
- Maven 3.8 ou superior;
- PostgreSQL 15 ou superior *(irei utilizar o PostgreSQL 18)*;
  - _pgAdmin 4, que já vem junto com o PostgreSQL._
- VS Code com as extensões _Extension Pack for Java_ e _Spring Boot Extension Pack_.

---

## Artigos e Roteiros

- [Parte 1 - Introdução ao ORM, JPA e Hibernate](parte-1.md)