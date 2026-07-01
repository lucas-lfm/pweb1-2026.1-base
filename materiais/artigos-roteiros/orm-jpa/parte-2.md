# Parte 2 - Configurando o Hibernate e criando entidades

## 🎯 Objetivos

Ao final deste artigo você deverá ser capaz de:

- Configurar a _Persistence Unit_ em um projeto Java com Maven;
- Criar entidades JPA e mapear relacionamentos entre elas;
- Compreender as anotações JPA e como elas funcionam.

---

## O que você encontrará neste artigo

- [Configuração da Persistence Unit](#configurando-a-persistence-unit)
- [Criação das Entidades](#criando-as-entidades)
- [Anotações JPA e Mapeamento de Relacionamentos (Teoria)](#anotacoes-jpa-e-mapeamento-de-relacionamentos-teoria)
- [Anotações JPA e Mapeamento de Relacionamentos (Prática)](#anotacoes-jpa-e-mapeamento-de-relacionamentos-pratica) 
- [Testando a Geração do Esquema do Banco de Dados](#testando-a-geracao-do-esquema-do-banco-de-dados)

---

<a id="configurando-a-persistence-unit"></a>
## ⚙️ Configurando a Persistence Unit

Continuando com o projeto que iniciamos na [Parte 1 desta série](./parte-1.md), vamos configurar a _Persistence Unit_ para que possamos utilizar o Hibernate como provedor JPA. A primeira coisa que precisamos fazer é criar o arquivo `persistence.xml` dentro do diretório `src/main/resources/META-INF`. Este arquivo é responsável por definir a configuração das _Persistence Units_ em nosso projeto.

Como vimos anteriormente, a _Persistence Unit_ é uma unidade de configuração que define como o JPA deve se comportar em relação ao banco de dados. No nosso caso, vamos criar uma _Persistence Unit_ chamada `cursosPU` e configurá-la para utilizar o Hibernate como provedor JPA.

No arquivo `persistence.xml`, dentro do diretório `src/main/resources/META-INF` (importante criar o diretório se ele não existir), vamos definir a _Persistence Unit_ da seguinte forma:

```xml
<persistence xmlns="https://jakarta.ee/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="https://jakarta.ee/xml/ns/persistence
             https://jakarta.ee/xml/ns/persistence/persistence_3_2.xsd"
             version="3.2">

    <persistence-unit name="cursosPU" transaction-type="RESOURCE_LOCAL">
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        <class>com.example.entities.Instrutor</class>
        <class>com.example.entities.Curso</class>
        <class>com.example.entities.Aula</class>
        <properties>
            <property name="jakarta.persistence.jdbc.driver" value="org.postgresql.Driver"/>
            <property name="jakarta.persistence.jdbc.url" value="jdbc:postgresql://localhost/cursos_pweb1"/>
            <property name="jakarta.persistence.jdbc.user" value="postgres"/>
            <property name="jakarta.persistence.jdbc.password" value="123456"/>

            <property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQLDialect"/>
            <property name="hibernate.hbm2ddl.auto" value="update"/>
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
        </properties>
    </persistence-unit>
</persistence>
```

> Lembre-se de substituir a identificação do pacote `com.example.entities` pela definição correta pacote criado na [Parte 1](./parte-1.md) e de alterar as credenciais de acesso ao banco de dados conforme a sua configuração local.

**Explicação das propriedades configuradas:**

- `jakarta.persistence.jdbc.driver`: Define o driver JDBC que será utilizado para conectar ao banco de dados. No nosso caso, estamos utilizando o driver do PostgreSQL.
- `jakarta.persistence.jdbc.url`: Define a URL de conexão com o banco de dados.
- `jakarta.persistence.jdbc.user`: Define o usuário para acesso ao banco de dados.
- `jakarta.persistence.jdbc.password`: Define a senha para acesso ao banco de dados.
- `hibernate.dialect`: Define o dialeto do Hibernate que será utilizado para gerar as consultas SQL específicas para o banco de dados em uso. No nosso caso, estamos utilizando o dialeto do PostgreSQL.
- `hibernate.hbm2ddl.auto`: Define a estratégia de geração do esquema do banco de dados. No nosso caso, estamos utilizando o valor `update`, que indica que o Hibernate deve atualizar o esquema do banco de dados com base nas entidades JPA definidas no projeto. Outras opções incluem `validate`, `create`, `create-drop`, entre outras.
- `hibernate.show_sql`: Define se as consultas SQL geradas pelo Hibernate devem ser exibidas no console. No nosso caso, estamos utilizando o valor `true`, que indica que as consultas SQL serão exibidas. Isso é útil para fins de depuração e aprendizado.
- `hibernate.format_sql`: Define se as consultas SQL exibidas no console devem ser formatadas para melhor legibilidade.

---

<a id="criando-as-entidades"></a>
## 🏗️ Criando as Entidades

Agora vamos implementar as entidades JPA que representam os objetos do nosso domínio. Para isso, vamos criar as classes `Instrutor`, `Curso` e `Aula` com seus atributos, de acordo com o modelo de dados que definimos na [Parte 1](./parte-1.md), construtores, getters e setters. Lembre-se que criamos um pacote chamado `entities` para armazenar essas classes.

**Instrutor.java**

```java
package com.example.entities; // Substitua pelo pacote correto

public class Instrutor {

  private Long id;
  private String nome;
  private String email;
  private String biografia;

  public Instrutor() {
  }

  // Construtor com parâmetros

  // Getters e Setters

}
```

**Curso.java**

```java
package com.example.entities; // Substitua pelo pacote correto

public class Curso {

  private Long id;
  private String titulo;
  private String descricao;
  private Double cargaHoraria;
  private Double preco;
  private String nivel;
  private String url;
  private String status;
  private Instrutor instrutor;

  public Curso() {
  }

  // Construtor com parâmetros

  // Getters e Setters

}
```

**Aula.java**

```java
package com.example.entities; // Substitua pelo pacote correto

public class Aula {

  private Long id;
  private String titulo;
  private String descricao;
  private Integer duracaoMinutos;
  private Integer ordem;
  private String urlVideo;
  private Curso curso;

  public Aula() {
  }

  // Construtor com parâmetros

  // Getters e Setters

}
```

Perceba que as classes `Curso` e `Aula` possuem atributos que representam os relacionamentos entre elas, como o atributo `instrutor` na classe `Curso` e o atributo `curso` na classe `Aula`. Esses atributos serão mapeados para os relacionamentos no banco de dados utilizando as anotações JPA.

Como discutido na [Parte 1](./parte-1.md), os relacionamentos entre as entidades são representadas na orientação a objetos através de referências diretas entre as instâncias das entidades. Ou seja, usamos o conceito de associações (composição ou agregação) entre as entidades. 

Após a criação das classes, vamos adicionar as anotações JPA para mapear os relacionamentos entre as entidades. Vamos utilizar as anotações `@Entity`, `@Id`, `@GeneratedValue`, `@ManyToOne` e `@OneToMany`, entre outras, para mapear os relacionamentos entre as entidades.

---

<a id="anotacoes-jpa-e-mapeamento-de-relacionamentos-teoria"></a>
## 📚 Anotações JPA e Mapeamento de Relacionamentos (Teoria)

As **anotações _(annotations)_** são um recurso do Java que permite adicionar **metadados às classes, métodos e atributos**. No contexto do JPA, as anotações são utilizadas para mapear as entidades e seus relacionamentos com o banco de dados. Através das anotações, podemos definir como as classes e atributos serão persistidos no banco de dados, como os relacionamentos entre as entidades serão mapeados, entre outras configurações. 

O provedor JPA (no nosso caso, o Hibernate) lê essas anotações em **tempo de execução** e gera as consultas SQL necessárias para persistir os dados no banco de dados. Existem diversas anotações JPA que podem ser utilizadas para mapear as entidades e seus relacionamentos. Cada anotação possui uma função específica e deve ser utilizada de acordo com o contexto da aplicação. 

> Vamos abordar aqui as anotações mais comuns do JPA. Para um aprofundamento sobre as anotações JPA, consulte a [documentação oficial do Jakarta EE](https://jakarta.ee/specifications/persistence/3.2/jakarta-persistence-spec-3.2.html).

**@Entity**

A anotação `@Entity` é utilizada para indicar que uma classe é uma entidade no contexto do JPA. Isso significa que a classe representa uma tabela no banco de dados. Alguns detalhes importantes sobre a anotação `@Entity`:

- A anotação `@Entity` deve ser colocada na **declaração da classe**.
- A classe anotada com `@Entity` deve possuir um **construtor sem argumentos**.
- A classe anotada com `@Entity` deve possuir um **atributo que será a chave primária** da tabela, que será anotado com `@Id`.

**@Table**

A anotação `@Table` é utilizada para definir o nome da tabela no banco de dados que será mapeada pela entidade. Se a anotação `@Table` não for utilizada, o JPA utilizará o **nome da classe** como nome da tabela por padrão. Alguns detalhes importantes sobre a anotação `@Table`:

- A anotação `@Table` deve ser colocada na **declaração da classe**, logo após a anotação `@Entity`.
- O atributo `name` da anotação `@Table` define o **nome da tabela** no banco de dados: 
    ```java
    @Entity
    @Table(name = "instrutores")
    public class Instrutor {
        // ...
    }
    ```
    > No exemplo acima, a entidade `Instrutor` será mapeada para a tabela `instrutores` no banco de dados.
- Essa anotação é opcional. Caso não seja utilizada, o Hibernate utilizará o **nome da classe** como nome da tabela por padrão.

**@Id e @GeneratedValue**
A anotação `@Id` é utilizada para indicar que um atributo da classe é a **chave primária** da tabela no banco de dados. A anotação `@GeneratedValue` é utilizada para definir a estratégia de geração do valor da chave primária. Alguns detalhes importantes sobre a anotação `@Id`:

- A anotação `@Id` deve ser colocada na **declaração do atributo** que será a chave primária.
- A anotação `@GeneratedValue` deve ser colocada na **declaração do atributo** que será a chave primária.
    ```java
    @Entity
    public class Instrutor {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        // ...
    }
    ```
    > Na maioria dos casos, a estratégia `GenerationType.IDENTITY` é utilizada para gerar o valor da chave primária de forma automática pelo banco de dados.
- Valores possíveis para o atributo `strategy` da anotação `@GeneratedValue`:
    - `GenerationType.IDENTITY`: O banco de dados é responsável por gerar o valor da chave primária. Essa estratégia é utilizada quando a chave primária é auto-incrementada no banco de dados.
    - `GenerationType.SEQUENCE`: O JPA utiliza uma sequência do banco de dados para gerar o valor da chave primária. Essa estratégia é utilizada quando o banco de dados possui suporte a sequências.
    - `GenerationType.TABLE`: O JPA utiliza uma tabela auxiliar para gerar o valor da chave primária. Essa estratégia é utilizada quando o banco de dados não possui suporte a sequências.
    - `GenerationType.AUTO`: O JPA escolhe automaticamente a melhor estratégia de geração do valor da chave primária, dependendo do banco de dados utilizado.

**@Column**
A anotação `@Column` é utilizada para definir o mapeamento de um atributo da classe para uma coluna da tabela no banco de dados. Alguns detalhes importantes sobre a anotação `@Column`:

- A anotação `@Column` deve ser colocada na **declaração do atributo** que será mapeado para uma coluna da tabela.
- O atributo `name` da anotação `@Column` define o **nome da coluna** no banco de dados:
    ```java
    @Entity
    public class Instrutor {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "nome")
        private String nome;

        // ...
    }
    ```
    > No exemplo acima, o atributo `nome` da classe `Instrutor` será mapeado para a coluna `nome` na tabela no banco de dados.
- Caso a anotação `@Column` não seja utilizada, o JPA utilizará o **nome do atributo** como nome da coluna por padrão.
- A anotação `@Column` possui diversos atributos que podem ser utilizados para definir o mapeamento da coluna. Cada atributo define uma característica específica da coluna no banco de dados e é utilizado para configurar as propriedades da coluna, como:
    - `nullable`: Define se a coluna pode ser nula ou não. O padrão é `true`.
    - `length`: Define o tamanho máximo da coluna. O padrão é `255`.
    - `unique`: Define se a coluna deve ser única ou não. O padrão é `false`.

### Mapeamento de Relacionamentos

Uma das principais funcionalidades do JPA é o mapeamento de relacionamentos entre entidades. O JPA oferece diversas anotações para mapear os relacionamentos entre as entidades, como `@OneToOne`, `@OneToMany`, `@ManyToOne` e `@ManyToMany`. Cada anotação define um tipo específico de relacionamento entre as entidades.

**Relacionamento N:1 (Many-to-One) e Relacionamento 1:N (One-to-Many)**

O relacionamento N:1 (Many-to-One) é utilizado quando uma entidade possui um relacionamento com outra entidade, **onde várias instâncias da primeira entidade podem estar associadas a uma única instância da segunda entidade**. Por exemplo, vários cursos podem ser ministrados por um único instrutor.

O inverso do relacionamento N:1 é o relacionamento 1:N (One-to-Many), que é utilizado quando uma entidade possui um relacionamento com outra entidade, **onde uma única instância da primeira entidade pode estar associada a várias instâncias da segunda entidade**. Por exemplo, um instrutor pode ministrar vários cursos.

Com JPA, mapeamos esse relacionamento utilizando a anotação `@ManyToOne` na entidade que possui a referência para a outra entidade (`Curso`, no nosso caso, que possui a referência para o instrutor), e a anotação `@OneToMany` na entidade que possui a coleção de instâncias da primeira entidade (`Instrutor`, no nosso caso, que possui a coleção de cursos que ministra).

Na prática, o relacionamento N:1 e 1:N é representado da seguinte forma:

```java
@Entity
public class Curso {
    // ...

    @ManyToOne
    @JoinColumn(name = "instrutor_id")
    private Instrutor instrutor;

    // ...
}

```

```java
@Entity
public class Instrutor {
    // ...

    @OneToMany(mappedBy = "instrutor")
    private List<Curso> cursos;

    // ...
}
```

Perceba que na entidade `Curso`, utilizamos a anotação `@ManyToOne` para indicar que vários cursos podem estar associados a um único instrutor. Já na entidade `Instrutor`, utilizamos a anotação `@OneToMany` para indicar que um instrutor pode ministrar vários cursos. O atributo `mappedBy` na anotação `@OneToMany` indica o nome do atributo na entidade `Curso` que mapeia o relacionamento.

O mapeamento foi realizado de forma bidirecional, ou seja, é possível navegar do `Curso` para o `Instrutor` e do `Instrutor` para os `Cursos` que ele ministra. Porém, como o ***"lado que possui a FK (chave estrangeira)"*** é o `Curso`, o mapeamento é realizado a partir dela.

A anotação `@JoinColumn` na entidade `Curso` define a coluna que será utilizada para armazenar a chave estrangeira que referencia o instrutor. Nesse caso, na tabela `curso`, teremos uma coluna chamada `instrutor_id` que armazenará o ID do instrutor associado ao curso.

> 📌 Em OO, o sentido de navegação de um relacionamento é determinado pela direção da referência: a entidade que possui o atributo de referência é a que pode navegar até a outra. Se `Curso` possui um campo `Instrutor`, então é possível partir de um `Curso` e chegar ao seu `Instrutor`. Mas não o contrário, a menos que `Instrutor` também possua uma referência de volta para `Curso` (nesse caso, para uma coleção de cursos). É muito importante ter um entendimento claro sobre esse conceito de navegabilidade de um relacionamento.

**Relacionamento 1:1 (One-to-One)**

O relacionamento 1:1 (One-to-One) é utilizado quando uma entidade possui um relacionamento com outra entidade, onde **uma única instância da primeira entidade está associada a uma única instância da segunda entidade**. Por exemplo, um instrutor pode ter um perfil detalhado associado a ele. O relacionamento entre as entidades `Instrutor` e `Perfil` seria representado da seguinte forma:

```java
@Entity
public class Instrutor {
    // ...

    @OneToOne
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

    // ...
}
```

```java
@Entity
public class Perfil {
    // ...

    @OneToOne(mappedBy = "perfil")
    private Instrutor instrutor;

    // ...
}
```

Perceba que na entidade `Instrutor`, utilizamos a anotação `@OneToOne` para indicar que um instrutor possui um perfil associado a ele. Já na entidade `Perfil`, utilizamos a anotação `@OneToOne` com o atributo `mappedBy` para indicar que o relacionamento é bidirecional e que a entidade `Perfil` já está mapeada pelo atributo `perfil` na entidade `Instrutor`.

**Relacionamento N:N (Many-to-Many)**

O relacionamento N:N (Many-to-Many) é utilizado quando uma entidade possui um relacionamento com outra entidade, onde **várias instâncias da primeira entidade podem estar associadas a várias instâncias da segunda entidade**. Por exemplo, um curso pode ter vários alunos matriculados, e um aluno pode estar matriculado em vários cursos. O relacionamento entre as entidades `Curso` e `Aluno` seria representado da seguinte forma:

```java
@Entity
public class Curso {
    // ...

    @ManyToMany
    @JoinTable(
        name = "curso_aluno",
        joinColumns = @JoinColumn(name = "curso_id"),
        inverseJoinColumns = @JoinColumn(name = "aluno_id")
    )
    private List<Aluno> alunos;

    // ...
}
```

```java
@Entity
public class Aluno {
    // ...

    @ManyToMany(mappedBy = "alunos")
    private List<Curso> cursos;

    // ...
}
```

Uma atenção especial deve ser dada ao relacionamento N:N, pois ele requer uma tabela intermediária para armazenar as associações entre as duas entidades. No exemplo acima, a tabela intermediária, chamada `curso_aluno`, foi definida com a anotação `@JoinTable`. Ela possui duas colunas: `curso_id` e `aluno_id`, que armazenam os IDs das entidades associadas. O atributo `joinColumns` define a coluna que referencia a entidade onde o mapeamento está definido (no caso, `Curso`), enquanto o atributo `inverseJoinColumns` define a coluna que referencia a outra entidade (no caso, `Aluno`).

Na entidade `Aluno`, utilizamos a anotação `@ManyToMany` com o atributo `mappedBy` para indicar que o relacionamento é bidirecional e que a entidade `Aluno` já está mapeada pelo atributo `alunos` na entidade `Curso`.

---

<a id="anotacoes-jpa-e-mapeamento-de-relacionamentos-pratica"></a>
## 👨‍💻 Anotações JPA e Mapeamento de Relacionamento (Prática)

Antes de retomar a parte prática, vale ressaltar que nesse momento iremos trabalhar somente com relacionamentos dos tipos *N:1* e *1:N* (`@ManyToOne` e `@OneToMany`), entre as entidades `Curso` e `Instrutor` e entre as entidades `Aula` e `Curso`.

Abra o arquivo `Instrutor.java` e adicione as anotações JPA para mapear a entidade `Instrutor` e o relacionamento com a entidade `Curso`. O código final da classe `Instrutor` ficará da seguinte forma:

**Instrutor.java**

```java
package com.example.entities; // Substitua pelo pacote correto

// Importações necessárias

@Entity
public class Instrutor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nome", nullable = false, length = 100)
  private String nome;

  @Column(name = "email", nullable = false, length = 100, unique = true)
  private String email;

  @Column(name = "biografia", length = 500)
  private String biografia;

  @OneToMany(mappedBy = "instrutor")
  private List<Curso> cursos = new ArrayList<>();

  public Instrutor() {
  }

  // Construtor com parâmetros

  // Getters e Setters

  public List<Curso> getCursos() {
    return cursos;
  }

}
```

Perceba que adicionamos ao modelo da entidade `Instrutor` o atributo `cursos`, que representa a coleção de cursos ministrados pelo instrutor. Esse atributo é mapeado com a anotação `@OneToMany`, indicando que um instrutor pode ministrar vários cursos. O atributo `mappedBy` indica que o relacionamento é mapeado pelo atributo `instrutor` na entidade `Curso`.

Agora abra o arquivo `Curso.java` e adicione as anotações JPA para mapear a entidade `Curso` e o relacionamento com a entidade `Instrutor`. O código final da classe `Curso` ficará da seguinte forma:

**Curso.java**

```java
package com.example.entities; // Substitua pelo pacote correto

// Importações necessárias

@Entity
public class Curso {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "titulo", nullable = false, length = 100)
  private String titulo;

  @Column(name = "descricao", nullable = false, length = 500)
  private String descricao;

  @Column(name = "carga_horaria", nullable = false)
  private Double cargaHoraria;

  @Column(name = "preco", nullable = false)
  private Double preco;

  @Column(name = "nivel", nullable = false, length = 50)
  private String nivel;

  @Column(name = "url", nullable = false, length = 200)
  private String url;

  @Column(name = "status", nullable = false, length = 50)
  private String status;

  @ManyToOne
  @JoinColumn(name = "instrutor_id", nullable = false)
  private Instrutor instrutor;

  // Coleção de aulas associadas ao curso
  @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Aula> aulas = new ArrayList<>();

  public Curso() {
  }

  // Construtor com parâmetros

  // Getters e Setters

}
```

Perceba que adicionamos o atributo `aulas`, que representa a coleção de aulas associadas ao curso. Esse atributo é mapeado com a anotação `@OneToMany`, indicando que um curso pode ter várias aulas associadas a ele. O atributo `mappedBy` indica que o relacionamento é mapeado pelo atributo `curso` na entidade `Aula`.

Como a entidade `Aula` é dependente de `Curso`, ou seja, não faz sentido existir uma aula sem um curso associado, a entidade `Aula` é uma entidade fraca em relação a `Curso`. Por isso, adicionamos os atributos `cascade = CascadeType.ALL` e `orphanRemoval = true` na anotação `@OneToMany`. 

O atributo `cascade` indica que todas as operações realizadas em um curso (como persistir ou remover) serão propagadas para as aulas associadas a ele. Ou seja, caso um curso seja removido, todas as aulas associadas a ele também serão removidas. Já o atributo `orphanRemoval` indica que, se uma aula for removida da coleção de aulas de um curso, ela será automaticamente removida do banco de dados, pois não faz sentido ela continuar existindo sem um curso associado.

A existência do atributo `aulas` na entidade `Curso` permite que possamos gerenciar, mais facilmente, as aulas associadas a cada curso. Inclusive, podemos utilizar o atributo `aulas` para adicionar ou remover as aulas de um curso específico. Vejamos como isso pode ser feito na prática:

```java
package com.example.entities; // Substitua pelo pacote correto

// Importações necessárias

@Entity
public class Curso {
    // ... (outros atributos e métodos)

    public void adicionarAula(Aula aula) {
        aulas.add(aula);
        aula.setCurso(this); // Define o curso da aula, passando esta instância (this) de Curso.
    }

    public void removerAula(Aula aula) {
        aulas.remove(aula);
        aula.setCurso(null); // Remove a referência do curso na aula
    }
}
```

Por fim, abra o arquivo `Aula.java` e adicione as anotações JPA para mapear a entidade `Aula` e o relacionamento com a entidade `Curso`. O código final da classe `Aula` ficará da seguinte forma:

**Aula.java**

```java
package com.example.entities; // Substitua pelo pacote correto

// Importações necessárias

@Entity
public class Aula {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;
    
    @Column(name = "descricao", nullable = false, length = 500)
    private String descricao;
    
    @Column(name = "duracao_minutos", nullable = false)
    private Integer duracaoMinutos;
    
    @Column(name = "ordem", nullable = false)
    private Integer ordem;
    
    @Column(name = "url_video", nullable = false, length = 200)
    private String urlVideo;
    
    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;
    
    public Aula() {
    }
    
    // Construtor com parâmetros
    
    // Getters e Setters
    
}
```

---

<a id="testando-a-geracao-do-esquema-do-banco-de-dados"></a>
## ▶️ Testando a Geração do Esquema do Banco de Dados

Agora que já configuramos a _Persistence Unit_ e criamos as entidades JPA com seus respectivos relacionamentos, podemos testar a geração do esquema do banco de dados. Para isso, vamos criar uma classe `Main` com o método `main` (ou utilizar a classe `Main`, caso já exista) para inicializar o JPA e verificar se as tabelas foram criadas corretamente no banco de dados, seguindo o mapeamento definido nas entidades.

```java
package com.example; // Substitua pelo pacote correto

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cursosPU"); // Referencia à Persistence Unit definida no persistence.xml
        EntityManager em = emf.createEntityManager();

        // Aqui você poderia adicionar código para testar a persistência de entidades, se desejar.

        em.close();
        emf.close();
    }
}
```

Execute a classe `Main` e verifique no banco de dados se as tabelas foram criadas corretamente. Se tudo estiver configurado corretamente, você deverá ver as tabelas `instrutor`, `curso` e `aula` no banco de dados, com os relacionamentos definidos entre elas.

Caso ocorra algum erro durante a execução, observe a saída do console para obter mais informações sobre o erro. Verifique se as dependências do Hibernate e do PostgreSQL estão corretamente configuradas no arquivo `pom.xml`, se o banco de dados está em execução e se as credenciais de acesso estão corretas no arquivo `persistence.xml`.

> 📌 Lembre-se de que, ao utilizar a estratégia `hibernate.hbm2ddl.auto` com o valor `update`, o Hibernate irá atualizar o esquema do banco de dados com base nas entidades JPA definidas no projeto. Caso você queira recriar o esquema do banco de dados, você pode alterar o valor para `create` ou `create-drop`, mas lembre-se de que isso irá apagar todos os dados existentes no banco de dados. Em ambiente de produção, é recomendado utilizar a estratégia `validate` para garantir que o esquema do banco de dados esteja alinhado com as entidades JPA.

---

## 🎯 Exercício Conceitual

1. Qual alternativa melhor define Hibernate?

    a) Framework ORM       
    b) Banco de dados      
    c) Especificação       
    d) Driver JDBC

2. Em qual arquivo é configurada a _Persistence Unit_ em um projeto Java com Maven?

    a) `application.properties`       
    b) `persistence.xml`      
    c) `hibernate.cfg.xml`       
    d) `pom.xml`

