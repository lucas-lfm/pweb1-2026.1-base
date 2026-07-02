# Parte 3 - Explorando o EntityManager, JPQL e Padrão DAO

## 🎯 Objetivos

Ao final deste artigo você deverá ser capaz de:

- Compreender o papel do `EntityManager` na JPA e como ele facilita a interação com o banco de dados;
- Entender o conceito de transações e como gerenciá-las utilizando o `EntityManager`;
- Aprender a utilizar a JPQL _(Java Persistence Query Language)_ para realizar consultas mais complexas e flexíveis em relação às consultas SQL tradicionais;
- Implementar o padrão DAO (Data Access Object) para isolar a lógica de acesso a dados, promovendo uma arquitetura mais limpa e modular.

---

## O que você encontrará neste artigo

---

## 🏭 EntityManager na Prática

Como vimos nos artigos anteriores, o `EntityManager` é a interface principal da JPA para interagir com o contexto de persistência. Ele é responsável por gerenciar o ciclo de vida das entidades, realizar operações de CRUD (Create, Read, Update, Delete) e gerenciar transações.

A especificação da JPA define que o `EntityManager` deve ser obtido a partir de uma `EntityManagerFactory`, que é configurada com as propriedades do banco de dados e as classes de entidade. 

> ✨ Em aplicações Spring Boot, o gerenciamento do `EntityManager` é simplificado, pois o Spring Boot configura automaticamente o `EntityManager` e o disponibiliza para injeção de dependência. Mas isso é assunto para o próximo artigo, onde exploraremos o **Spring Data JPA**.

No final da Parte 2, testamos a configuração do Hibernate e realizamos a conexão com o banco de dados, gerando automaticamente as tabelas no banco de dados. Utilizando a classe `Main` como ponto de entrada para os testes, vamos agora avançar na exploração do `EntityManager` para operações de persistência.

```java
// Pacote e importações omitidos

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cursosPU");
        EntityManager em = emf.createEntityManager();

        // Operações de persistência utilizando o EntityManager

        em.close();
        emf.close();
    }
}
```

**Entendimentos importantes sobre o código acima:**

- `Persistence.createEntityManagerFactory("cursosPU");`: Cria um objeto `EntityManagerFactory` com base na unidade de persistência definida no arquivo `persistence.xml`. O nome `"cursosPU"` deve corresponder ao nome da unidade de persistência configurada;
- `emf.createEntityManager();`: Cria um objeto `EntityManager` a partir do objeto `EntityManagerFactory`. O `EntityManager` é utilizado para realizar operações de persistência no banco de dados;
- Podemos ter uma única instância de `EntityManagerFactory` para toda a aplicação, enquanto podemos criar múltiplas instâncias de `EntityManager` conforme necessário;
- É importante fechar o `EntityManager` e o `EntityManagerFactory` após o uso para liberar recursos.

---

### 🔄 Operações de Persistência com o EntityManager

Com o `EntityManager`, podemos realizar diversas operações de persistência, como salvar, atualizar, buscar e remover entidades. Vamos explorar algumas dessas operações.

> 📌 Teste os códigos apresentados abaixo na classe `Main`, inserindo-os antes da chamada a `em.close()`, para entender como o `EntityManager` funciona na prática.

**1. Persistindo uma nova entidade:**

```java
// Criando uma nova instância da entidade Instrutor
Instrutor instrutor = new Instrutor();
instrutor.setNome("João Silva");
instrutor.setEmail("joao.silva@example.com");
instrutor.setBiografia("Especialista em Java e desenvolvimento web.");

// Iniciando uma transação
em.getTransaction().begin();

// Persistindo a entidade
em.persist(instrutor);

// Confirmando a transação
em.getTransaction().commit();
```

Executando o código acima, uma nova linha será inserida na tabela `instrutores` do banco de dados, representando o instrutor "João Silva". O método `persist()` é utilizado para tornar a entidade gerenciada pelo contexto de persistência, e a transação é iniciada e confirmada para garantir que a operação seja realizada de forma atômica (trataremos mais detalhes sobre transações mais adiante).

Preste atenção ao fato de que, ao persistir a entidade, o Hibernate obtém automaticamente o valor do campo `id` (chave primária) da entidade `Instrutor`, utilizando a estratégia de geração definida na anotação `@GeneratedValue`. Logo, se você imprimir o valor de `instrutor.getId()` após a chamada a `em.getTransaction().commit()`, verá que ele terá sido preenchido com o valor gerado pelo banco de dados.

