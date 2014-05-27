/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package InfoItem;

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
 * @author 200666155
 */
@WebServlet(name = "InfoItem", urlPatterns = {"/InfoItem"})
public class InfoItem extends HttpServlet {
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
            int idItem = Integer.parseInt(request.getParameter("Item"));
            String Genre = request.getParameter("Genre");
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            ecrireTete(out,"Information Item");
            out.println("<body>");
            out.println("<div class=\"Info\">");
            out.println("<a href=\"/DebarasBoileau/Catalogue\"><img src='Images/titre1.png' height='124' width='573'/></a>");
            out.println("<div class='InfoItem'>");
            Information(out,idItem,Genre);
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
    
    private void Information(PrintWriter out,int idItem, String Genre){
        if(Genre.equals("Armes"))
        {
            Armes(out,idItem);
        }
        else if(Genre.equals("Armures"))
        {
            Armures(out,idItem);
        }
        else if(Genre.equals("Potions"))
        {
            Potions(out,idItem);
        }
        else if(Genre.equals("Habilites"))
        {
            Habilites(out,idItem);
        }
    }
    
    private void Armes(PrintWriter out,int idItem){
        try
        {
            ConnectionOracle oradb = new ConnectionOracle();
            oradb.connecter();
            CallableStatement stm1 = oradb.getConnexion().prepareCall("{? = call GESTIONARMES.GETINFOS(?)}",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            stm1.registerOutParameter(1, OracleTypes.CURSOR);
            stm1.setInt(2, idItem);
            stm1.execute();
            ResultSet rstArmes =(ResultSet)stm1.getObject(1);
            rstArmes.next();
            
            
            out.println("<img class='ImageInfo' src='Images/Armes.png' height='80' width='80' />");
            out.println("<br>");
            out.println("Nom d'item: " + rstArmes.getString(1));
            out.println("<br>");
            out.println("Genre: " + rstArmes.getString(2));
            out.println("<br>");
            out.println("Prix: " + rstArmes.getString(3));
            out.println("<br>");
            out.println("Qts Dispo: " + rstArmes.getString(4));
            out.println("<br>");
            out.println("Efficacité: " + rstArmes.getString(5));
            out.println("<br>");
            out.println("Dégats: " + rstArmes.getString(6));
        }
        catch(SQLException sqlex){ System.out.println(sqlex);}
    }
    
    private void Armures(PrintWriter out,int idItem){
        try
        {
            ConnectionOracle oradb = new ConnectionOracle();
            oradb.connecter();
            CallableStatement stm2 = oradb.getConnexion().prepareCall("{? = call GESTIONARMURE.GETINFOS(?)}",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            stm2.registerOutParameter(1, OracleTypes.CURSOR);
            stm2.setInt(2, idItem);
            stm2.execute();
            ResultSet rstArmures =(ResultSet)stm2.getObject(1);
            rstArmures.next();
            
            out.println("<img class='ImageInfo' src='Images/Armures.png' height='80' width='80' />");
            out.println("<br>");
            out.println("Nom d'item: " + rstArmures.getString(1));
            out.println("<br>");
            out.println("Genre: " + rstArmures.getString(2));
            out.println("<br>");
            out.println("Prix: " + rstArmures.getString(3));
            out.println("<br>");
            out.println("Qts Dispo: " + rstArmures.getString(4));
            out.println("<br>");
            out.println("Efficacité: " + rstArmures.getString(5));
            out.println("<br>");
            out.println("Matière: " + rstArmures.getString(6));
            out.println("<br>");
            out.println("Taille: " + rstArmures.getString(7));
            out.println("<br>");
            out.println("Poids: " + rstArmures.getString(8));
        }
        catch(SQLException sqlex){ System.out.println(sqlex);}
    }
    
    private void Potions(PrintWriter out,int idItem){
        try
        {
            ConnectionOracle oradb = new ConnectionOracle();
            oradb.connecter();
            CallableStatement stm3 = oradb.getConnexion().prepareCall("{? = call GESTIONPOTIONS.GETINFOS(?)}",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            stm3.registerOutParameter(1, OracleTypes.CURSOR);
            stm3.setInt(2, idItem);
            stm3.execute();
            ResultSet rstPotions =(ResultSet)stm3.getObject(1);
            rstPotions.next();
            
            out.println("<img class='ImageInfo' src='Images/Potions.png' height='80' width='80' />");
            out.println("<br>");
            out.println("Nom d'item: " + rstPotions.getString(1));
            out.println("<br>");
            out.println("Genre: " + rstPotions.getString(2));
            out.println("<br>");
            out.println("Prix: " + rstPotions.getString(3));
            out.println("<br>");
            out.println("Qts Dispo: " + rstPotions.getString(4));
            out.println("<br>");
            out.println("Effet: " + rstPotions.getString(5));
            out.println("<br>");
            out.println("Durée Effet: " + rstPotions.getString(6));
        }
        catch(SQLException sqlex){ System.out.println(sqlex);}
    }
    
    private void Habilites(PrintWriter out,int idItem){
        try
        {
            ConnectionOracle oradb = new ConnectionOracle();
            oradb.connecter();
            CallableStatement stm4 = oradb.getConnexion().prepareCall("{? = call GESTIONSKILL.GETINFOS(?)}",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            stm4.registerOutParameter(1, OracleTypes.CURSOR);
            stm4.setInt(2, idItem);
            stm4.execute();
            ResultSet rstHabiletes =(ResultSet)stm4.getObject(1);
            rstHabiletes.next();
            
            out.println("<img class='ImageInfo' src='Images/habilite.png' height='70' width='70' />");
            out.println("<br>");
            out.println("Nom d'item: " + rstHabiletes.getString(1).toString());
            out.println("<br>");
            out.println("Genre: " + rstHabiletes.getString(2).toString());
            out.println("<br>");
            out.println("Prix: " + rstHabiletes.getString(3).toString());
            out.println("<br>");
            out.println("Qts Dispo: " + rstHabiletes.getString(4).toString());
            out.println("<br>");
            out.println("Description: " + rstHabiletes.getString(5).toString());
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
