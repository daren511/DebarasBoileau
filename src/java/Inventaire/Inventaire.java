/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package Inventaire;

import ConnexionUser.ConnectionOracle;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author Alexandre
 */
@WebServlet(name = "Inventaire", urlPatterns = {"/Inventaire"})
public class Inventaire extends HttpServlet {
    private HttpSession session;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            session = request.getSession();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            ecrireTete(out,"Inventaire");
            out.println("<body>");
            out.println("<div class=\"Catalogue\">");
            out.println("<a href=\"/DebarasBoileau/Catalogue\"><img src='Images/titre1.png' height='124' width='573'/></a>");
            out.println("<div class=\"connexion\">");
            out.println("<a href=\"/DebarasBoileau/Panier\"><img src='Images/Panier.png'  height='32' width='32'></a>");
            out.println("<table><tr><td> Bienvenue à vous "+session.getAttribute("User"));
            out.println("<br>Vous avez " +session.getAttribute("Ecus")+" Ecus");
            out.println("<br><form action=\"logout\" method=\"GET\"><button id=\"btndeconnexion\" type=\"submit\" class=\"BTN_Deconnexion\">Se déconnecter</button></form>");
            out.println("</table>");
            out.println("</div>");
            out.println("<div class='Liste'>");
            out.println("<table id='ObjectList' style=\"margin-left: 200px;\">");
            out.println("<tr>");
            out.println("<th>Nom d'item</th>");
            out.println("<th>Genre</th>");
            out.println("<th>Prix</th>");
            out.println("<th>Quantité</th>");
            out.println("</tr>");
            ListerInventaire(out);
            out.println("</table>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    private void ecrireTete(PrintWriter writer, String Titre){
        writer.println("<head>");
        writer.println("<meta charset=\"utf-8\" />");
        writer.println("<title>"+Titre+"</title>");
        writer.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"WebApp.css\">");
        writer.println("</head>");
    }
    
    private void ListerInventaire(PrintWriter out){
        try
        {
            //Connexion DB
            ConnectionOracle oradb = new ConnectionOracle();
            oradb.connecter();
            CallableStatement stm1;
            // Déclaration
            stm1 = oradb.getConnexion().prepareCall("{? = call GESTIONINVENTAIRE.LISTERINVENTAIRE(?)}",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            stm1.registerOutParameter(1, OracleTypes.CURSOR);
            stm1.setString(2, (String)session.getAttribute("User"));
            stm1.execute();
            ResultSet rstTous =(ResultSet)stm1.getObject(1);
            
            
            while (rstTous.next())
            {
                out.println("<tr><td>"+rstTous.getString(3).toString()+"</td>"+"<td>"+rstTous.getString(4).toString()+"</td>"+"<td>"+rstTous.getString(5).toString()+"</td>"+"<td>"
                        + rstTous.getString(6).toString()+"</td>");
            }
        }
    catch(SQLException sqlex){ System.out.println(sqlex);}
}

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
/**
 * Handles the HTTP <code>GET</code> method.
 *
 * @param request servlet request
 * @param response servlet response
 * @throws ServletException if a servlet-specific error occurs
 * @throws IOException if an I/O error occurs
 */
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
processRequest(request, response);
}

/**
 * Handles the HTTP <code>POST</code> method.
 *
 * @param request servlet request
 * @param response servlet response
 * @throws ServletException if a servlet-specific error occurs
 * @throws IOException if an I/O error occurs
 */
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
processRequest(request, response);
}

/**
 * Returns a short description of the servlet.
 *
 * @return a String containing servlet description
 */
@Override
public String getServletInfo() {
return "Short description";
}// </editor-fold>

}