Lembre-se do ciclo de vida das entidades na JPA, estudado na [Parte 1](./parte-1.md). No código acima, a entidade `instrutor` passa do estado _transient_ (logo após a criação) para o estado _managed_ quando chamamos `em.persist(instrutor)`. Após a confirmação da transação, a entidade permanece no estado _managed_ até que o `EntityManager` seja fechado ou a entidade seja explicitamente removida do contexto de persistência.

Isso significa que, enquanto a entidade estiver no estado _managed_, quaisquer alterações feitas em seus atributos serão automaticamente sincronizadas com o banco de dados quando a transação for confirmada. Por exemplo, se você alterar o email do instrutor após a chamada a `em.persist(instrutor)` e antes de `em.getTransaction().commit()`, essa alteração será refletida no banco de dados. Execute o seguinte código para testar essa funcionalidade:

```java
// Criando uma nova instância da entidade Instrutor
Instrutor instrutor = new Instrutor();
instrutor.setNome("João Silva");
instrutor.setEmail("joao.silva@example.com");
instrutor.setBiografia("Especialista em Java e desenvolvimento web.");

// Iniciando uma transação
em.getTransaction().begin();

// Persistindo a entidade
em.persist(instrutor);

instrutor.setEmail("silva.joao@example.com"); // Alterando o email do instrutor antes de confirmar a transação

// Confirmando a transação
em.getTransaction().commit();
```

Verifique os logs do Hibernate no console para observar os comandos SQL geradas durante a execução do código. Isso ajudará a entender como o Hibernate interage com o banco de dados e como as operações de persistência são realizadas. Você verá algo semelhante a:

![Logs do Hibernate](./img/log-hibernate-1.png)

Perceba que o Hibernate gerou um comando `INSERT` para inserir a nova entidade na tabela `instrutores`, e, em seguida, um comando `UPDATE` para atualizar o email do instrutor. 

> ✨ Você não precisou se preocupar em escrever comandos SQL manualmente, pois o Hibernate gerenciou isso automaticamente com base nos mapeamentos definidos nas entidades e nas operações realizadas no contexto de persistência.

**2. Buscando uma entidade pelo ID:**

```java
// Buscando uma entidade Instrutor pelo ID
Instrutor instrutorEncontrado = em.find(Instrutor.class, 1L); // Substitua 1L pelo ID do instrutor que deseja buscar
if (instrutorEncontrado != null) {
    System.out.println("Instrutor encontrado: " + instrutorEncontrado.getNome());
} else {
    System.out.println("Instrutor não encontrado.");
}
```

O método `find()` do `EntityManager` é utilizado para buscar uma entidade pelo seu ID. Ele retorna a entidade correspondente se encontrada, ou `null` caso contrário. Perceba que é necessário fornecer a classe da entidade como parâmetro para o método, visto que o Hibernate precisa saber qual é o tipo da entidade que está sendo buscada.

É importante entender que, ao buscar uma entidade, ela passa para o estado _managed_, permitindo que quaisquer alterações feitas em seus atributos sejam sincronizadas com o banco de dados quando a transação for confirmada. Vejamos um exemplo prático:

```java
// Buscando uma entidade Instrutor pelo ID
Instrutor instrutorEncontrado = em.find(Instrutor.class, 1L);
if (instrutorEncontrado != null) {
    System.out.println("Instrutor encontrado: " + instrutorEncontrado.getNome());
    
    // Iniciando uma transação para atualizar o nome do instrutor
    em.getTransaction().begin();

    // Alterando o nome do instrutor
    instrutorEncontrado.setNome("João da Silva");
    
    em.getTransaction().commit(); // Confirmando a transação para persistir a alteração no banco de dados
} else {
    System.out.println("Instrutor não encontrado.");
}
```

Alguns detalhes importantes sobre o código acima:

- O Hibernate pode usar cache de primeiro nível para otimizar a busca de entidades. Se você buscar a mesma entidade novamente dentro do mesmo contexto de persistência, o Hibernate retornará a instância já carregada em vez de realizar uma nova consulta ao banco de dados;
- Se você tentar buscar uma entidade que não existe no banco de dados, o método `find()` retornará `null`, e você deve tratar esse caso adequadamente para evitar `NullPointerException` em seu código;
- Perceba que se você quer somente buscar a entidade sem alterar seus atributos, não é necessário iniciar uma transação.

**3. Atualizando uma entidade existente:**

Para atualizar uma entidade existente, você pode simplesmente modificar seus atributos enquanto ela estiver no estado _managed_, como fizemos no exemplo anterior. O Hibernate detectará automaticamente as alterações e gerará os comandos SQL necessários para atualizar o banco de dados quando a transação for confirmada.

