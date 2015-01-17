<%-- 
    Document   : TermoSinonimos
    Created on : 05/07/2009, 18:40:56
    Author     : lefoly
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"
        import="java.sql.*, conexao.*, dominio.*, java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<% String idDisciplina = request.getParameter("idDisciplina");
            String nome = request.getParameter("nome");
            String idTermo = request.getParameter("idTermo");
            String idProfessor = request.getParameter("idProfessor");
            int id;
            String descricao = "";

            Termo termo = new Termo();
            TermoDAO dao = new TermoDAOImp();
            List<Termo> termos = dao.getLista();
            for (int i = 0; i <= termos.size() - 1; i++) {
                if (termos.get(i).getID() == Integer.parseInt(idTermo)) {
                    nome = termos.get(i).getNome();
                }
            }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Cadastro de Sinônimos</title>
    </head>
    <body>

        <table style="text-align: left; width: 100%; " border="0">
            <tbody>
                <tr>
                    <td align="left"><h3>LO: <%=nome%></h3></td>
                    <td></td>
                    <td align="right"><a href="./Termos.jsp?idDisciplina=<%=idDisciplina%>&nome=<%=nome%>&idProfessor=<%=idProfessor%>">Voltar para LOs</a></td>
                </tr>
            </tbody>
        </table>

        <table style="text-align: left; width: 100%; " border="1">
            <thead>
                <tr>
                    <th><a href="./TermoConceito.jsp?idDisciplina=<%= idDisciplina%>&nome=<%= nome%>&idTermo=<%=idTermo%>&idProfessor=<%=idProfessor%>">Definição</a></th>
                    <th><a href="./TermoExemplos.jsp?idDisciplina=<%= idDisciplina%>&nome=<%= nome%>&idTermo=<%=idTermo%>&idProfessor=<%=idProfessor%>">Exemplos</a></th>
                    <th><a href="./TermoSinonimos.jsp?idDisciplina=<%= idDisciplina%>&nome=<%= nome%>&idTermo=<%=idTermo%>&idProfessor=<%=idProfessor%>">Sinônimos</a></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td colspan="4" rowspan="1">

                        <h3>Sinônimos Cadastrados</h3>
                        <table border="1">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Descrição</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
<%
            Sinonimo sinonimo = new Sinonimo();
            SinonimoDAO daoS = new SinonimoDAOImp();
            List<Sinonimo> sinonimos = daoS.getListaFiltrada(Integer.parseInt(idTermo));
            for (int i = 0; i <= sinonimos.size() - 1; i++) {
                sinonimo = sinonimos.get(i);
                id = sinonimo.getID();
                descricao = sinonimo.getDescricao();
%>
                                <tr>
                                    <td><%= id%></td>
                                    <td><%= descricao%></td>
                                    <td><form action="CadSinonimo?cmd=excluir" method="POST">
                                            <input type="hidden" name="idDisciplina" value="<%=idDisciplina%>">
                                            <input type="hidden" name="nome" value="<%=idProfessor%>">
                                            <input type="hidden" name="id" value="<%=id%>">
                                            <input type="hidden" name="idTermo" value="<%=idTermo%>">
                                            <input type="submit" value="Excluir" name="Bexcluir" />
                                        </form></td>

                                </tr>
<%          }            
%>
                            </tbody>
                        </table>
                        <br>
                        <br>
                        <form action="CadSinonimo?cmd=incluir" method="POST">
                            Sinônimos: <br>
                            <input type="hidden" name="idDisciplina" value="<%=idDisciplina%>" />
                            <input type="hidden" name="idProfessor" value="<%=idProfessor%>" />
                            <input type="hidden" name="nome" value="<%=nome%>" />
                            <input type="hidden" name="idTermo" value="<%=idTermo%>" />
                            <input type="text" name="descricao" value="" />
                            <input type="submit" value="Cadastrar" />
                        </form>
                    </td>
                </tr>
            </tbody>
        </table>
    </body>
</html>
