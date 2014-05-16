/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Catalogue;

import ConnexionUser.ConnectionOracle;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oracle.jdbc.*;
import oracle.jdbc.pool.*;
import java.sql.*;

/**
 *
 * @author 201106779
 */
@WebServlet(name = "Catalogue", urlPatterns = {"/Catalogue"})
public class Catalogue extends HttpServlet {

   /**
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
    * methods.
    *
    * @param request servlet request
    * @param response servlet response
    * @throws ServletException if a servlet-specific error occurs
    * @throws IOException if an I/O error occurs
    */
   protected void processRequestTous(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
      try (PrintWriter out = response.getWriter()) {
         /* TODO output your page here. You may use following sample code. */
         out.println("<!DOCTYPE html>");
         out.println("<html>");
         ecrireTete(out,"Catalogue");
         out.println("<body>");
         out.println("<div class=\"Catalogue\">");
         out.println("<img src='Images/titre1.png' height='124' width='573'/></a>");
         out.println("<div class=\"connexion\">");
         out.println("<form action=\"ConnexionUser\" method=\"POST\">");
         out.println("<table>");
         out.println("<tr><td> Nom d'usager : </td><td> <input id=\"Username\" type=\"text\" class=\"Text_Box\" name=\"User\" /> </td></tr>");
         out.println("<tr><td> Mot de passe : </td><td> <input id=\"Password\" type=\"password\" class=\"Text_Box\" name=\"Password\" /> </td></tr>");
         out.println("<tr><td><button id=\"btnconnexion\" type=\"submit\" class=\"BTN_Connexion\">Se connecter</button></td></tr>");
         out.println("</table>");
         out.println("</br>");
         out.println("</form>");
         out.println("</div>");
         out.println("<form class=\"CB\" action=\"Catalogue\" method=\"POST\">");
         out.println("<div>");
         out.println("<select name=\"Genre\">");
         out.println("<option value='Tous'>Tous</option>");
         out.println("<option value='Armes'>Armes</option>");
         out.println("<option value='Armures'>Armures</option>");
         out.println("<option value='Potions'>Potions</option>");
         out.println("<option value='Habilités'>Habilites</option>");
         out.println("</select>");
         out.println("<button id='btnfiltrer' type=\"submit\" class='BTN_Filtrer'>Filtrer Inventaire</button>");
         out.println("</div>");
         out.println("</form>");
         out.println("<div class='Liste'>");
         out.println("<table id='ObjectList'>");
         out.println("<tr>");
         out.println("<th>Nom d'item</th>");
         out.println("<th>Genre</th>");
         out.println("<th>Prix</th>");
         out.println("<th>Quantité disponible</th>");
         out.println("</tr>");
         listerTous(out);
         out.println("</table>");
         out.println("</div>");
         out.println("</div>");
         out.println("</body>");
         out.println("</html>");
      }
   }
   protected void processRequestGenre(HttpServletRequest request, HttpServletResponse response, String genre)
           throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
      try (PrintWriter out = response.getWriter()) {
         /* TODO output your page here. You may use following sample code. */
         out.println("<!DOCTYPE html>");
         out.println("<html>");
         ecrireTete(out,"Catalogue");
         out.println("<body>");
         out.println("<div class=\"Catalogue\">");
         out.println("<img src='Images/titre1.png' height='124' width='573'/></a>");
         out.println("<div class=\"connexion\">");
         out.println("<form action=\"ConnexionUser\" method=\"POST\">");
         out.println("<table>");
         out.println("<tr><td> Nom d'usager : </td><td> <input id=\"Username\" type=\"text\" class=\"Text_Box\" name=\"User\" /> </td></tr>");
         out.println("<tr><td> Mot de passe : </td><td> <input id=\"Password\" type=\"password\" class=\"Text_Box\" name=\"Password\" /> </td></tr>");
         out.println("<tr><td><button id=\"btnconnexion\" type=\"submit\" class=\"BTN_Connexion\">Se connecter</button></td></tr>");
         out.println("</table>");
         out.println("</br>");
         out.println("</form>");
         out.println("</div>");
         out.println("<form class=\"CB\" action=\"ConnexionUser\" method=\"POST\">");
         out.println("<div>");
         out.println("<select name='Genre'>");
         out.println("<option value=\"Tous\">Tous</option>");
         out.println("<option value=\"Armes\">Armes</option>");
         out.println("<option value=\"Armures\">Armures</option>");
         out.println("<option value=\"Potions\">Potions</option>");
         out.println("<option value=\"Habilites\">Habilites</option>");
         out.println("</select>");
         out.println("<button id=\"btnfiltrer\" type=\"submit\" class=\"BTN_Filtrer\">Filtrer Inventaire</button>");
         out.println("</div>");
         out.println("</form>");
         out.println("<div class='Liste'>");
         out.println("<table id='ObjectList'>");
         out.println("<tr>");
         out.println("<th>Nom d'item</th>");
         out.println("<th>Genre</th>");
         out.println("<th>Prix</th>");
         out.println("<th>Quantité disponible</th>");
         out.println("</tr>");
         listerParGenre(genre, out);
         out.println("</table>");
         out.println("</div>");
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
   protected void listerTous(PrintWriter out){
        
      try
        {
           //Connexion DB
           ConnectionOracle oradb = new ConnectionOracle();
           oradb.connecter();
           // Déclaration
           String sqltous = "select * from items";
           ResultSet rstTous;
           // Lister les items
            Statement stm1 = oradb.getConnexion().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rstTous = stm1.executeQuery(sqltous);
            
            while (rstTous.next())
            {
               out.println("<tr><td>"+rstTous.getString(2).toString()+"<td>"+rstTous.getString(3).toString()+"<td>"+rstTous.getString(4).toString()+"<td>"
                       + rstTous.getString(5).toString());
            }
        }
        catch(SQLException sqlex){ System.out.println(sqlex);}
         
         
         
      }
   protected void listerParGenre(String genre ,PrintWriter out){
      try
        {
           //Connexion DB
           ConnectionOracle oradb = new ConnectionOracle();
           oradb.connecter();
           
            CallableStatement stm1 = oradb.getConnexion().prepareCall("{? = call GESTIONINVENTAIRE.LISTER(?)}",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stm1.registerOutParameter(1, OracleTypes.CURSOR);
            stm1.setString(2, genre);
            stm1.execute();
            ResultSet rstItems =(ResultSet)stm1.getObject(1);
            while (rstItems.next())
            {
               out.println("<tr><td>"+rstItems.getString(2).toString()+"<td>"+rstItems.getString(5).toString()+"<td>"+rstItems.getString(3).toString()+"<td>"
                       + rstItems.getString(4).toString());
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
      processRequestTous(request, response);
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
           // Recup du genre
           String genre = request.getParameter("Genre");
           //avertit le navigateur qu'il va recevoir du code HTML
           response.setContentType("text/html;charset=UTF-8");
        
            //Flux ecriture
            PrintWriter out = response.getWriter();
           try
           {
            if(genre.equals("Tous"))
           {
              processRequestTous(request, response); 
           }
            else
            {
               processRequestGenre(request, response, genre);
            }
           }
           finally
           {
              out.close();
           }
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