Esse comportamento é conhecido como _dirty checking_, e é uma das funcionalidades mais poderosas do Hibernate, pois permite que você trabalhe com objetos Java de forma natural, sem se preocupar com a sincronização manual com o banco de dados.

**Atualizando uma entidade com `merge()`:**

O método `merge()` do `EntityManager` é utilizado para atualizar uma entidade que está no estado _detached_ (desanexada do contexto de persistência). Quando você chama `merge()`, o Hibernate copia os valores da entidade fornecida para uma nova instância gerenciada e retorna essa instância. Vamos ver um exemplo prático:

```java
// Suponha que você tenha uma entidade Instrutor desanexada (detached)
Instrutor instrutorDetached = new Instrutor();
instrutorDetached.setId(1L); // ID da entidade que você deseja atualizar
instrutorDetached.setNome("Maria da Silva");
instrutorDetached.setEmail("maria.silva@example.com");

em.getTransaction().begin(); // Iniciando uma transação

// Chamando merge() para atualizar a entidade
Instrutor instrutorAtualizado = em.merge(instrutorDetached);

em.getTransaction().commit(); // Confirmando a transação
```

**4. Removendo uma entidade:**

Para remover uma entidade do banco de dados, você pode utilizar o método `remove()` do `EntityManager`. Esse método marca a entidade para remoção, e a remoção real ocorre quando a transação é confirmada. Vamos ver um exemplo prático:

```java
em.getTransaction().begin(); // Iniciando uma transação

// Buscando a entidade Instrutor que deseja remover
Instrutor instrutorParaRemover = em.find(Instrutor.class, 1L);
if (instrutorParaRemover != null) {
    // Removendo a entidade
    em.remove(instrutorParaRemover);
    System.out.println("Instrutor removido com sucesso.");
} else {
    System.out.println("Instrutor não encontrado para remoção.");
}

em.getTransaction().commit(); // Confirmando a transação
```

Perceba que, assim como nas operações de persistência e atualização, é necessário iniciar uma transação antes de remover a entidade. O Hibernate gerará o comando SQL `DELETE` correspondente quando a transação for confirmada. Nesse exemplo, envolvemos a operação de busca e remoção em uma transação para garantir que as duas operações sejam realizadas de forma atômica.

---

### ✅ Gerenciando Transações com o EntityManager

No contexto da JPA, uma transação é uma **unidade de trabalho que garante que um conjunto de operações seja executado de forma atômica**. Isso significa que todas as operações dentro de uma transação devem ser concluídas com sucesso para que as alterações sejam persistidas no banco de dados. 

Caso ocorra algum erro durante a execução das operações, a transação pode ser revertida, garantindo que o banco de dados permaneça em um estado consistente.

Para trabalhar com transações utilizando o `EntityManager`, você pode utilizar os métodos `getTransaction().begin()`, `getTransaction().commit()` e `getTransaction().rollback()`. Vamos explorar cada um desses métodos:

- `getTransaction().begin()`: Inicia uma nova transação. Todas as operações de persistência realizadas após essa chamada serão parte dessa transação;
- `getTransaction().commit()`: Confirma a transação, persistindo todas as alterações realizadas no banco de dados. Se todas as operações forem bem-sucedidas, a transação é concluída com sucesso;
- `getTransaction().rollback()`: Reverte a transação, desfazendo todas as alterações realizadas desde o início da transação. Isso é útil em casos de erro ou exceção, garantindo que o banco de dados permaneça em um estado consistente.

Para fins didáticos, vamos criar um exemplo prático que demonstra o uso de transações com o `EntityManager`. Suponha que queremos persistir uma nova entidade `Curso` com todas as suas aulas associadas. Se ocorrer algum erro durante a persistência das aulas, queremos garantir que nenhuma alteração seja feita no banco de dados. Veja o exemplo abaixo:

