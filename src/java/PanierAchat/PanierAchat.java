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
        
        boolean verifachat =Acheter(idItem,user,quantite);
        if(verifachat)
         response.sendRedirect("http://localhost:8084/DebarasBoileau/Panier?Status=ok");
        else
         response.sendRedirect("http://localhost:8084/DebarasBoileau/Panier?Status=error"); 
        
    }
    
     private boolean Acheter(ArrayList<Integer> idItem,String user, ArrayList<Integer> quantite){
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
                    return false;
                }
            }   
            
            CallableStatement stm2=oradb.getConnexion().prepareCall("{?=call GESTIONPANIER.VerifPanier(?)}",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            stm2.registerOutParameter(1, OracleTypes.CURSOR);
            stm2.setString(2,user);
            
            stm2.execute(); 
            ResultSet rst = (ResultSet)stm2.getObject(1);
            
            while(rst.next() && Disponible)
            {
                idItem.add(Compteur,rst.getInt(1));
                quantite.add(Compteur,rst.getInt(3));
                CallableStatement stm3=oradb.getConnexion().prepareCall("{call GESTIONPANIER.AcheterItem(?,?,?)}");
             
                stm3.setString(1,user);
                stm3.setInt(2, idItem.get(Compteur));
                stm3.setInt(3, quantite.get(Compteur));
                
                stm3.execute();
                Compteur++;
            }
            CallableStatement stm4=oradb.getConnexion().prepareCall("{?=call GESTIONJOUEURS.VERIFIERCASH(?)}");
             
            stm4.registerOutParameter(1,OracleTypes.NUMBER);
            stm4.setString(2, user);
            stm4.execute();
            
            float Ecus = stm4.getFloat(1);
            
            session.setAttribute("Ecus", Ecus);
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
