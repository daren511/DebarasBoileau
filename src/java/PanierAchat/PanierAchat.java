/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PanierAchat;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author 201093812
 */
@WebServlet(name = "PanierAchat", urlPatterns = {"/PanierAchat"})
public class PanierAchat extends HttpServlet {
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
        ArrayList<Integer> idItem = new ArrayList();
        ArrayList<Integer> quantite = new ArrayList();
        String user = (String)session.getAttribute("User");
        Acheter(idItem,user,quantite);
        
        response.sendRedirect("http://localhost:8084/DebarasBoileau/Panier");
    }
    
     private void Acheter(ArrayList<Integer> idItem,String user, ArrayList<Integer> quantite){
        try
        {
            ConnexionUser.ConnectionOracle oradb = new ConnexionUser.ConnectionOracle();
            oradb.connecter();
            
            CallableStatement stm1=oradb.getConnexion().prepareCall("{?=call GESTIONPANIER.VerifPanier(?)}",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            stm1.registerOutParameter(1, OracleTypes.CURSOR);
            stm1.setString(2,user);
            
            stm1.execute(); 
            ResultSet rstIdItem = (ResultSet)stm1.getObject(1);
            int Compteur = 0;
            boolean Disponible = true; 
            
            while(rstIdItem.next())
            {
                if(rstIdItem.getInt(3) > rstIdItem.getInt(2))
                {
                    Disponible=false;
                }
            }   
            
            rstIdItem.first();
            
            while(rstIdItem.next() && Disponible)
            {
                idItem.add(Compteur,rstIdItem.getInt(1));
                quantite.add(Compteur,rstIdItem.getInt(3));
                CallableStatement stm2=oradb.getConnexion().prepareCall("{call GESTIONPANIER.AcheterItem(?,?,?)}");
             
                stm2.setString(1,user);
                stm2.setInt(2, idItem.get(Compteur));
                stm2.setInt(3, quantite.get(Compteur));
                
                stm2.execute();
                Compteur++;
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