```java
try {
    em.getTransaction().begin();

    // Buscando o instrutor que será associado ao curso
    Instrutor instrutorEncontrado = em.find(Instrutor.class, 1L);

    // Criando uma nova instância da entidade Curso
    Curso curso = new Curso();
    curso.setTitulo("Java Avançado");
    curso.setDescricao("Curso avançado de Java para desenvolvedores experientes.");
    curso.setCargaHoraria(40.0);
    curso.setPreco(499.99);
    curso.setNivel("Avançado");
    curso.setUrl("https://www.example.com/java-avancado");
    curso.setStatus("Ativo");
    // Supondo que instrutorEncontrado seja uma entidade Instrutor previamente buscada
    curso.setInstrutor(instrutorEncontrado);

    // Persistindo o curso
    em.persist(curso);

    // Criando e persistindo aulas associadas ao curso
    Aula aula1 = new Aula();
    aula1.setTitulo("Aula 1 - Introdução ao Java Avançado");
    aula1.setDescricao("Nesta aula, vamos explorar conceitos avançados de Java.");
    aula1.setDuracaoMinutos(30);
    aula1.setOrdem(1);
    aula1.setUrlVideo("https://www.example.com/java-avancado/aula1");
    aula1.setCurso(curso);

    em.persist(aula1);

    Aula aula2 = new Aula();
    aula2.setTitulo("Aula 2 - Generics e Collections");
    aula2.setDescricao("Nesta aula, vamos explorar os conceitos de Generics e Collections em Java.");
    aula2.setDuracaoMinutos(45);
    aula2.setOrdem(2);
    aula2.setUrlVideo("https://www.example.com/java-avancado/aula2");
    aula2.setCurso(curso);

    em.persist(aula2);

    // Confirmando a transação
    em.getTransaction().commit();
} catch (Exception e) {
    // Em caso de erro, reverter a transação
    em.getTransaction().rollback();
    System.out.println("Erro ao persistir o curso e suas aulas: " + e.getMessage());
}
```

Caso ocorra algum erro durante a execução do código, a transação será revertida, com `em.getTransaction().rollback()`, garantindo que nenhuma alteração seja feita no banco de dados.

Acesse seu `pgAdmin` e verifique se o curso e suas aulas foram persistidos corretamente no banco de dados. Você pode executar a sequinte consulta SQL para verificar os registros:

```sql
SELECT a.*, c.titulo FROM aula AS a INNER JOIN curso AS c ON a.curso_id = c.id;
```

Se tudo estiver correto, você verá os registros das aulas associadas ao curso "Java Avançado" na tabela `aula`, juntamente com o título do curso correspondente, como a seguir:

| id | titulo | descricao | duracao_minutos | ordem | url_video | curso_id | titulo |
|----|--------|-----------|-----------------|-------|-----------|----------|--------|
| 1  | Aula 1 - Introdução ao Java Avançado | Nesta aula, vamos explorar conceitos avançados de Java. | 30 | 1 | https://www.example.com/java-avancado/aula1 | 1 | Java Avançado |
| 2  | Aula 2 - Generics e Collections | Nesta aula, vamos explorar os conceitos de Generics e Collections em Java. | 45 | 2 | https://www.example.com/java-avancado/aula2 | 1 | Java Avançado |

---

## 📚 Explorando a JPQL (Java Persistence Query Language)

Algumas vezes (algumas muitas vezes 😅), as operações de persistência básicas não são suficientes para atender às necessidades de consulta de uma aplicação. Nesses casos, a JPA oferece a **JPQL (Java Persistence Query Language)**, uma linguagem de consulta orientada a objetos que permite realizar consultas mais complexas e flexíveis em relação às consultas SQL tradicionais.

De forma básica, a JPQL é semelhante ao SQL, mas trabalha com entidades e seus atributos em vez de tabelas e colunas do banco de dados. Isso significa que você pode escrever consultas que retornam objetos Java diretamente, sem precisar se preocupar com a conversão entre os resultados da consulta e os objetos da aplicação. Vejamos um exemplo prático de como utilizar a JPQL para buscar todos os cursos de um determinado instrutor:

```java
// Suponha que você tenha um instrutor com ID 1
Long instrutorId = 1L;

// Criando a consulta JPQL para buscar todos os cursos do instrutor
String jpql = "SELECT c FROM Curso c WHERE c.instrutor.id = :instrutorId";

// Criando a query e definindo o parâmetro
TypedQuery<Curso> query = em.createQuery(jpql, Curso.class);
query.setParameter("instrutorId", instrutorId);

// Executando a consulta e obtendo os resultados
List<Curso> cursosDoInstrutor = query.getResultList();

// Exibindo os cursos encontrados
for (Curso curso : cursosDoInstrutor) {
    System.out.println("Curso: " + curso.getTitulo() + ", Descrição: " + curso.getDescricao());
}
```

No exemplo acima, a consulta JPQL seleciona todos os cursos (`Curso`) cujo `instrutor.id` corresponde ao ID fornecido. A consulta é executada utilizando o método `createQuery()` do `EntityManager`, e os resultados são obtidos como uma lista de objetos `Curso`. Para tipar a consulta, utilizamos `TypedQuery<Curso>`, garantindo que o resultado seja do tipo correto.

Perceba que podemos nomear os parâmetros da consulta utilizando `:nomeDoParametro`, e em seguida, definir o valor do parâmetro com `query.setParameter("nomeDoParametro", valor)`. Isso torna a consulta mais legível e evita problemas de injeção de SQL.

