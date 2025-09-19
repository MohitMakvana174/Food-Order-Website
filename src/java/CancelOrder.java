/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author MOHIT MAKVANA
 */
public class CancelOrder extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String m = request.getParameter("email");
        String f = request.getParameter("food");
        String qa1 = request.getParameter("qa");
        int qa = Integer.parseInt(qa1);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/foodorder","root","");


            String del_query = "DELETE FROM `order` WHERE o_id=?";
            String query = "SELECT o_id FROM `order` WHERE email=? and f_name=? and quantity=?";
            PreparedStatement ps = con.prepareStatement(query);
            PreparedStatement ps1 = con.prepareStatement(del_query);
            ps.setString(1,m);
            ps.setString(2,f);
            ps.setInt(3,qa);
            try{
                ResultSet rs = ps.executeQuery();
                rs.next();
                int o_id= rs.getInt(1);
               // out.println(o_id);

                ps1.setInt(1, o_id);
                ps1.execute();          
                out.println("Your order is successfully Deleted"+"<br>");
                out.println("<a href=\"cancelOrder.html\"><button class=\"btn btn-primary\">Back</button></a>");
            }
            catch(Exception er){
                out.println("No Order found with given Information");
                 out.println("<a href=\"cancelOrder.html\"><button class=\"btn btn-primary\">Back</button></a>");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
