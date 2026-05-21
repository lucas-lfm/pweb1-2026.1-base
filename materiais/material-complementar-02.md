# Tutorial de Criação e Configuração de Projeto Java Web

Neste tutorial, vamos aprender a criar um projeto Java Web utilizando a especificação Jakarta EE, o gerenciador de dependências Apache Maven e o servidor de aplicações Apache Tomcat.

Também iremos contruir uma aplicação web simples que utiliza Servlets para processar dados de uma requisições HTTP, enviados por meio de um formulário HTML.

Para isso, utilizaremos o editor de código Visual Studio Code com algumas extensões específicas para desenvolvimento Java (apresentadas no [material complementar 01](./material-complementar-01.md)).

> 🎯 **Objetivo:** Criar e configurar um projeto Java Web com Jakarta EE, implantar a aplicação em um servidor local e implementar um fluxo simples de processamento de uma requisição com Servlets.

---

> [!IMPORTANT]
> Antes de iniciar o processo de criação do projeto, certifique-se de que você tem uma pasta aberta no Visual Studio Code. A extensão `Maven for Java` depende de um _workspace_ ativo para funcionar corretamente. Se você tentar criar um projeto sem ter uma pasta aberta, a extensão pode apresentar erros ou não funcionar como esperado. Portanto, abra a pasta onde deseja criar o projeto antes de seguir os passos deste tutorial.

---

## Parte 1: Criação do Projeto Java Web

1. Abra o Visual Studio Code e acesse a paleta de comandos (`Ctrl+Shift+P`).

2. Procure pela opção `"Java: Create Java Project..."`.

3. Selecione a opção `"Maven"` para criar um projeto utilizando o gerenciador de dependências Apache Maven.

4. Na próxima etapa, escolha a opção `"More..."` para procurar por um template específico, que não aparece na lista padrão.

   ![Painel de seleção de archetype maven, com a opção "More..." destacada.](./imgagens/mc_02/mc2-01.png)

5. Pesquise por `"jakartaee10-minimal"`, Jakarta EE 10 Minimal Maven Archetype, da Eclipse Foundation (_Group ID:_ `org.eclipse.starter`). Na janela seguinte, selecione a versão mais recente disponível (versão `1.1.0`no momento de criação deste material).

   ![Painel de pesquisa de archetype maven, com a opção "jakartaee10-minimal" destacada.](./imgagens/mc_02/mc2-02.png)

> [!IMPORTANT]
> Vou indicar esse archetype por ser mais atual e recomendado para projetos Jakarta EE 10 ou superior, diferente do archetype padrão que estávamos utilizando (`maven-archetype-webapp`).

6. Informe o `Group ID` do projeto, que é um identificador único para o projeto, geralmente seguindo a convenção de nome de domínio invertido (exemplo: `com.exemplo`).

7. Informe o `Artifact ID`, que é o nome do projeto (exemplo: `tutorial_servlets`).

> [!WARNING]
> Neste ponto, a extensão `Maven for Java` pode apresentar um erro indicando que o _workspace_ está vazio. Lembrem do aviso que no início deste tutorial. Você deve estar com alguma pasta aberta no Visual Studio Code para que a extensão funcione corretamente. Se esse for o caso, basta abrir a pasta onde deseja criar o projeto e repetir os passos anteriores.

8. Após isso, o Maven irá executar no terminal e deve solicitar algumas informações como `Version` e `Package`. Informe `0.0.1` como versão e avançe (`Enter`) quando for solicitado o `Package`, para utilizar o valor padrão (já definido através do `Group ID + Artifact ID`).

9. O Maven irá criar a estrutura do projeto e baixar as dependências necessárias.

10. Após a criação do projeto, caso a pasta do projeto não seja aberta automaticamente, acesse a aba de arquivos do Visual Studio Code e abra a pasta do projeto que foi criado. Você deve ver a estrutura do projeto com as pastas `src/main/java`, `src/main/resources`, `src/main/webapp` e o arquivo `pom.xml` na raiz do projeto.

    ![Estrutura do projeto Java Web criado, com as pastas src/main/java, src/main/resources, src/main/webapp e o arquivo pom.xml.](./imgagens/mc_02/mc2-03.png)