Outro detalhe da sintaxe da JPQL é que ela utiliza os nomes das entidades e atributos definidos nas classes Java, em vez dos nomes das tabelas e colunas do banco de dados. Além disso, note que utilizamos um nome de objeto para a entidade `Curso` na consulta (`c`), o que facilita a referência aos atributos da entidade (`c.instrutor.id`). Isso é especialmente útil em consultas mais complexas, onde você pode ter múltiplas entidades envolvidas.

Em outro exemplo, podemos utilizar a JPQL para buscar todas as aulas de um determinado curso, ordenadas pela ordem das aulas:

```java
// Suponha que você tenha um curso com ID 1
Long cursoId = 1L;

// Criando a consulta JPQL para buscar todas as aulas do curso, ordenadas pela ordem das aulas
String jpql = "SELECT a FROM Aula a WHERE a.curso.id = :cursoId ORDER BY a.ordem ASC";
TypedQuery<Aula> query = em.createQuery(jpql, Aula.class);
query.setParameter("cursoId", cursoId);

// Executando a consulta e obtendo os resultados
List<Aula> aulasDoCurso = query.getResultList();

// Exibindo as aulas encontradas
for (Aula aula : aulasDoCurso) {
    System.out.println("Aula: " + aula.getTitulo() + ", Ordem: " + aula.getOrdem());
}
```

A JPQL também permite realizar consultas mais avançadas, como junções entre entidades, agregações, subconsultas e muito mais. Por exemplo, podemos buscar todos os instrutores que possuem cursos ativos:

```java
String jpql = "SELECT DISTINCT i FROM Instrutor i JOIN i.cursos c WHERE c.ativo = true";
TypedQuery<Instructor> query = em.createQuery(jpql, Instrutor.class);
List<Instructor> instrutoresComCursosAtivos = query.getResultList();
```

