/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PanierAjout;

import ConnexionUser.ConnectionOracle;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 200666155
 */
@WebServlet(name = "PanierAjout", urlPatterns = {"/PanierAjout"})
public class PanierAjout extends HttpServlet {
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
        //try (PrintWriter out = response.getWriter()) {
        session = request.getSession();
        int idItem = Integer.parseInt(request.getParameter("Item"));
        String user = (String)session.getAttribute("User");
        boolean verif=Ajouter(idItem,user);
        if(verif)
         response.sendRedirect("http://localhost:8084/DebarasBoileau/Catalogue?Ajout=ok"); 
        else
         response.sendRedirect("http://localhost:8084/DebarasBoileau/Catalogue?Ajout=error");  
    }
    
     private boolean Ajouter(int idItem,String user){
        try
        {
            ConnectionOracle oradb = new ConnectionOracle();
            oradb.connecter();
            
            CallableStatement stm1=oradb.getConnexion().prepareCall("{call GESTIONPANIER.AjoutPanier(?,?)}");
            
            stm1.setString(1,user);
            stm1.setInt(2, idItem);
            
            stm1.executeUpdate(); 
        }
        catch(SQLException sqlex){ System.out.println(sqlex); return false;} 
        return true;
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