---

## Parte 2: Configuração do Servidor de Aplicações e Implantação da Aplicação

> [!IMPORTANT]
> Vamos utilizar o servidor de aplicações Apache Tomcat para implantar e testar nossa aplicação Java Web. Para isso, vamos adicioná-lo de forma embutida no VS Code, através da extensão `Community Server Connector`. Caso ainda não tenha essa extensão instalada, acesse a seção de extensões do VS Code (`Ctrl+Shift+X`) e procure por `Community Server Connector` para instalar.

1. Na seção `Explorador` do Visual Studio Code, acesse a aba de `Servers`.

   ![Seção de servidores do Visual Studio Code, com a extensão "Community Server Connector" instalada.](./imgagens/mc_02/mc2-04.png)

2. Clique no ícone de `+`, na margem direita da aba de `Servers`, para adicionar um novo servidor. Ao ser perguntado se deseja realizar o download do servidor, selecione `Yes`.

   ![Janela de download do servidor, com a opção "Yes" selecionada para baixar o Apache Tomcat.](./imgagens/mc_02/mc2-05.png)

3. Pesquise por `Tomcat` e selecione a versão mais recente disponível (versão `11.0.0-M6` no momento de criação deste material).

4. A licença do Tomcat será exibida, selecione `Continue...` e `Yes (True)` para aceitar os termos e iniciar o download.

   > Se o download for bem-sucedido, avance para o passo 6.

