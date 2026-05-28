package com.proflucasmendes.exemplo_mvc.servlets;

import java.io.IOException;

import com.proflucasmendes.exemplo_mvc.model.Usuario;
import com.proflucasmendes.exemplo_mvc.model.UsuarioService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * CAMADA CONTROLLER — Padrão MVC
 *
 * O Controller é o elo entre a View (HTML/JSP) e o Model (dados/regras).
 * Sua responsabilidade é:
 *   1. Receber a requisição HTTP enviada pelo navegador;
 *   2. Extrair os dados do formulário (parâmetros da requisição);
 *   3. Delegar o processamento ao Model (UsuarioService e Usuario, nesse caso);
 *   4. Encaminhar o resultado para a View (JSP, por exemplo) para exibição.
 *
 * Em Jakarta EE, o Controller é implementado como um Servlet —
 * uma classe Java que estende HttpServlet e é mapeada para uma URL
 * via @WebServlet. O container (ex.: Tomcat) gerencia seu
 * ciclo de vida: instanciação, inicialização (init) e destruição (destroy).
 *
 */
@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

  // O service é declarado como atributo estático e final:
  // - "static": há apenas uma instância compartilhada (condizente com o singleton do Servlet)
  // - "final": a referência não pode ser substituída acidentalmente
  private static final UsuarioService service = new UsuarioService();

  /**
   * Trata requisições HTTP do tipo POST — acionadas pelo envio do formulário HTML.
   *
   * Fluxo MVC:
   *   View (index.html) → POST /usuarios → Controller (este método)
   *     → Model (UsuarioService.cadastrar) → Controller
   *       → View (JSP de confirmação, via forward ou redirect)
   *
   * req.getParameter() lê os campos do formulário HTML pelo atributo "name".
   * Ex.: <input name="nome"> → req.getParameter("nome")
   *
   * Para encaminhar o resultado a uma JSP, usaríamos:
   *   req.setAttribute("usuario", novoUsuario);           // disponibiliza dado para a JSP
   *   req.getRequestDispatcher("/confirmacao.jsp")        // seleciona a View
   *      .forward(req, resp);                             // faz o forward (server-side)
   *
   * Ou, para evitar reenvio do formulário (padrão PRG — Post/Redirect/Get):
   *   resp.sendRedirect(req.getContextPath() + "/confirmacao");
   */
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // 1. Extrai os parâmetros enviados pelo formulário HTML (View)
    String nome = req.getParameter("nome");
    String email = req.getParameter("email");

    // 2. Delega a lógica de negócio ao Model
    Usuario novoUsuario = service.cadastrar(nome, email);

    // 3. TODO: encaminhar o resultado para a View (JSP)

  }

}
