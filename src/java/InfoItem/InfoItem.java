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
            out.println("<div class='Liste'>");
            out.println("<table id='ObjectList'>");
            Information(out,idItem,Genre);
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
        else if(Genre.equals("Habiletes"))
        {
            Habiletes(out,idItem);
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
            
            out.println("<tr>");
            out.println("<th>Nom d'item</th>");
            out.println("<th>Genre</th>");
            out.println("<th>Prix</th>");
            out.println("<th>Quantité</th>");
            out.println("<th>Efficacité</th>");
            out.println("<th>Dégats</th>");
            out.println("</tr>");
            
            while (rstArmes.next())
            {
                out.println("<tr><td>"+rstArmes.getString(1).toString()+"</td>"+"<td>"+rstArmes.getString(2).toString()+"</td>"+"<td>"+rstArmes.getString(3).toString()+"</td>"+"<td>"
                        + rstArmes.getString(4).toString()+"</td><td>"+rstArmes.getString(5).toString()+"</td><td>"+rstArmes.getString(6).toString()+"</td></tr>");
            }
        }
        catch(SQLException sqlex){ System.out.println(sqlex);}
    }
    
    private void Armures(PrintWriter out,int idItem){
        try
        {
            ConnectionOracle oradb = new ConnectionOracle();
            oradb.connecter();
            CallableStatement stm2 = oradb.getConnexion().prepareCall("{? = call GESTIONARMURES.GETINFOS(?)}",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            stm2.registerOutParameter(1, OracleTypes.CURSOR);
            stm2.setInt(2, idItem);
            stm2.execute();
            ResultSet rstArmures =(ResultSet)stm2.getObject(1);
            
            out.println("<tr>");
            out.println("<th>Nom d'item</th>");
            out.println("<th>Genre</th>");
            out.println("<th>Prix</th>");
            out.println("<th>Quantité</th>");
            out.println("<th>Efficacité</th>");
            out.println("<th>Matière</th>");
            out.println("<th>Taille</th>");
            out.println("<th>Poids</th>");
            out.println("</tr>");
            
            while (rstArmures.next())
            {
                out.println("<tr><td>"+rstArmures.getString(1).toString()+"</td>"+"<td>"+rstArmures.getString(2).toString()+"</td>"+"<td>"+rstArmures.getString(3).toString()+"</td>"+"<td>"
                        + rstArmures.getString(4).toString()+"</td><td>"+rstArmures.getString(5).toString()+"</td><td>"+rstArmures.getString(6).toString()
                        + "</td><td>"+rstArmures.getString(7).toString()+"</td><td>"+rstArmures.getString(8).toString()+"</td></tr>");
            }
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
            
            out.println("<tr>");
            out.println("<th>Nom d'item</th>");
            out.println("<th>Genre</th>");
            out.println("<th>Prix</th>");
            out.println("<th>Quantité</th>");
            out.println("<th>Effet</th>");
            out.println("<th>Durée Effet</th>");
            out.println("</tr>");
            
            while (rstPotions.next())
            {
                out.println("<tr><td>"+rstPotions.getString(1).toString()+"</td>"+"<td>"+rstPotions.getString(2).toString()+"</td>"+"<td>"+rstPotions.getString(3).toString()+"</td>"+"<td>"
                        + rstPotions.getString(4).toString()+"</td><td>"+rstPotions.getString(5).toString()+"</td><td>"+rstPotions.getString(6).toString() + "</td></tr>");
            }
        }
        catch(SQLException sqlex){ System.out.println(sqlex);}
    }
    
    private void Habiletes(PrintWriter out,int idItem){
        try
        {
            ConnectionOracle oradb = new ConnectionOracle();
            oradb.connecter();
            CallableStatement stm4 = oradb.getConnexion().prepareCall("{? = call GESTIONSKILL.GETINFOS(?)}",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            stm4.registerOutParameter(1, OracleTypes.CURSOR);
            stm4.setInt(2, idItem);
            stm4.execute();
            ResultSet rstHabiletes =(ResultSet)stm4.getObject(1);
            
            
            while (rstHabiletes.next())
            {
                out.println("<tr><td>"+rstHabiletes.getString(1).toString()+"</td>"+"<td>"+rstHabiletes.getString(2).toString()+"</td>"+"<td>"+rstHabiletes.getString(3).toString()+"</td>"+"<td>"
                        + rstHabiletes.getString(4).toString()+"</td><td>"+rstHabiletes.getString(5).toString()+"</td></tr>");
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
