/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ConnexionUser;

import Inscription.ConnectionOracle;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import oracle.jdbc.*;
import oracle.jdbc.pool.*;

/**
 *
 * @author 201106779
 */
@WebServlet(name = "ConnexionUser", urlPatterns = {"/ConnexionUser"})
public class ConnexionUser extends HttpServlet {
   private String nomUser;
   private String motDePasse;
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
      session = request.getSession();
      response.setContentType("text/html;charset=UTF-8");
      try (PrintWriter out = response.getWriter()) {
         /* TODO output your page here. You may use following sample code. */
         out.println("<!DOCTYPE html>");
         out.println("<html>");
         ecrireTete(out,"Catalogue");
         out.println("<body>");
         out.println("<div class=\"Catalogue\">");
         out.println("<img src='Images/titre1.png' height='124' width='573'/></a>");
         out.println("<form action=\"C\" method=\"POST\">");
         out.println("<table>");
         out.println("<tr><td> Nom d'usager : </td><td> <input id=\"Username\" type=\"text\" class=\"Text_Box\" name=\"User\" /> </td></tr>");
         out.println("<tr><td> Mot de passe : </td><td> <input id=\"Password\" type=\"password\" class=\"Text_Box\" name=\"Password\" /> </td></tr>");
         out.println("<tr><button id=\"btnconnexion\" type=\"submit\" class=\"BTN_Connexion\">Se connecter</button></td></tr>");
         out.println("</table>");
         out.println("</br>");
         out.println("</form>");
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
   
   protected void processRequestUserConnecter(HttpServletRequest request, HttpServletResponse response,String Username ,String Ecus)
           throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
      session = request.getSession();
      nomUser = (String)session.getAttribute("Username");
             
      try (PrintWriter out = response.getWriter()) {
         /* TODO output your page here. You may use following sample code. */
         out.println("<!DOCTYPE html>");
         out.println("<html>");
         ecrireTete(out,"Catalogue");
         out.println("<body>");
         out.println("<div class=\"Catalogue\">");
         out.println("<img src='Images/titre1.png' height='124' width='573'/></a>");
         out.println("<form action=\"C\" method=\"POST\">");
         out.println("<table>");
         out.println("<tr><td> Bienvenue à vous "+Username +" Nombre d'écus :"+ Ecus +" /> </td></tr>");
         out.println("</table>");
         out.println("</br>");
         out.println("</form>");
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
                       +"<td>"+rstTous.getString(5).toString());
            }
        }
        catch(SQLException sqlex){ System.out.println(sqlex);}
}
private boolean validerConnexion(String nom , String mdp){

            boolean siValide = false;
            // Sql
            String sqljoueurs = "Select NomUsager,MotDePasse,EcusJoueurs from Joueurs where NomUsager = '" +nom+ "' and MotDePasse ='"+ mdp + "'";
            //Connexion
            ConnectionOracle oradb = new ConnectionOracle();
            oradb.connecter();

           try
           {
            Statement stm = oradb.getConnexion().createStatement();
            ResultSet rest = stm.executeQuery(sqljoueurs);
            
            if(rest.first())
            {
               siValide = true;
            }
            rest.close();
            stm.close();
            oradb.deconnecter();
           }
           catch(SQLException se)
           {
              System.err.println(se.getMessage());
           }
           return siValide;
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
           
           
           session = request.getSession();
           // Recup des params
           nomUser = request.getParameter("User");
           motDePasse = request.getParameter("Password");
           
           //avertit le navigateur qu'il va recevoir du code HTML
           response.setContentType("text/html;charset=UTF-8");
           
           //Flux ecriture
           PrintWriter out = response.getWriter();
           
           //Connexion
            ConnectionOracle oradb = new ConnectionOracle();
            oradb.connecter();
            
           if(validerConnexion(nomUser, motDePasse))
           {
              session.setAttribute("User", nomUser);
              response.sendRedirect("http://localhost:8084/DebarasBoileau/Catalogue");
           }
           else
           {
              out.println("Erreur,l'un des champs est erroné"); // a regler
              response.sendRedirect("http://localhost:8084/DebarasBoileau/Catalogue");
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
   
