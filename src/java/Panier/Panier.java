/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Panier;

import ConnexionUser.ConnectionOracle;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author 200666155
 */
@WebServlet(name = "Panier", urlPatterns = {"/Panier"})
public class Panier extends HttpServlet {
   private HttpSession session;
   private double Total;

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
         ecrireTete(out,"Panier");
         out.println("<body>");
         out.println("<div class=\"Catalogue\">");
         out.println("<a href=\"/DebarasBoileau/Catalogue\"><img src='Images/titre1.png' height='124' width='573'/></a>");
         out.println("<div class=\"connexion\">");
         out.println("<table><tr><td> Bienvenue à vous "+session.getAttribute("User"));
         out.println("<br>Vous avez " +session.getAttribute("Ecus")+" Ecus");
         out.println("<br><form action=\"logout\" method=\"GET\"><button id=\"btndeconnexion\" type=\"submit\" class=\"BTN_Deconnexion\">Se déconnecter</button></form>");
         out.println("</table>");
         out.println("</div>");
         out.println("</div>");
         out.println("<div class='Liste'>");
         out.println("<table id='ObjectList'>");
         out.println("<tr>");
         out.println("<th>Nom d'item</th>");
         out.println("<th>Genre</th>");
         out.println("<th>Prix</th>");
         out.println("<th>Disponible</th>");
         out.println("<th>Quantité</th>");
         out.println("<th></th>");
         out.println("</tr>");
         listerPanier(out);
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
    
    protected void listerPanier(PrintWriter out){
      try
      {
         //Connexion DB
         ConnectionOracle oradb = new ConnectionOracle();
         oradb.connecter();
         // Déclaration
         String sqlpanier = "select J.NOMUSAGER,P.IDITEM,NOMITEM,GENRE,PRIX,QUANTITEDISPO,QUANTITE from PANIER P INNER JOIN ITEMS I ON P.IDITEM=I.IDITEM INNER JOIN JOUEURS J ON J.IDJOUEUR=P.IDJOUEUR WHERE J.NOMUSAGER='" + session.getAttribute("User") + "'";
         ResultSet rstTous;
         // Lister les items
         Statement stm2 = oradb.getConnexion().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
         rstTous = stm2.executeQuery(sqlpanier);
         
         rstTous.beforeFirst();
         
         while (rstTous.next())
         {
            Total = rstTous.getDouble(5) * rstTous.getDouble(7); 
            out.println("<tr><td>"+rstTous.getString(3).toString()+"</td>"+"<td>"+rstTous.getString(4).toString()+"</td>"+"<td>"+rstTous.getString(5).toString()+"</td>"+"<td>"
                    + rstTous.getString(6).toString()+"</td>" + "<td><form action=\"PanierModif\" method=\"POST\"><input type=number name=TB_Quantite value=" + rstTous.getString(7).toString()+ "></input></td>");
            out.println("<td><input type=\"submit\" id=\"btnmodifier\" class=\"BTN_Modifier\" value=\"Modifier\"></input><input type=\"hidden\" value=" + rstTous.getString(2).toString()+ " name=\"Item\"></input></form>");
             out.println("<form action=\"PanierSupp\" method=\"POST\"><input type=\"submit\" id=\"btnsupprimer\" class=\"BTN_Supprimer\" value=\"Supprimer\"></input><input type=\"hidden\" value=" + rstTous.getString(2).toString()+ " name=\"Item\"></form></td>");
            out.println("</tr>");
         }
         //out.println("<div>" + Total + "</div>");
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
        System.out.println("GET");
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
