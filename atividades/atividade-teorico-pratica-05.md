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
        <td colspan="3" align="center"><strong>Atividade Teórico-Prática 05: Manutenção de Estado com Cookies e Sessões</strong></td>
    </tr>
</thead>
<tbody>
    <tr>
        <td colspan="3"><b>Instruções:</b></td>
    </tr>
    <tr>
        <td colspan="3">- A prática deve ser resolvida em Java, utilizando os fundamentos da linguagem, de Servlets, JSPs e Padrão MVC (se aplicável).</td>
    </tr>
    <tr>
        <td colspan="3">- A entrega deve ser feita anexando o link do repositório no Classroom. Pode ser utilizado o mesmo repositório geral para a disciplina, caso tenha criado. Nesse caso, utilize uma estrutura de pastas adequada para separar a atividade e envie o link onde encontra-se a pasta desta atividade dentro do repositório.</td>
    </tr>
    <tr>
        <td colspan="3">- O uso de ferramentas de IA deve ser feito com responsabilidade e seguindo o <a href="https://docs.google.com/document/d/1eUUiuaxLibc84h4bAZb6qX0cdZKHAm4akP9JXBxiP-s/edit?usp=sharing" target="_blank">código de conduta da disciplina</a>. Adicione uma seção de Declaração de Uso de IA, caso tenha utilizado, em um arquivo README.md na raiz do projeto.</td>
    </tr>
</tbody>
</table>

---

## 🧑‍💻 Questões Práticas

### 1. Diagnóstico de cookie `HttpOnly` em aplicação Jakarta EE

Durante uma aula prática de Programação Web, uma equipe desenvolveu uma aplicação Jakarta EE com Servlets para autenticação simples. Após o login, o Servlet cria um cookie chamado `JSESSIONID` com os atributos `HttpOnly`, `Secure` e `Path=/loja`. No painel **Application/Storage** do navegador, o cookie aparece corretamente. Entretanto, ao executar `document.cookie` no Console, os estudantes afirmam que “a sessão não foi criada”, pois o valor de `JSESSIONID` não aparece.

Como integrante da equipe, você deve analisar a situação, explicar tecnicamente o comportamento observado e propor um procedimento de verificação para confirmar se a sessão está funcionando corretamente.

---

### 2. Planejamento de autenticação e logout seguro

Uma aplicação acadêmica em Java Web permite que estudantes acessem notas, frequência e dados pessoais. A equipe implementou o login gravando o nome do estudante diretamente em um cookie persistente chamado `usuario`. O cookie não possui `HttpOnly`, não usa `Secure` e permanece válido por 30 dias. No logout, a aplicação apenas redireciona o usuário para a tela inicial, sem invalidar a sessão nem remover cookies.

Você foi solicitado a propor uma solução mais segura usando Jakarta EE e Servlets. Descreva o plano de correção e apresente os trechos essenciais de código para login e logout.

---

### 3. Implementação de preferências de usuário com cookies

Uma plataforma de cursos online deseja lembrar a preferência de idioma do usuário (Português, Inglês ou Espanhol) mesmo após ele fechar o navegador e retornar dias depois. A equipe de desenvolvimento optou por usar cookies para armazenar essa informação.

Como parte da equipe, você deve implementar a funcionalidade de criação e leitura do cookie de preferência de idioma em uma Servlet. Apresente os trechos de código relevantes para:

- Criar um cookie persistente com a preferência de idioma.
- Ler o cookie em requisições subsequentes para aplicar a preferência de idioma, devolvendo o conteúdo com o idioma correspondente.

---

## 📑 Questões Teóricas

## 1. Qual das alternativas descreve corretamente a principal função de um cookie?

a) Armazenar objetos Java no servidor para compartilhamento entre usuários.

b) Permitir que o servidor execute código JavaScript no navegador do cliente.

c) Armazenar pequenas informações no navegador do usuário para que possam ser reutilizadas em requisições futuras.

d) Criar conexões permanentes entre cliente e servidor.

---

## 2. Considere o seguinte cenário: um usuário acessa uma loja virtual, adiciona produtos ao carrinho e navega por diversas páginas antes de finalizar a compra.

