package br.ifsp.salto.tecinfo.admser;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet; // Usando anotação para mapeamento
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/convert") // Mapeia este servlet para a URL /convert
public class ConverterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String numberValueStr = request.getParameter("numberValue");
        String fromBase = request.getParameter("fromBase");
        String toBase = request.getParameter("toBase");

        String originalNumberForDisplay = numberValueStr; // Guardar para exibição
        String result = "";
        String error = "";
        long numberToConvertDecimal = 0;

        // Início da página HTML de resposta
        out.println("<!DOCTYPE html><html lang=\"pt-BR\"><head><meta charset=\"UTF-8\"><title>Resultado da Conversão</title>");
        out.println("<style>" +
                "body { font-family: 'Inter', Arial, sans-serif; margin: 20px; background-color: #f0f2f5; color: #333; display: flex; justify-content: center; align-items: center; min-height: 90vh; text-align:center; }" +
                ".container { background-color: #fff; padding: 25px 30px; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); max-width: 550px; width: 100%;}" +
                "h1 { color: #1d2d3f; margin-bottom:20px; }" +
                "p { font-size: 1.1em; margin: 10px 0; line-height:1.5; }" +
                ".error { color: #dc3545; font-weight: bold; background-color: #f8d7da; padding:10px; border-radius:4px; border:1px solid #f5c6cb;}" +
                ".success { color: #155724; font-weight: bold; background-color: #d4edda; padding:10px; border-radius:4px; border:1px solid #c3e6cb;}" +
                "a.button-link { display: inline-block; margin-top: 25px; padding: 12px 20px; background-color: #007bff; color: white; text-decoration: none; border-radius: 6px; font-size:16px; }" +
                "a.button-link:hover { background-color: #0056b3; }" +
                "strong { color: #007bff; }" +
                "</style>");
        out.println("</head><body><div class='container'>");
        out.println("<h1>Resultado da Conversão</h1>");

        if (numberValueStr == null || numberValueStr.trim().isEmpty()) {
            error = "Por favor, insira um número para converter.";
        } else {
            numberValueStr = numberValueStr.trim(); // Remover espaços
            try {
                // Validação e conversão para decimal
                switch (fromBase) {
                    case "decimal":
                        if (!numberValueStr.matches("-?\\d+")) throw new NumberFormatException("Número decimal inválido. Use apenas dígitos (0-9) e opcionalmente '-' no início.");
                        numberToConvertDecimal = Long.parseLong(numberValueStr);
                        break;
                    case "binary":
                        if (!numberValueStr.matches("[01]+")) throw new NumberFormatException("Número binário inválido. Use apenas 0s e 1s.");
                        numberToConvertDecimal = Long.parseLong(numberValueStr, 2);
                        break;
                    case "hexadecimal":
                        if (!numberValueStr.matches("[0-9a-fA-F]+")) throw new NumberFormatException("Número hexadecimal inválido. Use apenas 0-9 e a-f (ou A-F).");
                        numberToConvertDecimal = Long.parseLong(numberValueStr, 16);
                        break;
                    default:
                        error = "Base de origem inválida selecionada.";
                }

                if (error.isEmpty()) { // Prossiga para a conversão final se não houver erro na entrada
                    switch (toBase) {
                        case "decimal":
                            result = Long.toString(numberToConvertDecimal);
                            break;
                        case "binary":
                            result = Long.toBinaryString(numberToConvertDecimal);
                            break;
                        case "hexadecimal":
                            result = Long.toHexString(numberToConvertDecimal).toUpperCase();
                            break;
                        default:
                            error = "Base de destino inválida selecionada.";
                    }
                }
            } catch (NumberFormatException e) {
                error = "Erro de Formato: " + e.getMessage() + "<br>Verifique se o número fornecido (<strong>" + originalNumberForDisplay + "</strong>) corresponde à base de origem (<strong>" + fromBase + "</strong>). Números muito grandes também podem causar erro.";
            } catch (Exception e) {
                error = "Ocorreu um erro inesperado durante a conversão: " + e.getMessage();
            }
        }

        if (!error.isEmpty()) {
            out.println("<div class='error'><p>" + error + "</p></div>");
        } else {
            out.println("<div class='success'>");
            out.println("<p>Número Original: <strong>" + originalNumberForDisplay + "</strong> (Base: " + fromBase + ")</p>");
            out.println("<p>Convertido para Base: <strong>" + toBase + "</strong></p>");
            out.println("<p>Resultado: <strong>" + result + "</strong></p>");
            out.println("</div>");
        }
        // Usa getContextPath() para construir o link de volta para a raiz da aplicação
        String contextPath = request.getContextPath(); 
        out.println("<br><a href='" + response.encodeURL(contextPath + "/") + "' class='button-link'>Nova Conversão</a>");
        out.println("</div></body></html>");
        out.close();
    }
}
