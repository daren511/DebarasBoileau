/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package Inscription;

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
 * @author 201093812
 */
@WebServlet(name = "Inscription", urlPatterns = {"/Inscription"})
public class Inscription extends HttpServlet {
    
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
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            ecrireTete(out,"Inscription");
            out.println("<body>");
            out.println("<div class=\"Inscription\">");
            out.println("<a href=\"/DebarasBoileau/Catalogue\"><img src='Images/titre1.png' height='115' width='420'/></a>");
            out.println("<p id=\"Form\"><strong><u>Inscription</u></strong> <br/> </p>");
            out.println("<form action=\"Inscription\" method=\"POST\">");
            out.println("<table id=\"T_Inscription\">");
            
            out.println("<tr><td> Nom d'usager : </td><td> <input id=\"Username\" type=\"text\" class=\"Text_Box\" name=\"User\" /> </td></tr>");
            
            out.println("<tr><td> Mot de passe : </td><td> <input id=\"Password\" type=\"password\" class=\"Text_Box\" name=\"Password\" /> </td></tr>");
            
            out.println("<tr><td> Nom : </td><td> <input id=\"Name\" type=\"text\" class=\"Text_Box\" name=\"Nom\" /> </td></tr>");
            
            out.println("<tr><td> Prénom : </td><td> <input id=\"firstname\" type=\"text\" class=\"Text_Box\" name=\"Prenom\" /> </td></tr>");
            
            out.println("<tr><td colspan=\"2\"> <button id=\"btninscription\" type=\"submit\" class=\"BTN_Inscrire\">S'inscrire</button> </td></tr>");
            
            //out.println("<tr><td><a href=\"/DebarasBoileau/Catalogue\"><img src='Images/Catalogue.png'  height='39' width='38'></a></td></tr>");
              
            
            out.println("</table>");
            out.println("</form>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    protected void processRequestAfterInsert(HttpServletRequest request, HttpServletResponse response, String etat, String texte)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            ecrireTete(out,"Inscription");
            out.println("<body>");
            out.println("<div class=\"Inscription\">");
            out.println("<a href=/DebarasBoileau/Catalogue><img src='Images/titre1.png' height='115' width='420'/></a>");
            out.println("<p id=\"Form\"><strong><u>Inscription</u></strong> <br/> </p>");
            out.println("<form action=\"Inscription\" method=\"POST\">");
            out.println("<table id=\"T_Inscription\">");
            
            out.println("<tr><td> Nom d'usager : </td><td> <input id=\"Username\" type=\"text\" class=\"Text_Box\" name=\"User\" /> </td></tr>");
            
            out.println("<tr><td> Mot de passe : </td><td> <input id=\"Password\" type=\"password\" class=\"Text_Box\" name=\"Password\" /> </td></tr>");
            
            out.println("<tr><td> Nom : </td><td> <input id=\"Name\" type=\"text\" class=\"Text_Box\" name=\"Nom\" /> </td></tr>");
            
            out.println("<tr><td> Prénom : </td><td> <input id=\"firstname\" type=\"text\" class=\"Text_Box\" name=\"Prenom\" /> </td></tr>");
            
            out.println("<tr><td colspan=\"2\"> <button id=\"btninscription\" type=\"submit\" class=\"BTN_Inscrire\">S'inscrire</button> </td></tr>");
                       
            out.println("<tr><td class='"+etat+"' colspan='2'>"+texte+"</td></tr>");
            out.println("</table>");
            out.println("</form>");
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
    protected void doPost(
            HttpServletRequest request , HttpServletResponse response )
            throws ServletException, IOException
    {
        // Recup des params de la page
        String username = request.getParameter("User");
        String password = request.getParameter("Password");
        String nom = request.getParameter("Nom");
        String prenom = request.getParameter("Prenom");
        int nombreecus = 500;
        
        
        //avertit le navigateur qu'il va recevoir du code HTML
        response.setContentType("text/html;charset=UTF-8");
        
        //Flux ecriture
        PrintWriter out = response.getWriter();
        
        String etatB ="bon";
        String etatM ="mauvais";
        String textB ="Le compte "+username+" à été crée avec succès";
        String textM ="Erreur,veuillez remplir tout les champs";
        
        try
        {
            // Write Head
            ecrireTete(out,"Inscription");
            
            //Connexion
            ConnectionOracle oradb = new ConnectionOracle();
            oradb.connecter();
            CallableStatement stm1;
            
            
            try
            {
               // Insertion joueur
               stm1 = oradb.getConnexion().prepareCall("{call GESTIONJOUEURS.INSCRIPTION(?,?,?,?,?)}",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Affectation
                stm1.setString(1,username);
                stm1.setString(2, nom);
                stm1.setString(3,prenom);
                stm1.setString(4, password);
                stm1.setInt(5, nombreecus);
                stm1.executeUpdate();
                stm1.close();
                processRequestAfterInsert(request, response, etatB, textB);
                
            }
            catch(SQLException se)
            {
                System.err.println(se.getMessage());
                processRequestAfterInsert(request, response, etatM, textM);
            }
            
        }
        finally
        {
            out.close();
        }
        
        
        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
        //@Override
        //public String getServletInfo() {
        //    return "Short description";
        //}// </editor-fold>
        
    }
}