5. Caso haja algum erro no processo de download do servidor, pela extensão `Community Server Connector`, ou queira realizar o download manualmente, você pode fazê-lo através do site oficial do Apache Tomcat (https://tomcat.apache.org/download-11.cgi). Você deve realizar o download do arquivo `zip` (Windows), ou `tar.gz` (Linux/Mac), na seção `Binary Distributions - Core` e extrair o conteúdo do arquivo em uma pasta local.
   ![Download manual do Apache Tomcat](./imgagens/mc_02/mc2-06.png)

Após isso, basta seguir os passos abaixo para adicionar o Tomcat à extensão:

- Clique no ícone de `+`, na margem direita da aba de `Servers`, para adicionar um novo servidor. Ao ser perguntado se deseja realizar o download do servidor, selecione `No, use server on disk`.

- Selecione a pasta onde o Tomcat foi extraído para adicioná-lo à extensão.

- Na tela de configurações do servidor, informe um nome para o servidor (exemplo: `Tomcat 11.x`) e clique no botão `Finish` para finalizar a configuração.
  ![Configuração manual do Apache Tomcat na extensão "Community Server Connector"](./imgagens/mc_02/mc2-07.png)

6. Após a instalação do servidor, o Tomcat estará disponível na seção de `Servers`.

- Clique com o botão direito sobre ele e selecione a opção `Start` para iniciar o servidor.

  ![Iniciando o servidor Apache Tomcat através da extensão "Community Server Connector"](./imgagens/mc_02/mc2-08.png)

---

## Parte 3: Configurando e Implantando o Projeto no Tomcat

1. Antes de implementar nosso primeiro Servlet, vamos realizar uma pequena configuração no arquivo `pom.xml` para garantir que estejamos utilizando a versão apropriada do JDK e da Jakarta EE API. Abra o arquivo `pom.xml` e, então:

- Modifique a propriedade `maven.compiler.release` para `21` (ou a versão do JDK que você está utilizando).

- Verifique se a dependência `jakarta.platform` está presente e em sua versão `10.0.0`, caso não esteja, adicione a seguinte dependência dentro da tag `<dependencies>`:

  ```xml
  <dependency>
      <groupId>jakarta.platform</groupId>
      <artifactId>jakarta.jakartaee-api</artifactId>
      <version>10.0.0</version>
      <scope>provided</scope>
  </dependency>
  ```

> [!IMPORTANT]
> A dependência `jakarta.jakartaee-api` é um _bom coringa_ para projetos Java Web, pois inclui todas as APIs da plataforma Jakarta EE. O escopo `provided` indica que essas dependências serão fornecidas pelo servidor de aplicações (neste caso, o Tomcat), e não precisam ser empacotadas junto com a aplicação.

2. Vamos agora criar uma página HTML simples para coletar o nome do usuário. Crie um arquivo chamado `index.html` dentro da pasta `src/main/webapp` com o seguinte conteúdo:

   ```html
   <!DOCTYPE html>
   <html lang="pt-br">
     <head>
       <meta charset="UTF-8" />
       <meta name="viewport" content="width=device-width, initial-scale=1.0" />
       <title>Formulário de Saudação</title>
     </head>
     <body>
       <h1>Bem-vindo ao Tutorial de Servlets!</h1>
       <form action="greet" method="post">
         <label for="name">Digite seu nome:</label>
         <input type="text" id="name" name="name" required />
         <button type="submit">Enviar</button>
       </form>
     </body>
   </html>
   ```

   > ℹ️ Todo arquivo ou recurso dentro da pasta `src/main/webapp` é considerado parte do conteúdo web da aplicação e será disponibilizado de forma **pública** pelo servidor. O arquivo `index.html` será a página inicial da aplicação, acessível através da URL raiz do servidor.

3. Vamos testar se a página HTML está sendo servida corretamente pelo Tomcat. Para isso, precisamos gerar o arquivo `WAR` da aplicação, que é o formato de empacotamento utilizado para aplicações Java Web tradicionais. No terminal do Visual Studio Code, execute o seguinte comando na raiz do projeto:

   ```bash
   mvn clean package
   ```

   > - 📌 Esse comando irá limpar os arquivos de build anteriores e gerar um novo arquivo `WAR` dentro da pasta `target`.
   > - 🚨 Caso o comando `mvn` não seja reconhecido, certifique-se de que o Maven está instalado e configurado corretamente no seu sistema.

4. Você também pode utilizar a extensão `Maven for Java` para executar os comandos do Maven diretamente pelo Visual Studio Code, através da aba de `Maven`, na seção `Explorador`.

- Na aba `Lifecycle`, você pode encontrar as fases do ciclo de vida do Maven, como `clean`, `compile`, `package`, etc. Basta clicar com o mouse sobre a fase desejada para executá-la.

  ![Executando o comando "package" do Maven através da extensão "Maven for Java"](./imgagens/mc_02/mc2-09.png)

5. Após gerar o arquivo `WAR`, precisamos implantá-lo no Tomcat.

- Para isso, clique com o botão direito do mouse sobre o arquivo `WAR` gerado (dentro da pasta `target`) e selecione a opção `"Run on Server"`, selecionando o servidor Tomcat embutido.

- Se for questionado sobre o uso ou não de parâmetros adicionais, selecione `No`.

6. Nesse momento, o Tomcat irá implantar a aplicação e iniciar o servidor, caso ele ainda não esteja em execução.

- Após a implantação, acesse o navegador de sua preferência e digite a URL padrão `http://localhost:8080/tutorial_servlets/`.

- Se tudo estiver configurado corretamente, você deverá ver a página HTML que criamos, com um formulário para digitar o nome do usuário.

> [!IMPORTANT]
>
> - Certifique-se de que o servidor Tomcat está em execução antes de tentar acessar a aplicação.
> - O nome do contexto da aplicação será o mesmo do `Artifact ID` definido durante a criação do projeto (neste exemplo, `tutorial_servlets`), e é por isso que a URL de acesso inclui esse nome.

---

## Parte 4: Implementando um Servlet para Processar Requisições HTTP

> 📌 Nessa parte, vamos implementar um Servlet para processar as requisições HTTP enviadas por meio de um formulário HTML. O formulário será utilizado para coletar o nome do usuário e enviar os dados para o servidor. Um Servlet irá receber a requisição, processar os dados e retornar uma resposta personalizada para o usuário.

1. Crie um novo pacote dentro da pasta `src/main/java` para organizar as classes do projeto. Por exemplo, você pode criar um pacote chamado `com.exemplo.servlets`.

2. Dentro do pacote criado, crie uma nova classe Java chamada `GreetServlet` com o seguinte conteúdo:

   ```java
    package com.exemplo.servlets;

    import jakarta.servlet.ServletException;
    import jakarta.servlet.annotation.WebServlet;
    import jakarta.servlet.http.HttpServlet;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;

    import java.io.IOException;

    @WebServlet("/greet")
    public class GreetServlet extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String name = request.getParameter("name");

            if (name == null || name.isEmpty()) {
                name = "Visitante";
            }

            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().print("<h1>Olá, " + name + "! Bem-vindo ao tutorial de Servlets!</h1>");
        }
    }
   ```

**Explicando o código acima:**

- A anotação `@WebServlet("/greet")` indica que este Servlet irá responder às requisições enviadas para a URL `/greet`, que é a mesma URL definida no atributo `action` do formulário HTML.
- O método `doPost` é sobrescrito para processar as requisições HTTP do tipo POST. Ele recebe um objeto `HttpServletRequest`, que contém os dados da requisição, e um objeto `HttpServletResponse`, que é utilizado para enviar a resposta de volta ao cliente.
- Dentro do método `doPost`, o nome do usuário é obtido através do método `getParameter("name")`, que recupera o valor do campo `name` do formulário.
- A resposta é configurada para ser do tipo `text/html; charset=UTF-8` e, em seguida, uma mensagem personalizada é escrita no corpo da resposta utilizando o método `getWriter().print()`.
- Nesse exemplo, a resposta é uma simples página HTML que exibe uma saudação personalizada para o usuário.

> [!NOTE]
> Uma classe que extende `HttpServlet` é um Servlet, e os métodos `doGet`, `doPost`, `doPut`, etc., são utilizados para processar diferentes tipos de requisições HTTP. No nosso caso, estamos utilizando o método `doPost` para processar as requisições do formulário, que são enviadas como POST.

3. Após criar o Servlet, é necessário gerar novamente o arquivo `WAR` da aplicação para que as alterações sejam refletidas no servidor. Execute o comando `mvn clean package` novamente para gerar o novo arquivo `WAR`.

> [!IMPORTANT]
>
> - Ao gerar um novo build da aplicação, o arquivo `WAR` antigo é atualizado automaticamente no servidor. Caso isso não ocorra, é necessário reiniciar o servidor (clique com o botão direito no servidor Tomcat e selecione `"Restart in Run Mode"`).

4. Acesse novamente a URL `http://localhost:8080/tutorial_servlets/` no navegador, preencha o formulário com um nome e envie os dados. Você deverá ver a resposta personalizada do Servlet, exibindo uma mensagem de saudação com o nome que você digitou.

---

## Parte 5: Testando outros Tipos de Retorno

### Retornando uma resposta em formato JSON

1. Vamos modificar o Servlet para retornar uma resposta em formato JSON, ao invés de HTML. Para isso, altere o método `doPost` da classe `GreetServlet` para o seguinte código:

   ```java
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");

        if (name == null || name.isEmpty()) {
            name = "Visitante";
        }

        String jsonResponse = """
            {
                "message": "Olá, %s! Bem-vindo ao tutorial de Servlets!"
            }
        """.formatted(name);

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().print(jsonResponse);
    }
   ```

**Explicando o código acima:**

- A linha `response.setContentType("application/json; charset=UTF-8");` configura o tipo de conteúdo da resposta para JSON, indicando que a resposta será um objeto JSON.
- O método `getWriter().print()` é utilizado para escrever a resposta JSON, que neste caso é um objeto com uma única propriedade `message` que contém a saudação personalizada.

> [!NOTE]
>
> - O formato JSON é amplamente utilizado para troca de dados entre cliente e servidor, especialmente em aplicações web modernas. Configurar o tipo de conteúdo corretamente é importante para que o cliente possa interpretar a resposta de forma adequada.
> - Existem diversas bibliotecas Java para facilitar a construção de respostas JSON, como `Jackson` ou `Gson`, mas neste exemplo estamos construindo a resposta JSON manualmente para manter as coisas simples.

2. Após modificar o Servlet, gere novamente o arquivo `WAR` da aplicação e reinicie o servidor, caso necessário, para que as alterações sejam aplicadas.

3. Acesse novamente a URL `http://localhost:8080/tutorial_servlets/` no navegador, preencha o formulário com um nome e envie os dados. Você deverá ver a resposta JSON exibida no navegador, contendo a mensagem de saudação personalizada.

### Retornando um HTML gerado dinamicamente com JSP

> 📝 **JSP (_JavaServer Pages_)** é uma tecnologia Java, uma especificação oficial da plataforma Jakarta EE, que permite a criação de páginas web dinâmicas, onde é possível misturar código Java com HTML para gerar conteúdo dinâmico no servidor. Ele roda no lado do servidor, onde o código Java é processado e transformado em HTML puro antes de ser enviado ao cliente.

> [!NOTE]
> - O uso de JSP é uma abordagem tradicional para gerar conteúdo HTML dinâmico em aplicações Java Web.
> - Embora seja uma tecnologia mais antiga, o JSP ainda é amplamente utilizado em muitos projetos legados e pode ser uma boa opção para aprender os conceitos básicos de geração de conteúdo dinâmico em Java Web.

1. Vamos criar uma página JSP para gerar um HTML dinâmico com a saudação personalizada. Crie um arquivo chamado `greet.jsp` dentro da pasta `src/main/webapp` com o seguinte conteúdo:

   ```html
   <%@ page contentType="text/html; charset=UTF-8" language="java" %>
   <html>
     <head>
       <title>Saudação</title>
     </head>
     <body>
       <h1><%= request.getAttribute("userName") %></h1>
     </body>
   </html>
   ```

2. Modifique o método `doPost` da classe `GreetServlet` para definir um atributo na requisição com o nome do usuário e, em seguida, encaminhar a requisição para a página JSP:

   ```java
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");

        if (name == null || name.isEmpty()) {
            name = "Visitante";
        }

        request.setAttribute("userName", "Olá, " + name + "! Bem-vindo ao tutorial de Servlets!");
        request.getRequestDispatcher("/greet.jsp").forward(request, response);
    }
   ```

**Explicando o código acima:**
- O método `setAttribute` é utilizado para definir um atributo na requisição, que pode ser acessado posteriormente na página JSP. Neste caso, estamos definindo o atributo `userName` com a mensagem de saudação personalizada.
- O método `getRequestDispatcher("/greet.jsp").forward(request, response)` é utilizado para encaminhar a requisição para a página JSP, que irá processar o atributo definido e gerar o HTML dinâmico com a saudação personalizada.
- Na página JSP, o código `<%= request.getAttribute("userName") %>` (chamado de _scriptlet_) é utilizado para acessar o atributo `userName` definido na requisição e exibir a mensagem de saudação personalizada no HTML gerado.

> [!NOTE]
> - O uso de JSP permite separar a lógica de negócios (no Servlet) da apresentação (na página JSP), seguindo o princípio de separação de responsabilidades, o que pode tornar o código mais organizado e fácil de manter.

3. Em aplicações mais modernas que utilizam JSP, o uso de _scriptlets_, como demonstrado no exemplo acima, é desencorajado. 

- Em vez disso, é recomendado utilizar **Expression Language (EL)** e/ou **JSTL (Jakarta Standard Tag Library)** para acessar os atributos e gerar o conteúdo dinâmico de forma mais limpa e segura.

- Veja como ficaria a página JSP utilizando EL para acessar o atributo `userName`:

   ```html
   <%@ page contentType="text/html; charset=UTF-8" language="java" %>
   <html>
     <head>
       <title>Saudação</title>
     </head>
     <body>
       <h1>${userName}</h1>
     </body>
   </html>
   ```

- Nesse exemplo, `${userName}` é a sintaxe da Expression Language para acessar o atributo `userName` definido na requisição, e o resultado será o mesmo que no exemplo anterior, exibindo a mensagem de saudação personalizada no HTML gerado.

4. Para usar JSTL, é necessário adicionar as dependências da JSTL no arquivo `pom.xml`:

    ```xml
    <dependency>
        <groupId>jakarta.servlet.jsp.jstl</groupId>
        <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
        <version>3.0.2</version>
    </dependency>
    <dependency>
        <groupId>org.glassfish.web</groupId>
        <artifactId>jakarta.servlet.jsp.jstl</artifactId>
        <version>3.0.1</version>
    </dependency>
    ```

- Com as dependências da JSTL adicionadas, podemos utilizar as tags da JSTL para acessar os atributos e gerar o conteúdo dinâmico de forma mais limpa e segura. Veja como ficaria a página JSP utilizando JSTL para acessar o atributo `userName`:

    ```html
    <%@ page contentType="text/html; charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <html>
      <head>
         <title>Saudação</title>
      </head>
      <body>
         <h1><c:out value="${userName}" /></h1>
      </body>
    </html>
    ```

- Nesse exemplo, estamos utilizando a tag `<c:out>` da JSTL para escapar o valor do atributo `userName`, garantindo que qualquer conteúdo potencialmente perigoso seja tratado de forma segura, evitando vulnerabilidades como Cross-Site Scripting (XSS).

- O JSTL traz tags adicionais para controle de fluxo, manipulação de dados, formatação, etc., que podem ser muito úteis para criar páginas JSP mais complexas e dinâmicas, sem a necessidade de utilizar código Java diretamente na página.

    - **Exemplo com uso de loops e condicionais utilizando JSTL:**

   ```html
   <%@ page contentType="text/html; charset=UTF-8" language="java" %>
   <%@ taglib prefix="c" uri="jakarta.tags.core" %>
   <html>
     <head>
       <title>Saudação</title>
     </head>
     <body>
       <h1>${userName}</h1>

       <c:if test="${userName != 'Visitante'}">
         <p>Obrigado por visitar nosso tutorial, ${userName}!</p>
       </c:if>

       <ul>
         <c:forEach var="item" items="${someList}">
           <li>${item}</li>
         </c:forEach>
       </ul>
     </body>
   </html>
   ```

    - Nesse exemplo, estamos utilizando a tag `<c:if>` para exibir uma mensagem adicional apenas para usuários que não sejam "Visitante", e a tag `<c:forEach>` para iterar sobre uma lista de itens e exibi-los como elementos de lista.

---

## 👨‍💻 Atividade Proposta

1. Desenvolva um programa Java Web, utilizando Servlets e JSP, que permita aos usuários simularem um processo de login simples. O programa deve conter:

    a. Uma página de login (`login.jsp`) com um formulário para o usuário inserir seu nome de usuário e senha.      

    b. Um Servlet (`LoginServlet`) para processar as requisições de login, verificando se o nome de usuário e senha correspondem a um conjunto pré-definido de credenciais (por exemplo, `admin`/`admin123`).       

    c. Se as credenciais forem válidas, o Servlet deve encaminhar o usuário para uma página de boas-vindas (`welcome.jsp`) que exibe uma mensagem personalizada com o nome do usuário.      

    d. Se as credenciais forem inválidas, o Servlet deve redirecionar o usuário de volta para a página de login, exibindo uma mensagem de erro indicando que o login falhou.        

---

2. Desenvolva um programa Java Web, utilizando Servlets e JSP, que permita aos usuários realizarem uma simulação de financiamento de um veículo. O programa deve conter:

    a. Uma página de simulação (`simulation.jsp`) com um formulário para o usuário inserir o valor do veículo, a entrada, a taxa de juros e o prazo do financiamento.  

    b. Um Servlet (`SimulationServlet`) para processar as requisições de simulação, realizando os cálculos necessários para determinar o valor das parcelas mensais, o valor total pago ao final do financiamento e o valor total de juros pagos.       

    c. O Servlet deve encaminhar o usuário para uma página de resultados (`results.jsp`) que exibe os resultados da simulação de forma clara e organizada, incluindo o valor das parcelas, o valor total pago e o valor total de juros.     

> [!TIP]
> - Utilize o método de amortização PRICE para realizar os cálculos do financiamento, e certifique-se de formatar os valores monetários de forma adequada na página de resultados.