3. Qual anotação JPA é utilizada para indicar que uma classe é uma entidade no contexto do JPA?

    a) `@Table`       
    b) `@Entity`      
    c) `@Id`       
    d) `@Column`

4. Qual anotação JPA é utilizada para mapear um relacionamento N:1 (Many-to-One) entre entidades?

    a) `@OneToMany`       
    b) `@ManyToOne`      
    c) `@OneToOne`       
    d) `@ManyToMany`

5. Qual anotação JPA é utilizada para mapear um relacionamento 1:N (One-to-Many) entre entidades?

    a) `@OneToMany`       
    b) `@ManyToOne`      
    c) `@OneToOne`       
    d) `@ManyToMany`

6. Qual anotação JPA é utilizada para mapear um relacionamento N:N (Many-to-Many) entre entidades?

    a) `@OneToMany`       
    b) `@ManyToOne`      
    c) `@OneToOne`       
    d) `@ManyToMany`

7. Qual anotação JPA é utilizada para definir o nome da tabela no banco de dados que será mapeada pela entidade?

    a) `@Table`       
    b) `@Entity`      
    c) `@Id`       
    d) `@Column`

8. Qual anotação JPA é utilizada para definir o mapeamento de um atributo da classe para uma coluna da tabela no banco de dados?

    a) `@Table`       
    b) `@Entity`      
    c) `@Id`       
    d) `@Column`

9. Qual anotação JPA é utilizada para indicar que um atributo da classe é a chave primária da tabela no banco de dados?

    a) `@Table`       
    b) `@Entity`      
    c) `@Id`       
    d) `@Column`

10. Em relacionamentos bidirecionais, qual é o lado que possui a chave estrangeira (FK) no relacionamento N:1 (Many-to-One)?

    a) O lado "1" (One)       
    b) O lado "N" (Many)      
    c) Ambos os lados       
    d) Nenhum dos lados

---

## ⏩ Próximos Passos

- Realizar operações de persistência (CRUD) utilizando o JPA e o Hibernate;
- Aprender a utilizar o `EntityManager` para gerenciar as entidades e realizar consultas no banco de dados;
- Explorar o uso de JPQL (Java Persistence Query Language) para realizar consultas mais complexas;
- Aprender a usar e gerenciar transações no JPA e no Hibernate;