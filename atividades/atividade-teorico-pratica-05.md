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
        <td colspan="3" align="center"><strong>Atividade Teórico-Prática: Manutenção de Estado com Cookies e Sessões</strong></td>
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

## 📑 Questões Teóricas

### 3. Estado em aplicações Web

Uma empresa desenvolve um sistema de compras on-line. O cliente pode navegar anonimamente, adicionar produtos ao carrinho, autenticar-se no meio do processo e finalizar a compra. A equipe de desenvolvimento discute três alternativas: armazenar todo o carrinho em cookies no navegador, armazenar apenas um identificador de sessão em cookie e manter o carrinho no servidor, ou enviar todos os dados do carrinho em parâmetros de URL a cada requisição.

Considerando os princípios de funcionamento do HTTP, cookies e sessões, analise qual alternativa é mais adequada para uma aplicação Web com requisitos de segurança, integridade dos dados e experiência do usuário. Justifique sua resposta.

---

### 4. Manutenção de sessão e segurança

Uma aplicação institucional em Jakarta EE utiliza Servlets para autenticação de usuários. Após uma auditoria, observou-se que o identificador de sessão era mantido em cookie sem os atributos `HttpOnly` e `Secure`. Além disso, após o login, o identificador da sessão não era renovado. A equipe argumentou que “não havia problema”, pois a aplicação validava usuário e senha corretamente.

Considerando boas práticas de gerenciamento de sessão em aplicações Web, assinale a alternativa correta.

A. A validação correta de usuário e senha elimina a necessidade de proteger o identificador de sessão, pois a sessão é apenas um recurso interno do servidor.

B. O atributo `HttpOnly` impede que o cookie seja enviado em requisições HTTP, por isso não deve ser usado em cookies de sessão.

C. A ausência de renovação do identificador após o login pode favorecer ataques de fixação de sessão, e atributos como `HttpOnly` e `Secure` reduzem riscos de exposição do cookie.

D. O atributo `Secure` criptografa o conteúdo do cookie no navegador, dispensando o uso de HTTPS na aplicação.

E. Cookies de sessão não precisam de proteção quando a aplicação utiliza Servlets, pois o contêiner Jakarta EE bloqueia automaticamente todos os acessos indevidos.

---

### 5. Privacidade, minimização e responsabilidade arquitetural

Uma startup de educação digital pretende personalizar a experiência dos estudantes em sua plataforma Web. Para isso, propõe armazenar em cookies informações como nome completo, curso, preferências de tema, identificador interno, último acesso, perfil de aprendizagem e lista parcial de disciplinas. Um dos desenvolvedores afirma que essa abordagem simplifica a aplicação, pois evita consultas ao banco de dados e reduz uso de memória no servidor.

Analise criticamente essa proposta sob a perspectiva de arquitetura Web, privacidade, segurança e manutenção. Indique quais dados poderiam eventualmente ser mantidos em cookies e quais deveriam permanecer no servidor.