> ✨ Não é o foco deste artigo explorar todas as funcionalidades da JPQL, mas é importante que você saiba que ela é uma ferramenta poderosa para realizar consultas complexas em suas aplicações JPA. Recomendo que você consulte a [documentação oficial da JPA](https://jakarta.ee/specifications/persistence/) para aprender mais sobre a JPQL e suas capacidades.

---

## ⚙️ Implementando o Padrão DAO (Data Access Object) com JPA

O padrão DAO _(Data Access Object)_ é um padrão de projeto que visa separar a lógica de acesso a dados da lógica de negócios da aplicação. Ele fornece uma interface para realizar operações de persistência em entidades, permitindo que a aplicação interaja com o banco de dados de forma mais modular e organizada.

Apresentarei aqui uma implementação simples do padrão DAO utilizando a JPA. Caso queira explorar mais sobre o padrão DAO, recomendo a leitura do material que elaborei para a disciplina de Programação Web 1, do curso de Análise e Desenvolvimento de Sistemas: [Roteiro de Prática — Integração com Banco de Dados via JDBC API](https://github.com/lucas-lfm/pweb1-2026.1-base/blob/main/atividades/jdbc-jpa/roteiro-pratica-01.md).

Primeiramente, vamos isolar a lógica de criação de instâncias do `EntityManager` em uma classe utilitária, seguindo a ideia de _Factory Class_, chamada `DBFactory` (no pacote `db`). Essa classe será responsável por fornecer instâncias do `EntityManager` para os DAOs, garantindo que a criação e o gerenciamento do `EntityManager` sejam centralizados:

```java
package com.example.db; // Substitua pelo pacote adequado

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class DBFactory {

  private static final String PERSISTENCE_UNIT = "cursosPU"; // Nome do persistence unit definido no persistence.xml
  private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
      Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);

  private DBFactory() {
  }

  public static EntityManager criarEntityManager() {
    return ENTITY_MANAGER_FACTORY.createEntityManager();
  }

  public static void fechar() {
    if (ENTITY_MANAGER_FACTORY.isOpen()) {
      ENTITY_MANAGER_FACTORY.close();
    }
  }
}
```

Como definimos métodos estáticos na classe `DBFactory`, não é necessário criar uma instância da classe para utilizá-la. Podemos simplesmente chamar `DBFactory.criarEntityManager()` para obter uma nova instância do `EntityManager` sempre que precisarmos realizar operações de persistência.

Crie um novo pacote, chamado `dao`, para as classes e interfaces relacionadas ao padrão DAO em nosso projeto. Vamos definir uma interface genérica, chamada `GenericDAO`, que fornecerá métodos básicos de CRUD para qualquer entidade:

```java
package com.example.dao; // Substitua pelo pacote adequado

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, TypeID> {

  T salvar(T entidade);

  Optional<T> buscarPorId(TypeID id);

  List<T> buscarTodos();

  T atualizar(T entidade);

  void remover(TypeID id);
}
```

**Explicando o código acima:**

- A interface `GenericDao` é genérica, permitindo que seja utilizada para qualquer tipo de entidade (`T`) e tipo de atributo identificador (`TypeID`). A ideia é que você possa criar DAOs específicos para cada entidade, mas mantendo uma estrutura comum para todas as operações de persistência;
- O método `salvar(T entidade)` é responsável por persistir uma nova entidade no banco de dados. Ele retorna a entidade persistida, que pode ter sido modificada pelo JPA (por exemplo, preenchendo o ID gerado);
- O método `buscarPorId(TypeID id)` busca uma entidade pelo seu ID e retorna um `Optional<T>`, permitindo tratar o caso em que a entidade não é encontrada de forma elegante, evitando `NullPointerException`;
- O método `buscarTodos()` retorna uma lista de todas as entidades do tipo `T` presentes no banco de dados. Ele é útil para exibir todos os registros de uma determinada entidade;
- O método `atualizar(T entidade)` é responsável por atualizar uma entidade existente no banco de dados. Ele retorna a entidade atualizada, que pode ter sido modificada pelo JPA durante o processo de atualização;
- O método `remover(TypeID id)` remove uma entidade do banco de dados com base no seu ID. Ele não retorna nenhum valor, mas você pode implementar verificações adicionais para garantir que a entidade foi removida com sucesso.

O próximo passo é criar as implementações concretas do `GenericDao` para cada entidade. Vamos iniciar com a implementação concreta do DAO para a entidade `Instrutor`. Chamaremos essa implementação de `InstrutorDaoImpl`:

```java
package com.example.dao; // Substitua pelo pacote adequado

// Importações necessárias omitidas

public class InstrutorDAOImpl implements GenericDao<Instrutor, Long> {

  @Override
  public Instrutor salvar(Instrutor entidade) {
    // Implementação do método salvar utilizando o EntityManager
  }

  @Override
  public Optional<Instrutor> buscarPorId(Long id) {
    // Implementação do método buscarPorId utilizando o EntityManager
  }

  @Override
  public List<Instrutor> buscarTodos() {
    // Implementação do método buscarTodos utilizando o EntityManager
  }

  @Override
  public Instrutor atualizar(Instrutor entidade) {
    // Implementação do método atualizar utilizando o EntityManager
  }

  @Override
  public void remover(Long id) {
    // Implementação do método remover utilizando o EntityManager
  }

}
```

Vou explicar cada método da implementação do `InstrutorDAOImpl`:

- `salvar(Instrutor entidade)`: Este método é responsável por persistir uma nova entidade `Instrutor` no banco de dados. Ele utiliza o `EntityManager` para iniciar uma transação, persistir a entidade e confirmar a transação. Caso ocorra algum erro durante o processo, a transação é revertida para garantir a consistência do banco de dados. O método retorna a entidade persistida, que pode ter sido modificada pelo JPA (por exemplo, preenchendo o ID gerado).
    ```java
    @Override
    public Instrutor salvar(Instrutor entidade) {
        EntityManager em = DBFactory.criarEntityManager(); // Criando uma instância do EntityManager a partir da DBFactory
        EntityTransaction tx = em.getTransaction(); // Pegando a transação do EntityManager

        try {
            // Iniciando a transação, persistindo a entidade e confirmando a transação
            tx.begin();
            em.persist(entidade);
            tx.commit();

            // Retornando a entidade persistida para quem chamou o método
            return entidade;
        } catch (Exception e) { // Em caso de erro, reverter a transação e lançar a exceção
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally { // O bloco finally garante que o EntityManager seja fechado, mesmo em caso de exceção
            em.close();
        }
    }
    ```

- `buscarPorId(Long id)`: Este método busca uma entidade `Instrutor` pelo seu ID. Ele utiliza o `EntityManager` para buscar a entidade e retorna um `Optional<Instrutor>`, permitindo tratar o caso em que a entidade não é encontrada de forma elegante, evitando `NullPointerException`.
    ```java
    @Override
    public Optional<Instrutor> buscarPorId(Long id) {
        EntityManager em = DBFactory.criarEntityManager(); // Criando uma instância do EntityManager a partir da DBFactory

        try {
            // Buscando a entidade Instrutor pelo ID
            Instrutor instrutor = em.find(Instrutor.class, id);
            return Optional.ofNullable(instrutor); // Retornando um Optional contendo a entidade encontrada ou vazio se não encontrada
        } catch (Exception e) {
            throw e;
        } finally { // O bloco finally garante que o EntityManager seja fechado, mesmo em caso de exceção
            em.close();
        }
    }
    ```
    > A classe `Optional` é um recurso do Java que permite representar a presença ou ausência de um valor de forma mais segura, evitando problemas com `null`. Ao utilizar `Optional`, você pode verificar se o valor está presente antes de acessá-lo, reduzindo o risco de `NullPointerException` em seu código.

- `buscarTodos()`: Este método retorna uma lista de todas as entidades `Instrutor` presentes no banco de dados. Ele utiliza o `EntityManager` para criar uma **consulta JPQL** que seleciona todos os instrutores e retorna os resultados como uma lista.
    ```java
    @Override
    public List<Instrutor> buscarTodos() {
        EntityManager em = DBFactory.criarEntityManager(); // Criando uma instância do EntityManager a partir da DBFactory

        try {
            // Criando a consulta JPQL para buscar todos os instrutores
            String jpql = "SELECT i FROM Instrutor i";
            TypedQuery<Instrutor> query = em.createQuery(jpql, Instrutor.class);

            // Executando a consulta e retornando os resultados como uma lista
            return query.getResultList();
        } catch (Exception e) {
            throw e;
        } finally { // O bloco finally garante que o EntityManager seja fechado, mesmo em caso de exceção
            em.close();
        }
    }
    ```
    > Como a classe `EntityManager` não disponibiliza um método direto para buscar todas as entidades de um determinado tipo, utilizamos a **JPQL** para criar uma consulta que seleciona todos os instrutores.

- `atualizar(Instrutor entidade)`: Este método é responsável por atualizar uma entidade `Instrutor` existente no banco de dados. Ele utiliza o `EntityManager` para iniciar uma transação, mesclar a entidade e confirmar a transação. Caso ocorra algum erro durante o processo, a transação é revertida para garantir a consistência do banco de dados. O método retorna a entidade atualizada, que pode ter sido modificada pelo JPA durante o processo de atualização.
    ```java
    @Override
    public Instrutor atualizar(Instrutor entidade) {
        EntityManager em = DBFactory.criarEntityManager(); // Criando uma instância do EntityManager a partir da DBFactory
        EntityTransaction tx = em.getTransaction(); // Pegando a transação do EntityManager

        try {
            // Iniciando a transação, mesclando a entidade e confirmando a transação
            tx.begin();
            Instrutor instrutorAtualizado = em.merge(entidade);
            tx.commit();

            // Retornando a entidade atualizada para quem chamou o método
            return instrutorAtualizado;
        } catch (Exception e) { // Em caso de erro, reverter a transação e lançar a exceção
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally { // O bloco finally garante que o EntityManager seja fechado, mesmo em caso de exceção
            em.close();
        }
    }
    ```

- `remover(Long id)`: Este método remove uma entidade `Instrutor` do banco de dados com base no seu ID. Ele utiliza o `EntityManager` para iniciar uma transação, buscar a entidade pelo ID, removê-la e confirmar a transação. Caso ocorra algum erro durante o processo, a transação é revertida para garantir a consistência do banco de dados. O método não retorna nenhum valor, mas você pode implementar verificações adicionais para garantir que a entidade foi removida com sucesso.
    ```java
    @Override
    public void remover(Long id) {
        EntityManager em = DBFactory.criarEntityManager(); // Criando uma instância do EntityManager a partir da DBFactory
        EntityTransaction tx = em.getTransaction(); // Pegando a transação do EntityManager

        try {
            // Iniciando a transação
            tx.begin();

            // Buscando a entidade Instrutor pelo ID
            Instrutor instrutorParaRemover = em.find(Instrutor.class, id);
            if (instrutorParaRemover != null) {
                // Removendo a entidade
                em.remove(instrutorParaRemover);
            } else {
                System.out.println("Instrutor não encontrado para remoção.");
            }

            // Confirmando a transação
            tx.commit();
        } catch (Exception e) { // Em caso de erro, reverter a transação e lançar a exceção
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally { // O bloco finally garante que o EntityManager seja fechado, mesmo em caso de exceção
            em.close();
        }
    }
    ```

Perceba que as transações só são necessárias para operações de persistência, atualização e remoção, enquanto as operações de busca podem ser realizadas sem transações. Isso ocorre porque as operações de busca não alteram o estado do banco de dados, enquanto as operações de persistência, atualização e remoção podem modificar os dados armazenados.

Já implementamos todos os métodos da interface `GenericDao` na classe `InstrutorDAOImpl`, garantindo que todas as operações de persistência para a entidade `Instrutor` sejam realizadas de forma consistente. Mas, podemos ir além de criar métodos adicionais para atender a necessidades específicas de consulta ou manipulação de dados. Por exemplo, podemos criar um método para buscar instrutores por nome, utilizando a JPQL:

```java
public List<Instrutor> buscarPorNome(String nome) {
    EntityManager em = DBFactory.criarEntityManager(); // Criando uma instância do EntityManager a partir da DBFactory

    try {
        // Criando a consulta JPQL para buscar instrutores pelo nome
        String jpql = "SELECT i FROM Instrutor i WHERE i.nome LIKE :nome";
        TypedQuery<Instrutor> query = em.createQuery(jpql, Instrutor.class);
        query.setParameter("nome", "%" + nome + "%"); // Configurando o parâmetro da consulta para buscar nomes que contenham a string fornecida em qualquer posição

        // Executando a consulta e retornando os resultados como uma lista
        return query.getResultList();
    } catch (Exception e) {
        throw e;
    } finally { // O bloco finally garante que o EntityManager seja fechado, mesmo em caso de exceção
        em.close();
    }
}
```

**👨‍💻 Agora é com você!**

Tendo a base do padrão DAO implementada, você pode criar DAOs específicos para outras entidades do nosso projeto didático, como `Curso` e `Aula`, seguindo a mesma estrutura apresentada para o `Instrutor`. Isso permitirá que você gerencie as operações de persistência de forma organizada e reutilizável em toda a aplicação.

Pare um pouco e tente implementar os DAOs para as entidades `Curso` e `Aula`, utilizando a mesma abordagem que utilizamos para o `Instrutor`. Desafie-se em criar métodos adicionais conforme necessário, como buscar cursos por título ou aulas por ordem, para atender às necessidades específicas da sua aplicação. Ao concluir, volte aqui e continue a leitura.

---

## ▶️ Testando o padrão DAO implementado

Para finalizar, vamos testar as nossas classes DAO implementadas, garantindo que todas as operações de persistência estejam funcionando corretamente. Utilize a classe `Main` para realizar os testes, criando instâncias das entidades e chamando os métodos dos DAOs. Abaixo está um exemplo de como você pode testar a persistência de um instrutor utilizando o `InstrutorDAOImpl`: 

```java
public class Main {
    public static void main(String[] args) {
        InstrutorDAOImpl instrutorDAO = new InstrutorDAOImpl();

        // Criando um novo instrutor
        Instrutor novoInstrutor = new Instrutor();
        novoInstrutor.setNome("Carlos Eduardo");
        novoInstrutor.setEmail("carlos.eduardo@example.com");
        novoInstrutor.setBiografia("Especialista em desenvolvimento de software e arquitetura de sistemas.");

        // Persistindo o novo instrutor
        instrutorDAO.salvar(novoInstrutor);

        // Buscando o instrutor pelo ID
        Optional<Instrutor> instrutorEncontrado = instrutorDAO.buscarPorId(novoInstrutor.getId());
        if (instrutorEncontrado.isPresent()) {
            System.out.println("Instrutor encontrado: " + instrutorEncontrado.get().getNome());
        } else {
            System.out.println("Instrutor não encontrado.");
        }

        // Atualizando o instrutor
        novoInstrutor.setNome("Carlos E. Silva");
        instrutorDAO.atualizar(novoInstrutor);

        // Removendo o instrutor
        instrutorDAO.remover(novoInstrutor.getId());

        // Fechando a fábrica de EntityManager
        DBFactory.fechar();
    }
}
```

O código acima demonstra como utilizar o `InstrutorDAOImpl` para realizar operações de persistência, incluindo salvar, buscar, atualizar e remover um instrutor. Lembre-se de que, ao testar os DAOs, é importante verificar se as operações de persistência estão sendo realizadas corretamente no banco de dados. Verifique os comandos SQL gerados pelo Hibernate no console e acesse o `pgAdmin` para inspecionar o estado do banco de dados. 

> 😅 Comente a linha `instrutorDAO.remover(novoInstrutor.getId());` para evitar que o instrutor seja removido durante os testes, caso queira verificar se ele foi persistido corretamente.

---

## Próximos Passos

Na próxima parte desta série, iremos:

- Aprender a criar e configurar um projeto com Spring Boot e Spring Data JPA, integrando-o com o banco de dados PostgreSQL;
- Explorar como o Spring Boot simplifica a configuração e o gerenciamento do contexto de persistência;
- Implementar repositórios Spring Data JPA para gerenciar as entidades de forma ainda mais eficiente, aproveitando os recursos oferecidos pelo framework.