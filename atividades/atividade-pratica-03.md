<table style="width: 100%; margin: 0 auto;">
<thead>
    <tr>
        <td rowspan="2"><img src="./logo_taua_simples.png" style="width: 200px; margin: 0 auto"></td>
        <td colspan="2" align="center"><b>INSTITUTO FEDERAL DO CEARÁ - CAMPUS TAUÁ<br>
                        ANÁLISE E DESENVOLVIMENTO DE SISTEMAS</b>
        </td>
    </tr>
    <tr>
        <td><b>Professor:</b> Me. Lucas Mendes</td>
        <td><b>Disciplina:</b> Programação Web I<br>
            <b>Turma:</b> S3
        </td>
    </tr>
    <tr>
        <td colspan="3" align="center"><strong>Atividade Teórico-Prática: Arquitetura de Aplicações Web com Servlets e JSPs</strong></td>
    </tr>
</thead>
<tbody>
    <tr>
        <td colspan="3"><b>Instruções:</b></td>
    </tr>
    <tr>
        <td colspan="3">- Todas as questões práticas devem ser resolvidas em Java, utilizando os fundamentos da linguagem, de Servlets e JSPs.</td>
    </tr>
    <tr>
        <td colspan="3">- A entrega deve ser feita anexando o link do repositório no Classroom. Pode ser utilizado o mesmo repositório geral para a disciplina, caso tenha criado. Ou, ainda, criar um repositório específico para esta atividade.</td>
    </tr>
    <tr>
        <td colspan="3">- O uso de ferramentas de IA deve ser feito com responsabilidade e seguindo o <a href="https://docs.google.com/document/d/1eUUiuaxLibc84h4bAZb6qX0cdZKHAm4akP9JXBxiP-s/edit?usp=sharing" target="_blank">código de conduta da disciplina</a>. É obrigatório uma seção final com uma declaração de uso de IA, conforme item 3 do código de conduta mencionado, caso você tenha utilizado alguma ferramenta de IA nesta atividade, mesmo que somente como apoio ao estudo do conteúdo.</td>
    </tr>
</tbody>
</table>

---

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

---

## Questões Teóricas

> [!NOTE] 
> As questões a seguir devem ser respondidas em um documento README.md, em uma pasta `docs` no repositório. Utilize linguagem clara e objetiva, demonstrando compreensão dos conceitos relacionados a Servlets, JSPs e a arquitetura de aplicações Web.

3. A arquitetura de uma aplicação Java Web utilizando Servlets e JSPs é baseada no modelo MVC (Model-View-Controller). Explique o papel de cada componente (Model, View e Controller) nessa arquitetura e como eles interagem entre si para processar as requisições dos usuários e gerar as respostas adequadas.

---

4. O protocolo HTTP é baseado em um modelo de requisição-resposta. Explique como as requisições HTTP são processadas em uma aplicação Java Web utilizando Servlets, desde o momento em que o usuário envia uma requisição até o momento em que a resposta é gerada e enviada de volta ao cliente. Inclua detalhes sobre o ciclo de vida de um Servlet, como ele lida com os diferentes tipos de requisições (GET, POST, etc.) e como podemos configurar cabeçalhos HTTP, como o `Content-Type`.

---

5. Uma das características importantes de uma aplicação Web é a forma como o conteúdo de resposta é gerado e formatado. Com base nisso, no ecosistema de desenvolvimento Java Web (com Servlets e JSPs), responda:

    a) Explique como arquivos estáticos (como HTML, CSS e JavaScript) e arquivos dinâmicos (como JSPs) são utilizados em uma aplicação Java Web para gerar o conteúdo de resposta. 

    b) Qual pasta do projeto é geralmente usada para armazenar arquivos estáticos? Por padrão, os arquivos estáticos, dentro dessa pasta, são acessíveis publicamente?

    c) Qual pasta do projeto é geralmente usada para armazenar arquivos dinâmicos (JSPs), que representam a camada de visualização? Por padrão, os arquivos JSPs, dentro dessa pasta, são acessíveis publicamente?