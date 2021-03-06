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
import javax.servlet.http.HttpSession;

/**
 *
 * @author 201106779
 */
@WebServlet(name = "Catalogue", urlPatterns = {"/Catalogue"})
public class Catalogue extends HttpServlet {
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
    protected void processRequestTous(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            session = request.getSession();
            String Status = request.getParameter("Status");
            String Ajout = request.getParameter("Ajout");
            
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            ecrireTete(out,"Catalogue");
            out.println("<body>");
            out.println("<div class=\"Catalogue\">");
            out.println("<img src='Images/titre1.png' height='124' width='573'/></a>");
            out.println("<div class=\"connexion\">");
            //out.println("<div class=\"connexionIMG\">");
            panierOuInscription(out);
            //out.println("</div>");
            if( session.getAttribute("User") == null)
                out.println("<form action=\"ConnexionUser\" method=\"POST\">");
            else
                out.println("<form action=\"logout\" method=\"POST\">");
            out.println("<table>");
            ecrireConn(out, session, request,Status);
            out.println("</table>");
            out.println("</br>");
            out.println("</form>");
            out.println("</div>");
            ecrireSelectionMode(out);
            out.println("<div class='Liste'>");
            out.println("<table id='ObjectList'>");
            out.println("<tr>");
            out.println("<th>Nom d'item</th>");
            out.println("<th>Genre</th>");
            out.println("<th>Prix</th>");
            out.println("<th>Quantité disponible</th>");
            if(Ajout !=null)
            {
            if(Ajout.equals("ok"))
            {
               out.println("<th colspan=\"2\">Item ajouté !</th>");
            }
            else if(Ajout.equals("error"))
            {
               out.println("<th colspan=\"2\">Ajout d'item échoué</th>");
            }
            }
            out.println("</tr>");
            listerTous(out,request);
            out.println("</table>");
            out.println("</div>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    private void ecrireSelectionMode(PrintWriter out){
            out.println("<form class=\"CB\" action=\"Catalogue\" method=\"POST\">");
            out.println("<div>");
            out.println("<select name=\"Genre\">");
            out.println("<option value='Tous'>Tous</option>");
            out.println("<option value='Armes'>Armes</option>");
            out.println("<option value='Armures'>Armures</option>");
            out.println("<option value='Potions'>Potions</option>");
            out.println("<option value='Habilites'>Habilites</option>");
            out.println("</select>");
            out.println("<tr><td> Recherche : </td><td> <input id=\"Recherche\" type=\"text\" class=\"Text_Box\" name=\"Recherche\" /> </td></tr>");
            out.println("<button id='btnfiltrer' type=\"submit\" class='BTN_Filtrer'>Filtrer Inventaire</button>");
            out.println("</div>");
            out.println("</form>");
}
    
    protected void processRequestGenre(HttpServletRequest request, HttpServletResponse response, String genre)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            session = request.getSession();
            String Status = request.getParameter("Status");
            String Ajout = request.getParameter("Ajout");
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            ecrireTete(out,"Catalogue");
            out.println("<body>");
            //panierOuInscription(out);
            out.println("<div class=\"Catalogue\">");
            out.println("<img src='Images/titre1.png' height='124' width='573'/>");
            out.println("<div class=\"connexion\">");
            panierOuInscription(out);
            if( session.getAttribute("User") == null)
                out.println("<form action=\"ConnexionUser\" method=\"POST\">");
            else
                out.println("<form action=\"logout\" method=\"POST\">");
            out.println("<table>");
            ecrireConn(out, session, request,Status);
            out.println("</table>");
            out.println("</br>");
            out.println("</form>");
            out.println("</div>");
            ecrireSelectionMode(out);
            out.println("<div class='Liste'>");
            out.println("<table id='ObjectList'>");
            out.println("<tr>");
            out.println("<th>Nom d'item</th>");
            out.println("<th>Genre</th>");
            out.println("<th>Prix</th>");
            out.println("<th>Quantité disponible</th>");
            if(Ajout !=null)
            {
            if(Ajout.equals("ok"))
            {
               out.println("<th colspan=\"2\">Item ajouté !</th>");
            }
            else if(Ajout.equals("error"))
            {
               out.println("<th colspan=\"2\">Ajout d'item échoué</th>");
            }
            }
            out.println("</tr>");
            listerParGenre(genre, out,request);
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
    private void ecrireConn(PrintWriter out , HttpSession session , HttpServletRequest request,String Status) {
        session = request.getSession();
        if( Status == null)
        {
            if(session.getAttribute("User") != null){
                out.println("<tr><td> Bienvenue à vous "+session.getAttribute("User"));
                out.println("<br>Vous avez "+session.getAttribute("Ecus")+" Ecus");
                out.println("<tr><td><button id=\"btndeconnexion\" type=\"submit\" class=\"BTN_Deconnexion\">Se déconnecter</button></td></tr>");
            }
            else{
                out.println("<tr><td> Nom d'usager : </td><td> <input id=\"Username\" type=\"text\" class=\"Text_Box\" name=\"User\" /> </td></tr>");
                out.println("<tr><td> Mot de passe : </td><td> <input id=\"Password\" type=\"password\" class=\"Text_Box\" name=\"Password\" /> </td></tr>");
                out.println("<tr><td><button id=\"btnconnexion\" type=\"submit\" class=\"BTN_Connexion\">Se connecter</button></td></tr>");
            }
        }
        else
        {
           if(Status.equals("ok"))
           {
            out.println("<tr><td> Bienvenue à vous "+session.getAttribute("User"));
            out.println("<br>Vous avez "+session.getAttribute("Ecus")+" Ecus");
            out.println("<tr><td><button id=\"btndeconnexion\" type=\"submit\" class=\"BTN_Deconnexion\">Se déconnecter</button></td></tr>");
           }
           if(Status.equals("error"))
           {
            out.println("<tr><td> Nom d'usager : <input id=\"Username\" type=\"text\" class=\"Text_Box\" name=\"User\" /> </td></tr>");
            out.println("<tr><td> Mot de passe : <input id=\"Password\" type=\"password\" class=\"Text_Box\" name=\"Password\" /> </td></tr>");
            out.println("<tr><td><button id=\"btnconnexion\" type=\"submit\" class=\"BTN_Connexion\">Se connecter</button></td></tr>");
            out.println("<tr><td> Erreur de connexion ,</td></tr><tr><td> l'un des champs est invalide</td></tr>");
           }
        }
        
    }
    protected void listerTous(PrintWriter out,HttpServletRequest request){
        try{
            String tbRecherche = request.getParameter("Recherche");
            ConnectionOracle oradb = new ConnectionOracle();
            CallableStatement stm1;
            oradb.connecter();
            if(tbRecherche == null)
            {
                stm1 = oradb.getConnexion().prepareCall("{? = call GESTIONINVENTAIRE.LISTERTOUS()}",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stm1.registerOutParameter(1, OracleTypes.CURSOR);
                stm1.execute();
                ResultSet rstItems =(ResultSet)stm1.getObject(1);
                
                ecrireItems(rstItems,out);
            }
            else
            {
                stm1 = oradb.getConnexion().prepareCall("{? = call GESTIONINVENTAIRE.RECHERCHESANSGENRE(?)}",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stm1.registerOutParameter(1, OracleTypes.CURSOR);
                stm1.setString(2, tbRecherche);
                stm1.execute();
                ResultSet rstItems =(ResultSet)stm1.getObject(1);
                
                ecrireItems(rstItems,out);
            }
        }
        catch(SQLException sqlex){ System.out.println(sqlex);}
        
    }
    
    private void ecrireItems(ResultSet rst,PrintWriter out){
        try
        {
            while (rst.next())
            {
                
                out.println("<tr><td>"+rst.getString(2).toString()+"</td>"+"<td>"+rst.getString(5).toString()+"</td>"+"<td>"+rst.getString(3).toString()+"</td>"+"<td>"
                        + rst.getString(4).toString()+"</td>");
                out.println("<td><form action=\"InfoItem\" method=\"POST\"><input type=\"hidden\" name=\"Item\" value="+ rst.getString(1) +" /><input type=\"hidden\" name=\"Genre\" value="+ rst.getString(5) +" /><button id=\"btninfo\" type=\"submit\" class=\"BTN_Info\">Informations</button></form></td>");
                if( session.getAttribute("User") != null)
                {
                    out.println("<td><form action=\"PanierAjout\" method=\"POST\"><input type=\"hidden\" name=\"Item\" value="+ rst.getString(1) +" /><button id=\"btnajouter\" type=\"submit\" class=\"BTN_Ajouter\">Ajouter Au Panier</button></form></td>");
                    
                }
                out.println("</tr>");
            }
        }
        catch(SQLException sqlex){ System.out.println(sqlex);}
    }
    
    
    private void panierOuInscription(PrintWriter out){
        if( session.getAttribute("User") != null){
            out.println("<tr><td><a href=\"/DebarasBoileau/Panier\"><img src='Images/Panier.png'  height='32' width='32'></a></td></tr>");
            out.println("<a href=\"/DebarasBoileau/Inventaire\"><img src='Images/Inv.jpg' height='32' width='32'/></a>");
        }
        else{
            out.println("<tr><td><a href=\"/DebarasBoileau/Inscription\"><img src='Images/Inscription.png'  height='32' width='32'></a></td></tr>");
        }
    }
    protected void listerParGenre(String genre ,PrintWriter out,HttpServletRequest request){
        try
        {
            //Connexion DB
            String tbRecherche = request.getParameter("Recherche");
            ConnectionOracle oradb = new ConnectionOracle();
            CallableStatement stm1;
            oradb.connecter();
            if(tbRecherche == null)
            {
                stm1 = oradb.getConnexion().prepareCall("{? = call GESTIONINVENTAIRE.LISTERAVECGENRE(?)}",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stm1.registerOutParameter(1, OracleTypes.CURSOR);
                stm1.setString(2, genre);
                stm1.execute();
                ResultSet rstItems =(ResultSet)stm1.getObject(1);
                ecrireItems(rstItems,out);
                
            }
            else
            {
                stm1 = oradb.getConnexion().prepareCall("{? = call GESTIONINVENTAIRE.RECHERCHELIKEGENRE(?,?)}",ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stm1.registerOutParameter(1, OracleTypes.CURSOR);
                stm1.setString(2, genre);
                stm1.setString(3, tbRecherche);
                stm1.execute();
                ResultSet rstItems =(ResultSet)stm1.getObject(1);
                
                ecrireItems(rstItems,out);
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
            if(genre.equals("Tous"))
            {
                processRequestTous(request, response);
            }
            else
            {
                processRequestGenre(request, response, genre);
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