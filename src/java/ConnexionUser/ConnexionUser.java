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
import javax.swing.JOptionPane;
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
   private String ecus;
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
            Statement stm = oradb.getConnexion().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rest = stm.executeQuery(sqljoueurs);
            
            if(rest.first())
            {
               siValide = true;
               ecus=Integer.toString(rest.getInt(3));
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
private void writeConnexionTB(PrintWriter out){
         out.println("<tr><td> Nom d'usager : </td><td> <input id=\"Username\" type=\"text\" class=\"Text_Box\" name=\"User\" /> </td></tr>");
         out.println("<tr><td> Mot de passe : </td><td> <input id=\"Password\" type=\"password\" class=\"Text_Box\" name=\"Password\" /> </td></tr>");
         out.println("<tr><button id=\"btnconnexion\" type=\"submit\" class=\"BTN_Connexion\">Se connecter</button></td></tr>");
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
              session.setAttribute("Ecus", ecus);
              response.sendRedirect("http://localhost:8084/DebarasBoileau/Catalogue");
           }
           else
           {
              String st="L'un des champs est invalide";
              JOptionPane.showMessageDialog(null, st);
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
   