Qual mecanismo é mais adequado para armazenar temporariamente os itens do carrinho durante a navegação?

a) Cookies contendo todos os dados dos produtos.

b) Variáveis locais de cada Servlet.

c) Sessão HTTP associada ao usuário.

d) Arquivos HTML armazenados no navegador.

---

## 3. Sobre o protocolo HTTP, assinale a alternativa correta.

a) O HTTP mantém automaticamente o histórico completo das interações de cada usuário.

b) O HTTP é um protocolo orientado a estado.

c) O HTTP é considerado um protocolo stateless (sem estado).

d) O HTTP utiliza sessões automaticamente sem necessidade de configuração.

---

## 4. Ao criar um cookie em uma Servlet utilizando a API Java, qual método é utilizado para enviar o cookie ao navegador do cliente?

a) `request.addCookie(cookie)`

b) `response.addCookie(cookie)`

c) `session.addCookie(cookie)`

d) `cookie.send()`

---

## 5. Uma plataforma de cursos online deseja lembrar o idioma preferido do usuário mesmo após ele fechar o navegador e retornar dias depois.

Qual solução é mais apropriada?

a) Armazenar a preferência em um atributo da requisição (`request`).

b) Armazenar a preferência em uma variável local da Servlet.

c) Armazenar a preferência em um cookie persistente.

d) Armazenar a preferência exclusivamente em uma sessão HTTP.

**Justifique brevemente sua escolha.**

---

## 6. Analise as afirmações a seguir sobre sessões HTTP:

I. Cada usuário normalmente possui sua própria sessão.

II. Os dados da sessão são armazenados no servidor.

III. A sessão pode ser encerrada automaticamente após um período de inatividade.

Assinale a alternativa correta.

a) Apenas I.

b) Apenas I e II.

c) Apenas II e III.

d) I, II e III.

---

## 7. Uma empresa desenvolveu um sistema acadêmico onde cada aluno, ao realizar login, recebe um identificador de sessão único. O sistema utiliza esse identificador para recuperar informações do aluno durante toda a navegação.

Qual é o principal benefício dessa abordagem?

a) Reduzir o tamanho das páginas HTML.

b) Evitar que o servidor precise armazenar informações do usuário.

c) Permitir a identificação e manutenção do contexto do usuário entre múltiplas requisições.

d) Eliminar a necessidade de autenticação.

---

## 8. Quando um navegador realiza uma nova requisição para um servidor, o que normalmente acontece com os cookies previamente armazenados para aquele domínio?

a) São apagados automaticamente.

b) São enviados junto à requisição, desde que atendam às regras de domínio, caminho e validade.

c) São convertidos em atributos de sessão.

d) São enviados apenas quando o usuário realiza login.

---

## 9. Um desenvolvedor decide armazenar em um cookie informações sensíveis, como CPF, senha e nível de acesso do usuário, em texto puro.

Qual é o principal problema dessa decisão?

a) Os cookies não podem armazenar texto.

b) O navegador não consegue recuperar cookies após fechar a aba.

c) Informações armazenadas no cliente podem ser visualizadas, modificadas ou utilizadas indevidamente, comprometendo a segurança da aplicação.

d) O servidor não consegue ler cookies enviados pelo navegador.

---

## 10. Uma startup desenvolveu uma plataforma web para gerenciamento de eventos. Durante os testes, a equipe identificou dois requisitos:

* O sistema deve manter o usuário autenticado enquanto ele navega entre as páginas da aplicação.
* O sistema deve lembrar a preferência de tema (claro ou escuro) mesmo após o usuário fechar o navegador e retornar dias depois.

Como arquiteto de software da equipe, explique quais mecanismos de gerenciamento de estado devem ser utilizados para atender cada requisito. Em sua resposta:

* Diferencie cookies e sessões HTTP;
* Explique onde os dados são armazenados em cada caso;
* Justifique por que cada tecnologia é mais adequada ao requisito correspondente;
* Discuta brevemente aspectos de segurança relacionados à solução proposta.

**Valorize a clareza da argumentação e o uso correto dos conceitos estudados em aula.**
